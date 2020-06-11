package com.hamza3202

import com.hamza3202.book.Book
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("/")
interface BookClient {

    @Consumes(MediaType.APPLICATION_JSON)
    @Get(value = "getBook")
    Book getBook(@Header String authorization, @QueryValue String isbn)

}
