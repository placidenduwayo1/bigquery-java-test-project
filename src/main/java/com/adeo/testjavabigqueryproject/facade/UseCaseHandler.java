package com.adeo.testjavabigqueryproject.facade;

import com.adeo.testjavabigqueryproject.configuration.config.BigQueryConfiguration;
import com.adeo.testjavabigqueryproject.configuration.config.BigQueryConfigurationImpl;
import com.adeo.testjavabigqueryproject.configuration.check.Checking;
import com.adeo.testjavabigqueryproject.configuration.check.CheckingImpl;
import com.adeo.testjavabigqueryproject.values.Values;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class UseCaseHandler implements Handler {
    @Override
    public void handle() throws IOException {
        Checking checking = new CheckingImpl();
        BigQueryConfiguration bigQueryConfiguration =new BigQueryConfigurationImpl();
        BigQuery bigQuery = bigQueryConfiguration.createBigQuery();
        Dataset dataset;
        if (checking.databaseExists()) {
            log.info("dataset already exits");
        }
        else {
            dataset = bigQueryConfiguration.createDataset(bigQuery);
            log.info("dataset {} created successfully", dataset);
        }

        Table table;
        if(checking.tableExists(Values.TABLE_NAME)){
            log.info("table already exists");
        }
        else {
            table = bigQueryConfiguration.createTable(bigQuery);
            log.info("table {} successfully created", table);
        }

    }
}
