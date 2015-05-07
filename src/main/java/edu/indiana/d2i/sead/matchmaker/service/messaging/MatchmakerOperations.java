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
# Unless required by applicable law or areed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------
#
# Project: Matchmaker
# File:  MatchmakerOperations.java
# Description:  Definition of service operations
#
# -----------------------------------------------------------------
# 
*/

package edu.indiana.d2i.sead.matchmaker.service.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.indiana.d2i.sead.matchmaker.drivers.MetaDriver;

/**
 * @author Yuan Luo
 */

public class MatchmakerOperations {
	public static final Log l = LogFactory.getLog(MatchmakerOperations.class);
	public static final String  ERROR_STRING = "SERVER ERROR";


	
	public enum OperationType {
		INFOMATION,
		BROKER
	}
	
	
	public String exec(String message){
		//return "{success:true,response:\"Sample Response Message\"}";
		MetaDriver md = new MetaDriver();
		return md.exec(message);
	}

}
