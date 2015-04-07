package edu.indiana.d2i.sead.matchmaker.service.messaging;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.*;
import com.rabbitmq.client.ShutdownSignalException;

import edu.indiana.d2i.sead.matchmaker.core.POJOGenerator;

import edu.indiana.d2i.sead.matchmaker.service.ServiceLauncher;


/**
 * @author Yuan Luo (yuanluo@indiana.edu)
 */

public class SynchronizedReceiverRunnable  implements Runnable  {

	private MessagingConfig msgconf;
	private Receiver receiver;
	private MatchmakerOperations mmOperations;
	private Logger log;
	private POJOGenerator input;
	private int RETRY_INTERVAL;
	private int RETRY_THRESHOLD;
	public static final String  INVALID_REQUEST_ERROR_STRING = "Invalid Request";
	
	public SynchronizedReceiverRunnable(MessagingConfig msgconf, AbstractENV env) throws IOException, ClassNotFoundException{
		this.msgconf=msgconf;
		this.receiver=new Receiver(msgconf, MessagingOperationTypes.RECEIVE_REQUESTS);
		
		this.mmOperations = new MatchmakerOperations();

		this.log = Logger.getLogger(SynchronizedReceiverRunnable.class);
		this.RETRY_INTERVAL=msgconf.getMessagingRetryInterval();
		this.RETRY_THRESHOLD=msgconf.getMessagingRetryThreshold();
		input = new POJOGenerator("edu.indiana.d2i.sead.matchmaker.service.messaging.MatchmakerInputSchema");
		
	}
	
	public void run() throws java.lang.IllegalMonitorStateException{
		boolean runInfinite=true;
		String requestMessage;
		while (runInfinite) {
			try {
				log.info("[Matchmaker server: Listening Queries from Messaging System]");
				requestMessage=this.receiver.getMessage();
				log.info("[Matchmaker server: One Query received]\n"+requestMessage);
				// Parse the Message
				if(requestMessage!=null){
					
					this.input.fromString(requestMessage);
					JsonNode requestMessageJsonNode=this.input.getJsonTree();
					
					
					String ResponseRoutingKey=requestMessageJsonNode.get("responseKey").toString();
					String request=requestMessageJsonNode.get("request").toString();
					log.info("[Matchmaker server: Request] "+input);
					log.info("[Matchmaker server: Message Response Routing Key] "+ResponseRoutingKey);
					
					//Perform Service Logic
					String response=null;
					try{
						response=this.mmOperations.exec(request);
					}catch(Exception e){
						
						response=INVALID_REQUEST_ERROR_STRING;
						log.info("[Matchmaker server: Request Error] "+e.toString());
					}
					//Response
					msgconf.setResponseRoutingKey(ResponseRoutingKey);
					Sender sender=new Sender(msgconf, MessagingOperationTypes.SEND_RESPONSE);
					try {
						sender.sendMessage(response);
						log.info("[Matchmaker server: Send Response to a client]");
						sender.closeChannel();
						sender.closeConnection();
					} catch (ShutdownSignalException e) {
						// TODO Resend to client
						log.info("[Matchmaker server: Messaging Response Error] "+e.toString());
					} catch (IOException e) {
						// TODO Resend to client
						log.info("[Matchmaker server: Messaging Response Error] "+e.toString());
					} catch (InterruptedException e) {
						// TODO Resend to client
						log.info("[Matchmaker server: Messaging Response Error] "+e.toString());
					} 

				}else {
					log.info("[Matchmaker server: Empty Request]\n");
				}
				
				
			} catch (ShutdownSignalException e) {
				e.printStackTrace();
					
				this.receiver.abortChannel();
				this.receiver.abortConnection();
				
				boolean reconnected=false;
				int retry_count=0;
				while(!reconnected){
					if(retry_count>this.RETRY_THRESHOLD){
						ServiceLauncher.shutdown();
						return;
					}
					retry_count++;
					reconnected=false;
					try {
						log.info("Reconneting to Messaging Server.");
						this.receiver.createConnection();
						this.receiver.createChannel();
						reconnected=true;
					} catch (IOException e1) {
						log.error("Can't connect to Messaging Server.");
						reconnected=false;
						e1.printStackTrace();
					}
					//Sleep 5*retry_count seconds and try to reconnect again
					try {
						Thread.sleep(this.RETRY_INTERVAL*1000);
					} catch (InterruptedException e3) {
						e3.printStackTrace();
					}
					//break the while loop when reconnected
					if(reconnected){
						try {
							this.receiver.formatChannel();
							log.info("Reconneted to Messaging Server.");
							break;
						} catch (IOException e1) {
							reconnected=false;
							this.receiver.closeChannel();
							this.receiver.closeConnection();
						}
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("", e);
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("", e);
				e.printStackTrace();
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("", e);
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}