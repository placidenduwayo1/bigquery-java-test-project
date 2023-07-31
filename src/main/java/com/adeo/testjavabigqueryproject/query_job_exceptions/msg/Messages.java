package com.adeo.testjavabigqueryproject.query_job_exceptions.msg;

public enum Messages {
    no_queryjob_exist("job no longer exists"),
    query_job_error("");

    private final String mgs;

    Messages(String mgs) {
        this.mgs = mgs;
    }

    public String getMgs() {
        return mgs;
    }
}
