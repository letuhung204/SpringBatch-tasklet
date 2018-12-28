package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class NumberItemProcessor implements ItemProcessor<Number, Number> {

    private static final Logger log = LoggerFactory.getLogger(NumberItemProcessor.class);

    @Override
    public Number process( Number number) throws Exception {
    	
        log.info("demo spring batch");

        return number;
    }
    
   

}
