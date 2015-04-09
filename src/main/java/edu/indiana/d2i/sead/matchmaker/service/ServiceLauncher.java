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
# File:  ServiceLauncher.java
# Description:  Utility class for initializing service and launching
#   internal threads and daemons
#
# -----------------------------------------------------------------
# 
*/


package edu.indiana.d2i.sead.matchmaker.service;

import edu.indiana.d2i.sead.matchmaker.service.messaging.*;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



/**
 * @author Yuan Luo
 *
 */
public class ServiceLauncher {
    
    private static boolean initialized = false;
    private static PropertyReader propertyReader = null;
    private static AbstractENV env = null;
    private static final Logger log = Logger.getLogger(ServiceLauncher.class);
    
    public static void start(String propertiesPath) throws ClassNotFoundException {
        if (!initialized) {
            propertyReader = PropertyReader.getInstance(propertiesPath);
            PropertyConfigurator.configure(propertyReader.getProperty("log4j.properties.path"));
            
            if (log.isDebugEnabled()) log.debug("Matchmaker started");
			initialized = true;
            
        }
    }
    
    public static void shutdown() {
        if (initialized) {
            if (log.isDebugEnabled()) log.debug("Matchmaker stopped");
            initialized = false;
        }
        
    }
    

    
    public static boolean startMessageReceiverDaemon() {



        String MessagingUsername= propertyReader.getProperty("messaging.username");

        String MessagingPassword= propertyReader.getProperty("messaging.password");

        String MessagingHostname= propertyReader.getProperty("messaging.hostname");

        int MessagingHostPort= Integer.parseInt(propertyReader.getProperty("messaging.hostport"));

        String MessagingVirtualHost= propertyReader.getProperty("messaging.virtualhost");

        String MessagingExchangeName= propertyReader.getProperty("messaging.exchangename")==null? "MatchmakerExchange": propertyReader.getProperty("messaging.exchangename");

        String MessagingQueueName= propertyReader.getProperty("messaging.queuename")==null? "MatchmakerQueue": propertyReader.getProperty("messaging.queuename");

        String MessagingRoutingKey= propertyReader.getProperty("messaging.routingkey")==null? "MatchmakerKey": propertyReader.getProperty("messaging.routingkey");
        
        int MessagingRetryInterval= Integer.parseInt(propertyReader.getProperty("messaging.retry.interval")==null? "5": propertyReader.getProperty("messaging.retry.interval"));
        
        int MessagingRetryThreshold= Integer.parseInt(propertyReader.getProperty("messaging.retry.threshold")==null? "5": propertyReader.getProperty("messaging.retry.threshold"));

        MessagingDaemonsConfig msgdmconf=new MessagingDaemonsConfig();
        
        msgdmconf.setNumberOfMessagingDaemons(Integer.parseInt(propertyReader.getProperty("messaging.daemon")==null? "1": propertyReader.getProperty("messaging.daemon")));
        
        MessagingDaemons msgrd;

        MessagingConfig msgconf;

        try {

            msgconf=new MessagingConfig(MessagingUsername, MessagingPassword, MessagingHostname, MessagingHostPort, MessagingVirtualHost, MessagingExchangeName, MessagingQueueName, MessagingRoutingKey, MessagingRetryInterval, MessagingRetryThreshold);

            msgrd = new MessagingDaemons(msgdmconf, msgconf, env);

            msgrd.start();

        } catch (IOException e) {

            e.printStackTrace();
            return false;

        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
        return true;

    }


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
            	shutdown();
            }
           
        } catch (Throwable e) {
        	log.fatal("Unable to launch service", e);
        	shutdown();
        }
    }
    
}

