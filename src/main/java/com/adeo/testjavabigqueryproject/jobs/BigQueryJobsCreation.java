package com.adeo.testjavabigqueryproject.jobs;

import com.adeo.testjavabigqueryproject.query_job_exceptions.exceptions.NoQueryJobExists;
import com.adeo.testjavabigqueryproject.query_job_exceptions.exceptions.QueryJobError;

import java.io.IOException;

public interface BigQueryJobsCreation {
    void runJob() throws NoQueryJobExists, QueryJobError, InterruptedException, IOException;
}
