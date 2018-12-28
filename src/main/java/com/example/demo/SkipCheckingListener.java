package com.example.demo;

import javax.batch.runtime.StepExecution;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class SkipCheckingListener extends StepExecutionListenerSupport {
	
	public ExitStatus afterStep(StepExecution stepExecution,final Number number) {
		
		String exitCode = stepExecution.getExitStatus();
		String exitCodeA= stepExecution.getStepName();
		
		if(exitCodeA.equals("stepA")) {
			
		}
		if (exitCode.equals(ExitStatus.COMPLETED.getExitCode()) &&(number.getNumber2()==2)) {
			return new ExitStatus("COMPLETED WITH 2");
		} else {
			if (exitCode.equals(ExitStatus.COMPLETED.getExitCode()) &&( number.getNumber1()==3 || number.getNumber2()==3||number.getNumber3()==3)) {
				return new ExitStatus("COMPLETED WITH 3");
		}
			else {
				return null; 
			}
		}
		
	}
}