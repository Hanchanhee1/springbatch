package com.chanhee.batch;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.chanhee.entity.AfterEntity;
import com.chanhee.repository.AfterRepository;
import com.mysql.cj.result.Row;

@Configuration
public class FourthBatch {
	
	private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final AfterRepository afterRepository;

    public FourthBatch(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, AfterRepository afterRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.afterRepository = afterRepository;
    }
    
    @Bean
    public Job fourthJob() {

        System.out.println("fourth job");

        return new JobBuilder("fourthJob", jobRepository)
                .start(fourthStep())
                .build();
    }
    
    @Bean
    public Step fourthStep() {

        return new StepBuilder("fourthStep", jobRepository)
                .<Row, AfterEntity> chunk(10, platformTransactionManager)
                .reader(excelReader())
                .processor(fourthProcessor())
                .writer(fourthAfterWriter())
                .build();
    }
    
    private ItemProcessor<? super Row, ? extends AfterEntity> fourthProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	private ItemWriter<? super AfterEntity> fourthAfterWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
    public ItemStreamReader<Row> excelReader() {

        try {
            return new ExcelRowReader("‪C:\\Users\\mh981\\Downloads\\q321.xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
