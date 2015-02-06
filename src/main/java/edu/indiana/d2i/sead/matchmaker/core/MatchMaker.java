package edu.indiana.d2i.sead.matchmaker.core;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MatchMaker {

    public void basicGo(PrintStream out) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession();
        kSession.insert(new String("Matchmaking Testing"));
        MatchMakingList initList=new MatchMakingList();
        kSession.insert(initList);
        kSession.fireAllRules();
    }


    public static void main(String[] args) {
        new MatchMaker().basicGo(System.out);
    }

}
