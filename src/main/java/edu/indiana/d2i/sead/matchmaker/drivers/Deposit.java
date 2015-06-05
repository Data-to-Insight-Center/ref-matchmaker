package edu.indiana.d2i.sead.matchmaker.drivers;

import edu.indiana.d2i.sead.matchmaker.service.messaging.MatchmakerENV;

public class Deposit extends MetaDriver {
	public Deposit(MatchmakerENV env, String message){
		super(env, message);
	}
	
	public String exec() {
		return "{sucess:false,response:\"not implemented\"}";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
