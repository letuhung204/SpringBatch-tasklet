package com.example.demo;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
 
public class FlowDecision implements JobExecutionDecider {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
    	
    	Number number= new Number(1,2,3);
    	logger.info("spring batch");
    	
    	if(number.getNumber2()==2|| number.getNumber3()==3){
    		return FlowExecutionStatus.COMPLETED;
    	}
    	return FlowExecutionStatus.FAILED;
    }


}