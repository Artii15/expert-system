package com.sample;

import org.drools.*;
import org.drools.builder.*;
import org.drools.logger.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class ReasoningModule implements Runnable {
	
	private KnowledgeBase kbase;
	private StatefulKnowledgeSession ksession;
	private KnowledgeRuntimeLogger logger;
	
	public ReasoningModule() {
		initKnowledgeBase();
	}
	
	private void initKnowledgeBase() {
    	try {
            // load up the knowledge base
            kbase = readKnowledgeBase();
            ksession = kbase.newStatefulKnowledgeSession();
            logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

	@Override
	public void run() {
		ksession.fireAllRules();
        logger.close();
	}
}
