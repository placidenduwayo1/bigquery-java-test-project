package com.adeo.testjavabigqueryproject.configuration.config;

import com.google.cloud.bigquery.*;

import java.io.IOException;

public interface BigQueryConfiguration {
    BigQuery createBigQuery() throws IOException;
    Table createTable(BigQuery bigQuery);
    Dataset createDataset(BigQuery bigQuery);
}
