package com.example.demo.tasklet;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.demo.FileUtils;
import com.example.demo.JobCompletionNotificationListener;
import com.example.demo.Number;

public class TaskletForStepA implements Tasklet, StepExecutionListener {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	private List<Number> listNumber;
	private FileUtils fu;
	private static String taskletStatus;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();

		log.debug("number Processor initialized.");

	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Number> numbers = Reader.readDataLineByLine();
		

		if (numbers.get(0).getNumber1() == 1) {
			taskletStatus = "STOP";
		} else {
			if(numbers.get(0).getNumber1()==3) {
				taskletStatus="NEXTSTEPC";
			}else {
			taskletStatus = "CONTINUE";
		}
		}
		
		return RepeatStatus.FINISHED;
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {


		if (taskletStatus == "STOP") {
			log.info("number Processor ended.");
			return ExitStatus.FAILED;
			
		} else {
			if(taskletStatus=="NEXTSTEPC") {
				return ExitStatus.NOOP;
			}
			log.info("number Processor continue.");

			return ExitStatus.COMPLETED;

		}

	}

}
