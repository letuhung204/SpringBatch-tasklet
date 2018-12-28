package com.example.demo.tasklet;


import java.util.List;

import javax.batch.runtime.BatchStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.demo.JobCompletionNotificationListener;
import com.example.demo.Number;


public class TasletForStepB implements Tasklet,StepExecutionListener {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	private static String taskletStatus;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Number> numbers = Reader.readDataLineByLine();

		if (numbers.get(0).getNumber2() == 1) {
			taskletStatus = "CONTINUE";
		} else {
			taskletStatus = "STOP";
		}
		
		
		return RepeatStatus.FINISHED;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
	
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (taskletStatus == "STOP") {
			log.info("number Processor ended.");
			return ExitStatus.FAILED;
		} else {
			log.info("number Processor continue.");

			return ExitStatus.COMPLETED;

		}
	}

}
