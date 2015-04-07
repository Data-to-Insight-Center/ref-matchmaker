package edu.indiana.d2i.sead.matchmaker.service.messaging;

public class MessagingDaemonsConfig{
	private int NumberOfMessagingDaemons;
	
	public MessagingDaemonsConfig(){
		this.NumberOfMessagingDaemons=1;
	}
	
	public void setNumberOfMessagingDaemons(int numOfQueryDaemons){
		this.NumberOfMessagingDaemons=numOfQueryDaemons;
	}
	public int getNumberOfMessagingDaemons(){
		return this.NumberOfMessagingDaemons;
	}
}
