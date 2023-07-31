package com.adeo.testjavabigqueryproject;
import com.adeo.testjavabigqueryproject.configuration.check.CheckingImpl;
import com.adeo.testjavabigqueryproject.configuration.config.BigQueryConfigurationImpl;
import com.adeo.testjavabigqueryproject.facade.UseCaseHandler;
import com.adeo.testjavabigqueryproject.jobs.BigQueryJobsImpl;
import com.adeo.testjavabigqueryproject.query_job_exceptions.exceptions.NoQueryJobExists;
import com.adeo.testjavabigqueryproject.query_job_exceptions.exceptions.QueryJobError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication(scanBasePackages = {"com.adeo.testjavabigqueryproject"})
@Slf4j
public class TestJavaBigqueryProjectApplication {

    public static void main(String[] args) throws IOException, NoQueryJobExists, QueryJobError, InterruptedException {
        new SpringApplication(TestJavaBigqueryProjectApplication.class)
                .setBannerMode(Banner.Mode.OFF);
        new CheckingImpl();
        new UseCaseHandler().handle();
        new BigQueryJobsImpl(new BigQueryConfigurationImpl()).runJob();
    }
}
