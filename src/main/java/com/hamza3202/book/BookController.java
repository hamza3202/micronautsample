package com.hamza3202.book;

import com.hamza3202.client.openlibrary.OpenLibraryClient;
import com.hamza3202.config.AppConfiguration;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.validation.Valid;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
public class BookController
{

    private final OpenLibraryClient openLibraryClient;

    public BookController(OpenLibraryClient openLibraryClient)
    {
        this.openLibraryClient = openLibraryClient;
    }

    @Get(value = "getBook{?bookQuery*}", produces = MediaType.APPLICATION_JSON)
    public Book getBook(@Valid final Query bookQuery)
    {
        return openLibraryClient.fetch(bookQuery);
    }

    @Error(status = HttpStatus.NOT_FOUND)
    public HttpResponse notFound(HttpRequest request)
    {
        JsonError error = new JsonError("Book Not Found")
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError>notFound()
                .body(error);
    }
}
