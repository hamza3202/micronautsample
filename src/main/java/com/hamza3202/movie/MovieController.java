package com.hamza3202.movie;

import com.hamza3202.client.omdb.OMDBClient;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.validation.Valid;
import java.util.List;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
public class MovieController
{
    private final OMDBClient omdbClient;

    public MovieController(OMDBClient omdbClient)
    {
        this.omdbClient = omdbClient;
    }

    @Get(value = "getMovie{?movieQuery*}", produces = MediaType.APPLICATION_JSON)
    public List<Movie> getMovie(@Valid final Query movieQuery)
    {
        return omdbClient.fetch(movieQuery);
    }
}
