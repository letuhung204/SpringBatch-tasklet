package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.FAILED) {
			log.error("job thất bại");
		};
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info(" Job hoàn thành ! ");

			jdbcTemplate.query("SELECT number1, number2,number3 FROM number",
				(rs, row) -> new Number(
					rs.getInt(1),
					rs.getInt(2),
					rs.getInt(3))
			).forEach(number -> log.info("tìm thấy <" + number + "> trong database."));
		}
	}
}
