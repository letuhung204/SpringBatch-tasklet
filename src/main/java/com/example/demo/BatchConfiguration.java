package com.example.demo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.demo.tasklet.TaskletForStepA;
import com.example.demo.tasklet.TaskletForStepC;
import com.example.demo.tasklet.TasletForStepB;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
	Number number;

	@Bean
	public FlatFileItemReader<Number> reader() {
		return new FlatFileItemReaderBuilder<Number>().name("numberItemReader")
				.resource(new ClassPathResource("sample-data.csv")).delimited()
				.names(new String[] { "number1", "number2", "number3" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Number>() {
					{
						setTargetType(Number.class);
					}
				}).build();
	}

	@Bean
	public NumberItemProcessor processor() {
		return new NumberItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Number> writer() {
		 JdbcBatchItemWriter<Number> csvAnimeWriter = new JdbcBatchItemWriter<Number>();
		 csvAnimeWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Number>());
		 csvAnimeWriter.setSql("INSERT INTO number (number1, number2, number3) VALUES (:number1, :number2, :number3)");
		 csvAnimeWriter.setDataSource(dataSource);
	        return csvAnimeWriter;
	}

	
	@Bean
	public Job testJob(JobCompletionNotificationListener listen) {
		
		Flow flow1 = new FlowBuilder<Flow>("flow1")
                .start(stepA())
                	.on("FAILED").end()
                	.on("NOOP").to(stepC()).on("COMPLETED").end()
                	.from(stepA())
                	.on("COMPLETED").to(stepB())
                		.on("FAILED").end()
                		.on("COMPLETED").to(stepC()).on("COMPLETED").end()
                .build();
		 
//		Flow flow2 = new FlowBuilder<Flow>("flow2")
//                .start(stepA()).on("FAILED").end()
//                	.on("COMPLETED").end()
//                	.on("NOOP").to(stepC())
//                		.on("COMPLETED").end()
//                .build();
		
		 return this.jobBuilderFactory.get("testJob")
                 .start(flow1)/*.on("COMPLETED").to(flow2)*/
                 .end()
                 .build();
		
	}
	
	@Bean
	public Step stepA() {
		return stepBuilderFactory.get("stepA")
				.tasklet(taskletForStepA()).build();
	}
	@Bean
	public TaskletForStepA taskletForStepA() {
		TaskletForStepA taskletA = new TaskletForStepA();
		
		return taskletA;
	}
	@Bean
	public Step stepB() {
	        return stepBuilderFactory.get("stepB")
	                                .tasklet(taskletForStepB())
	                                .build();
	}
	@Bean
	public TasletForStepB taskletForStepB() {
		TasletForStepB taskletB = new TasletForStepB();
		
		return taskletB;
	}
	
	@Bean
	public Step stepC() {
	        return stepBuilderFactory.get("stepC")
	                                .tasklet(taskletForStepC())
	                                .build();
	}
	@Bean
	public TaskletForStepC taskletForStepC() {
		TaskletForStepC taskletC = new TaskletForStepC();
		
		return taskletC;
	}


}
