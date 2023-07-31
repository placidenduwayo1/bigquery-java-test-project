package com.adeo.testjavabigqueryproject.jobs;

import com.adeo.testjavabigqueryproject.configuration.config.BigQueryConfiguration;
import com.adeo.testjavabigqueryproject.entity.Model;
import com.adeo.testjavabigqueryproject.query_job_exceptions.exceptions.NoQueryJobExists;
import com.adeo.testjavabigqueryproject.query_job_exceptions.exceptions.QueryJobError;
import com.adeo.testjavabigqueryproject.query_job_exceptions.msg.Messages;
import com.adeo.testjavabigqueryproject.values.Values;
import com.google.cloud.bigquery.*;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;
@Slf4j
public class BigQueryJobsImpl implements BigQueryJobsCreation{
    private final BigQueryConfiguration bigQueryConfiguration;

    public BigQueryJobsImpl(BigQueryConfiguration bigQueryConfiguration) {
        this.bigQueryConfiguration = bigQueryConfiguration;
    }

    @Override
    public void runJob() throws NoQueryJobExists, QueryJobError, InterruptedException, IOException {
        String id0 = UUID.randomUUID().toString();
        String firstname0 ="Placide";
        String lastname0 ="Nduwayo";
        String email0 =firstname0+"."+lastname0+Values.DOMAIN_NAME;

        String id1 = UUID.randomUUID().toString();
        String firstname1 ="Vianney";
        String lastname1 ="Leman";
        String email1 =firstname1+"."+lastname1+Values.DOMAIN_NAME;
        String id2 = UUID.randomUUID().toString();
        String firstname2 ="Guillaume";
        String lastname2 ="Debaisieux";
        String email2 =firstname2+"."+lastname2+Values.DOMAIN_NAME;
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());


        String request = "INSERT INTO `influential-hub-393514.engineers_dataset.engineers_table` " +
                "VALUES ('"+id0+"','"+firstname0+"','"+lastname0+"','"+email0+"','"+createdDate+"')," +
                " ('"+id1+"','"+firstname1+"','"+lastname1+"','"+email1+"','"+createdDate+"'), " +
                "('"+id2+"','"+firstname2+"','"+lastname2+"','"+email2+"','"+createdDate+"')";

        BigQuery bigQuery = bigQueryConfiguration.createBigQuery();
        createJob(request, bigQuery);

        String frankFirstname ="Franck";
        String franckLastname ="Marlard";
        String franckEmail = frankFirstname+"."+franckLastname+Values.DOMAIN_NAME;

        Model model = new Model(UUID.randomUUID().toString(), frankFirstname, franckLastname, franckEmail, createdDate);
        TableId tableId = TableId.of(Values.DATASET_ID, Values.TABLE_NAME);
        Map<String, Object> mapRow = createRow(model);
        InsertAllResponse response = bigQuery.insertAll(InsertAllRequest.newBuilder(tableId).addRow(mapRow).build());
        log.info("list of errors: {} ", response);

    }
    private void createJob(String request, BigQuery bigQuery) throws InterruptedException, NoQueryJobExists, QueryJobError{

        QueryJobConfiguration queryJobConfiguration = QueryJobConfiguration
                .newBuilder(request)
                .build();
        Job queryJob = bigQuery.create(JobInfo.of(queryJobConfiguration));
        queryJob = queryJob.waitFor();

        if(queryJob==null){
            throw new NoQueryJobExists(Messages.query_job_error.getMgs());
        }
        else if (queryJob.getStatus().getError()!=null){
            throw new QueryJobError(Messages.query_job_error.getMgs());
        }
    }

    private Map<String, Object> createRow(Model model){
        return Map.of(
                "id", model.getId(),
                "firstname", model.getFirstname(),
                "lastname", model.getLastname(),
                "email", model.getEmail(),
                "created_date", model.getCreatedDate()
        );
    }
}
