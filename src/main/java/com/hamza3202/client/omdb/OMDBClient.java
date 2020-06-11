package com.hamza3202.client.omdb;

import com.hamza3202.config.OMDBConfiguration;
import com.hamza3202.movie.Movie;
import com.hamza3202.movie.Query;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.reactivex.Flowable;

import javax.inject.Singleton;
import java.net.URI;
import java.util.List;

@Singleton
public class OMDBClient
{
    private final RxHttpClient client;
    private final OMDBConfiguration config;

    public OMDBClient(@Client(OMDBConfiguration.URL) RxHttpClient client, OMDBConfiguration config)
    {
        this.client = client;
        this.config = config;
    }

    public List<Movie> fetch(Query query)
    {
        URI uri = getURI(query);

        HttpRequest<?> request = HttpRequest.GET(uri);
        Flowable<List<Movie>> flowable = client.retrieve(request, Argument.listOf(Movie.class));
        return flowable.firstElement().blockingGet();
    }

    private URI getURI(Query query)
    {
        return UriBuilder
                .of(OMDBConfiguration.URI)
                .queryParam(OMDBConfiguration.PARAM_TITLE, query.getTitle())
                .queryParam(OMDBConfiguration.PARAM_YEAR, query.getYear())
                .queryParam(OMDBConfiguration.PARAM_PLOT, query.getPlot())
                .queryParam(OMDBConfiguration.PARAM_API_KEY, config.getApiKey())
                .build();
    }
}
