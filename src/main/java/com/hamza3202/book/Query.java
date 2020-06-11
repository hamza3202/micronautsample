package com.hamza3202.book;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Introspected
public class Query
{
    @NotNull
    @Pattern(regexp = "ISBN:[0-9]{10,13}", message = "Field value must start with ISBN: and may only contain 10-13 " +
            "digits afterwards")
    private String isbn;

    public String getIsbn()
    {
        return isbn;
    }

}
