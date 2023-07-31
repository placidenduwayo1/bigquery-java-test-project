package com.adeo.testjavabigqueryproject.configuration.config;

import com.adeo.testjavabigqueryproject.values.Values;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Configuration
@Slf4j
@RequiredArgsConstructor
public class BigQueryConfigurationImpl implements BigQueryConfiguration {

    private Schema createTableSchema() {
        return Schema.of(
                Field.of("id", StandardSQLTypeName.STRING),
                Field.of("firstname", StandardSQLTypeName.STRING),
                Field.of("lastname", StandardSQLTypeName.STRING),
                Field.of("email", StandardSQLTypeName.STRING),
                Field.of("created_date", StandardSQLTypeName.TIMESTAMP)
        );
    }

    private TableId getTableId() {
        return TableId.of(Values.DATASET_ID, Values.TABLE_NAME);
    }

    private TableDefinition getTableDefinition() {
        return StandardTableDefinition.of(createTableSchema());
    }

    private TableInfo getTableInfo() {
        return TableInfo.of(getTableId(), getTableDefinition());
    }

    @Override
    public BigQuery createBigQuery() throws IOException {
        return BigQueryOptions.newBuilder()
                .setCredentials(getGoogleCredentials())
                .setProjectId(Values.PROJECT_ID)
                .build().getService();
    }

    @Override
    public Table createTable(BigQuery bigQuery) {
        Table table = null;
        try {
            table = bigQuery.create(getTableInfo());
        } catch (BigQueryException exception) {
            exception.printStackTrace();
        }

        return table;
    }

    private GoogleCredentials getGoogleCredentials() throws IOException {
        File file = new File(Values.CREDENTIALS_FILE);
        FileInputStream fileInputStream = new FileInputStream(file);
        return ServiceAccountCredentials.fromStream(fileInputStream);
    }

    private DatasetInfo getDatasetInfo() {
        return DatasetInfo
                .newBuilder(Values.DATASET_ID)
                .build();
    }

    @Override
    public Dataset createDataset(BigQuery bigQuery) {
        return bigQuery.create(getDatasetInfo());
    }
}
