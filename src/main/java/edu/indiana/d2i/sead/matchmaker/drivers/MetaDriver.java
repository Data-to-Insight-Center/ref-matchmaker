/*
#
# Copyright 2015 The Trustees of Indiana University
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------
#
# Project: Matchmaker Service
# File:  MetaDriver.java
# Description:  Preparing for input and initiate matchmaking.
#
# -----------------------------------------------------------------
# 
*/
package edu.indiana.d2i.sead.matchmaker.drivers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.indiana.d2i.sead.matchmaker.core.MatchMaker;
import edu.indiana.d2i.sead.matchmaker.core.MatchMakingList;
import edu.indiana.d2i.sead.matchmaker.core.POJOGenerator;

/**
 * @author yuanluo
 *
 */
public class MetaDriver {
	//TODO: query PDT (when available) to get profiles, instead of using local copies.
	MatchMakingList candidateList = null;
	
	public MetaDriver(String message){
		POJOGenerator reposGen = null,personGen = null,researchObjectGen = null;
		try {
			reposGen = new POJOGenerator("edu.indiana.d2i.sead.matchmaker.pojo.Repository");
			reposGen.fromPath("/Users/yuanluo/WorkZone/workspace/matchmaker-master/profile/repositories.json");
			personGen = new POJOGenerator("edu.indiana.d2i.sead.matchmaker.pojo.Person");
			personGen.fromPath("/Users/yuanluo/WorkZone/workspace/matchmaker-master/profile/person.json");
			researchObjectGen=new POJOGenerator("edu.indiana.d2i.sead.matchmaker.pojo.ResearchObject");
			researchObjectGen.fromString(message);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	 
		Object[] repositories;
		Object person;
		Object researchObject;
		File[] rulefiles;
		String[] classNames;
		try {
			repositories = (Object[]) reposGen.generate();
			person = (Object) personGen.generate();
			researchObject= (Object) researchObjectGen.generate();
			
			rulefiles = new File[1];
			rulefiles[0]=new File("/Users/yuanluo/WorkZone/workspace/matchmaker-master/plugins/ruleset1/target/ruleset1-1.0.0.jar");
			candidateList=(MatchMakingList) Class.forName("edu.indiana.d2i.sead.matchmaker.custom.ruleset1.Ruleset1MatchMakingList").getConstructor(ArrayNode.class).newInstance((ArrayNode)reposGen.getJsonTree());
			classNames=new String[1];
			classNames[0]=new String("edu.indiana.d2i.sead.matchmaker.custom.ruleset1.Ruleset1Utility");
			new MatchMaker().basicGo(System.out, rulefiles, classNames, candidateList, repositories, person, researchObject);
			
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	


	public String exec() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}




}
