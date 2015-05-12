package edu.indiana.d2i.sead.matchmaker.drivers;

public class Query extends MetaDriver {

	public Query(String message) {
		super(message);
	}
	
	public String exec() {
		return "{sucess:true,response:"+candidateList.CandidateList() +"}";
	} 

	
}
