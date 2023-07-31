package com.adeo.testjavabigqueryproject.configuration.check;

import com.adeo.testjavabigqueryproject.configuration.config.BigQueryConfiguration;
import com.adeo.testjavabigqueryproject.configuration.config.BigQueryConfigurationImpl;
import com.adeo.testjavabigqueryproject.values.Values;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.Table;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;


@Slf4j
public class CheckingImpl implements Checking{
    private BigQuery bigQueryClient ;
    BigQueryConfiguration bigQueryConfiguration;

    public CheckingImpl( ) {
        this.bigQueryConfiguration = new BigQueryConfigurationImpl();
    }

    @Override
    public boolean databaseExists() throws IOException {
        bigQueryClient = bigQueryConfiguration.createBigQuery();
        Dataset dataset = bigQueryClient.getDataset(Values.DATASET_ID);
        return Objects.nonNull(dataset) && dataset.exists();
    }

    @Override
    public boolean tableExists(String tableName) {
        Table table = bigQueryClient.getTable(Values.DATASET_ID, Values.TABLE_NAME);
        return Objects.nonNull(table) && table.exists();
    }
}
