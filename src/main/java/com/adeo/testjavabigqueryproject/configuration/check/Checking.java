package com.adeo.testjavabigqueryproject.configuration.check;

import java.io.IOException;

public interface Checking {
    boolean databaseExists() throws IOException;
    boolean tableExists(final String table);
}
