package edu.indiana.d2i.sead.matchmaker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class MatchMakingList {
	
	int PRIORITY_DEFAULT=0;
	int PRIORITY_PREFERRED=1;
	int WEIGHT_DEFAULT=0;
	//String MatchmakingSchema ="{\"priority\":\"Integer\", \"weight\":\"Integer\"}";
	
	private HashMap<String, HashMap<String, Integer>> candidateList;
	
	/*
	 * Initiate candidateList, add all repositories.
	 * */
	public MatchMakingList(){
		this.candidateList = new HashMap<String, HashMap<String, Integer>>();
				
		//TODO: read all repo name from a catalog
		HashMap<String, Integer> paramsA = new HashMap<String, Integer>();
		paramsA.put("priority", PRIORITY_DEFAULT);
		paramsA.put("weight", WEIGHT_DEFAULT);
		HashMap<String, Integer> paramsB = new HashMap<String, Integer>();
		paramsB.put("priority", PRIORITY_DEFAULT);
		paramsB.put("weight", WEIGHT_DEFAULT);
		HashMap<String, Integer> paramsC = new HashMap<String, Integer>();
		paramsC.put("priority", PRIORITY_DEFAULT);
		paramsC.put("weight", WEIGHT_DEFAULT);
		HashMap<String, Integer> paramsD = new HashMap<String, Integer>();
		paramsD.put("priority", PRIORITY_DEFAULT);
		paramsD.put("weight", WEIGHT_DEFAULT);
		HashMap<String, Integer> paramsE = new HashMap<String, Integer>();
		paramsE.put("priority", PRIORITY_DEFAULT);
		paramsE.put("weight", WEIGHT_DEFAULT);
		
		this.candidateList.put("A", paramsA);
		this.candidateList.put("B", paramsB);
		this.candidateList.put("C", paramsC);
		this.candidateList.put("D", paramsD);
		this.candidateList.put("E", paramsE);
	}
	
	/*
	 * Some rules may restrict candidates to a smaller set, "restrictCandidates". 
	 * Therefore, get intersections of two sets: candidateList and restrictCandidates.
	 * */
	public void restricted(Set<String> restrictCandidates){
		Set<String> newCandidateKeys=this.candidateList.keySet();
		newCandidateKeys.retainAll(restrictCandidates);
	}
	
	/*
	 * Some rules may indicate a "not allowed" list. 
	 * Therefore, get the relative complement of notAllowedList in candidateList
	 * */
	public void notAllowed(Set<String> notAllowedList){
		Set<String> candidateKeySet=this.candidateList.keySet();
		Iterator iterator = candidateKeySet.iterator();
      	while (iterator.hasNext()){
      		if(notAllowedList.contains(iterator.next())){
      			iterator.remove();
      		}
      	}
		
	}
	
	/*
	 * Some rules may indicate preferred list. 
	 * */
	public void preferred(Set<String> PreferredList){
		for(String candidate : PreferredList){
			if(this.candidateList.containsKey(candidate)){
				HashMap<String, Integer> params= this.candidateList.get(candidate);
				params.replace("priority", PRIORITY_PREFERRED);
			}
		}
		
	}
	
	public void setWeight(String candidate, int weight){
		if (this.candidateList.containsKey(candidate)){
			//System.out.println(candidate+" "+weight);
			HashMap<String, Integer> params= this.candidateList.get(candidate);
			params.replace("weight", weight);
		}
	}
	
	public void addWeight(String candidate, int weight){
		if (this.candidateList.containsKey(candidate)){
			//System.out.println(candidate+" "+weight);
			HashMap<String, Integer> params= this.candidateList.get(candidate);
			params.replace("weight", params.get("weight").intValue() + weight);
		}
	}
	
	public void reduceWeight(String candidate, int weight){
		if (this.candidateList.containsKey(candidate)){
			//System.out.println(candidate+" "+weight);
			HashMap<String, Integer> params= this.candidateList.get(candidate);
			params.replace("weight", params.get("weight").intValue() - weight);
		}
	}
	
	public HashMap<String, HashMap<String, Integer>> getCandidateList(){
		return this.candidateList;
	}
	
	public void printCandidateList(){
		ObjectMapper mapper = new ObjectMapper();
        String matchmaking;
		try {
			matchmaking = mapper.writeValueAsString(this.candidateList);
			System.out.println(matchmaking);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws JsonProcessingException {
		// TODO Auto-generated method stub
		MatchMakingList mml= new MatchMakingList();
		
		mml.addWeight("B",3);
		mml.printCandidateList();
		mml.reduceWeight("B",1);
		mml.printCandidateList();
		mml.setWeight("C",10);
		mml.printCandidateList();
		Set<String> notAllowedList= new HashSet<String>();
		notAllowedList.add("C");
		mml.notAllowed(notAllowedList);
		mml.printCandidateList();
		Set<String> restrictedList= new HashSet<String>();
		restrictedList.add("A");
		restrictedList.add("B");
		restrictedList.add("D");
		restrictedList.add("F");
		mml.restricted(restrictedList);
		mml.printCandidateList();
		Set<String> preferredList= new HashSet<String>();
		preferredList.add("A");
		preferredList.add("B");
		mml.preferred(preferredList);
		mml.printCandidateList();
		
		
		
		
	}

}
