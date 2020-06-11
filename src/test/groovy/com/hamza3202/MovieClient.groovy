package com.hamza3202

import com.hamza3202.movie.Movie
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("/")
interface MovieClient {

    @Consumes(MediaType.APPLICATION_JSON)
    @Get(value = "getMovie")
    List<Movie> getMovie(@Header String authorization, @QueryValue String title)

    @Consumes(MediaType.APPLICATION_JSON)
    @Get("getMovie")
    List<Movie> getMovie(@Header String authorization,
                    @QueryValue String title,
                    @QueryValue String year)

    @Consumes(MediaType.APPLICATION_JSON)
    @Get("getMovie")
    List<Movie> getMovie(@Header String authorization,
                    @QueryValue String title,
                    @QueryValue String year,
                    @QueryValue String plot)
}
