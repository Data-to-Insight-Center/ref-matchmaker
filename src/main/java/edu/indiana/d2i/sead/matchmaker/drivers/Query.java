package edu.indiana.d2i.sead.matchmaker.drivers;

import edu.indiana.d2i.sead.matchmaker.service.messaging.MatchmakerENV;

public class Query extends MetaDriver {

	public Query(MatchmakerENV env, String message){
		super(env, message);
	}
	
	public String exec() {
		return "{sucess:true,response:"+candidateList.CandidateList() +"}";
	} 

	
}
