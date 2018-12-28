package com.example.demo.tasklet;



import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

import com.example.demo.Number;
import com.opencsv.CSVReader;

public class Reader extends FlatFileItemReader<Number> {
	 
	 public Reader(Resource resource) {
	 
	 super();
	 
	 setResource(resource);
	 
	 DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	 lineTokenizer.setNames(new String[] { "number1", "number2", "number3" });
	 lineTokenizer.setDelimiter(",");
	         lineTokenizer.setStrict(false);
	         
	         BeanWrapperFieldSetMapper<Number> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	                fieldSetMapper.setTargetType(Number.class);
	 
	 DefaultLineMapper<Number> defaultLineMapper = new DefaultLineMapper<>();
	 defaultLineMapper.setLineTokenizer(lineTokenizer);
	 defaultLineMapper.setFieldSetMapper(fieldSetMapper);
	 setLineMapper(defaultLineMapper);
	 }
	 
	// Java code to illustrate reading a 
	// CSV file line by line 
	public static ArrayList<Number> readDataLineByLine() 
	{ 
	  
	    try { 
	    	Number number;
	    	ArrayList<Number> listNumber = new ArrayList<Number>();
	    	File file = new File("D:\\CMC_hung_CaNhan\\Spring\\demoBatch\\src\\main\\resources\\sample-data.csv");
	        // Create an object of filereader 
	        // class with CSV file as a parameter. 
	        FileReader filereader = new FileReader(file); 
	  
	        // create csvReader object passing 
	        // file reader as a parameter 
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord; 
	  
	        // we are going to read data line by line 
	        while ((nextRecord = csvReader.readNext()) != null) { 
	        	number = new Number(Integer.parseInt(nextRecord[0]), Integer.parseInt(nextRecord[1]), Integer.parseInt(nextRecord[2]));
		        listNumber.add(number); 
	        }
	        
	        csvReader.close();
	        
	        return listNumber;
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	        return null;
	    } 
	} 
	}
