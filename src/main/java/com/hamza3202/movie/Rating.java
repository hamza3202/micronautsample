package com.hamza3202.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class Rating
{
    private String source;
    private String value;

    public Rating(@JsonProperty("Source") String source, @JsonProperty("Value") String value)
    {
        this.source = source;
        this.value = value;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
