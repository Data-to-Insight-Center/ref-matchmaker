package edu.indiana.d2i.sead.matchmaker.service.messaging;

import edu.indiana.d2i.sead.matchmaker.drivers.*;
import edu.indiana.d2i.sead.matchmaker.service.ServiceLauncher;

import java.io.IOException;

import org.apache.log4j.Logger;



/**
 * @author Yuan Luo (yuanluo@indiana.edu)
 */
public class MessagingDaemons  {

	private Thread[] MessagingDeamons;
	private int numOfMessagingDaemons;
	
	private Logger log;
	
	public MessagingDaemons(MessagingDaemonsConfig msgdmconf, MessagingConfig msgconf, AbstractENV env) throws IOException, ClassNotFoundException{
		this.numOfMessagingDaemons=msgdmconf.getNumberOfMessagingDaemons();
		this.MessagingDeamons=new Thread[numOfMessagingDaemons];
		
		SynchronizedReceiverRunnable qsgrr=new SynchronizedReceiverRunnable(msgconf, env);
	    for (int i = 0; i < this.numOfMessagingDaemons; i++) {
			this.MessagingDeamons[i]= new Thread(qsgrr);
		}
	    
	    
	}
	
	public void start() throws java.lang.IllegalMonitorStateException{
		log = Logger.getLogger(MessagingDaemons.class);
		for (int i = 0; i < this.numOfMessagingDaemons; i++) {
			log.info("Starting Messaging Deamon ["+i+"] for receiving messages from clients.");
		    this.MessagingDeamons[i].start();
		    log.info("Messaging Deamon ["+i+"] Started.");

		}
	    
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            
            if (args.length < 1) {
                System.err.println("ERROR: properties file not specified");
                System.err.println("Usage:  ServiceLauncher <propertiesFilePath>");
                throw new Exception("ERROR: properties file not specified");
            }
            String propertiesFilePath = args[0];
            ServiceLauncher.start(propertiesFilePath);
            if(!ServiceLauncher.startMessageReceiverDaemon()){
            	//If MessageReceiverDaemon can't be started, shall we shutdown the whole Server? If yes, add the code here.
            	System.err.println("Unable to launch MessageReceiverDaemon");
            }
           
        } catch (Throwable e) {
        	System.err.println("Unable to launch service");
        }
        System.out.println("Main ends here");

    }

}