package com.example.demo.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.demo.JobCompletionNotificationListener;

public class TaskletForStepC implements Tasklet {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		
		log.info("nội dung thuc hiện công việc của step C");
		// thuc hien cong viec cua step C
		
		log.info("nội dung công việc hoàn tất");
		return RepeatStatus.FINISHED;
	}

}
