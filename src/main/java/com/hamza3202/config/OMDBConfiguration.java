package com.hamza3202.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(OMDBConfiguration.PREFIX)
@Requires(property = OMDBConfiguration.PREFIX)
public class OMDBConfiguration
{
    static final String PREFIX = "app.client.omdb";
    public static final String URL = "https://www.omdbapi.com/";
    public static final String URI = "/";
    public static final String PARAM_TITLE = "t";
    public static final String PARAM_YEAR = "y";
    public static final String PARAM_PLOT = "plot";
    public static final String PARAM_API_KEY = "apikey";

    private String apiKey;

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }
}
