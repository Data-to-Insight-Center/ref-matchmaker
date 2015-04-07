package edu.indiana.d2i.sead.matchmaker.service.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Yuan Luo (yuanluo@indiana.edu)
 */

public class MatchmakerOperations {
	public static final Log l = LogFactory.getLog(MatchmakerOperations.class);
	public static final String  ERROR_STRING = "SERVER ERROR";


	/*
	public enum NotificationType {
		receivedResponse,
		receivedFault,
		workflowInitialized,
		serviceInitialized,
		workflowTerminated,
		serviceTerminated,
		serviceInvoked,
		workflowInvoked,
		dataConsumed,
		dataProduced,
		dataUpdated,
		dataDeleted,
		sendingResponse,
		sendingResponseStatus,
		dataSendStarted,
		dataSendFinished,
		dataReceivedStarted,
		sendingFault,
		storeUnknown
	}
	*/
	
	public String exec(String message){
		return "{sucess:true,response:\"Sample Response Message\"}";
	}

}
