package com.hamza3202.client;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {HttpClientResponseException.class, ExceptionHandler.class})
public class HttpClientResponseExceptionHandler implements ExceptionHandler<HttpClientResponseException, HttpResponse>
{

    @Override
    public HttpResponse handle(HttpRequest request, HttpClientResponseException exception)
    {
        if (exception.getStatus() == HttpStatus.UNAUTHORIZED) {
            JsonError error = new JsonError("Server configured with invalid API Key. Contact Administrator")
                    .link(Link.SELF, Link.of(request.getUri()));

            return HttpResponse.serverError(error);
        }

        JsonError error = new JsonError("Something Went Wrong")
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.serverError(error);
    }
}