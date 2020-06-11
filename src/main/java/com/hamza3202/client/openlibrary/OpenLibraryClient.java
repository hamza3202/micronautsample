package com.hamza3202.client.openlibrary;

import com.hamza3202.book.Book;
import com.hamza3202.book.Query;
import com.hamza3202.config.OpenLibraryConfiguration;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Singleton;
import java.net.URI;
import java.util.HashMap;

@Singleton
public class OpenLibraryClient
{

    private final RxHttpClient client;

    public OpenLibraryClient(@Client(OpenLibraryConfiguration.URL) RxHttpClient client)
    {
        this.client = client;
    }

    public Book fetch(Query query)
    {
        URI uri = getURI(query);

        HttpRequest<?> request = HttpRequest.GET(uri);

        OpenLibraryResponse response = client.retrieve(request, OpenLibraryResponse.class)
                .firstElement()
                .blockingGet();

        return response.get(query.getIsbn());
    }

    private URI getURI(Query query)
    {
        return UriBuilder
                .of(OpenLibraryConfiguration.BOOK_URI)
                .queryParam(OpenLibraryConfiguration.PARAM_KEY, query.getIsbn())
                .queryParam(OpenLibraryConfiguration.PARAM_FORMAT, OpenLibraryConfiguration.FORMAT_DEFAULT)
                .build();
    }

    private static class OpenLibraryResponse extends HashMap<String, Book>
    {
    }

}