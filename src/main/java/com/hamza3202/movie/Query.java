package com.hamza3202.movie;


import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Introspected
public class Query
{
    @NotNull
    @NotBlank
    private String title;

    @Pattern(regexp = "19[5][0-9]|2020|20[0-1][0-9]", message = "Field must have valid year between 1950-1920.")
    private String year;

    @Pattern(regexp = "short|full")
    private String plot = "short";

    private final String type = "movie";

    public String getTitle()
    {
        return title;
    }

    public String getYear()
    {
        return year;
    }

    public String getPlot()
    {
        return plot;
    }

    public String getType()
    {
        return type;
    }
}
