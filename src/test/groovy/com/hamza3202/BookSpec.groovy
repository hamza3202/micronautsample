package com.hamza3202

import com.hamza3202.book.Book
import com.hamza3202.config.AppConfiguration
import com.hamza3202.config.AuthConfiguration
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BookSpec extends Specification {

    @Inject
    BookClient bookClient

    @Inject
    AuthClient authClient

    @Inject
    @Client("/")
    RxHttpClient client

    @Inject
    AuthConfiguration config

    void 'Accessing book URL without authenticating returns unauthorized'() {
        when:
        client.toBlocking().exchange(HttpRequest.GET(AppConfiguration.BOOK_ENDPOINT,))

        then:
        HttpClientResponseException e = thrown()
        e.status == HttpStatus.UNAUTHORIZED
    }

    void "upon successful authentication, a book can be fetched"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(config.username, config.password)
        BearerAccessRefreshToken bearerAccessRefreshToken = authClient.login(creds)

        then:
        bearerAccessRefreshToken.username == config.username
        bearerAccessRefreshToken.accessToken

        and: 'the access token is a signed JWT'
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT

        when: 'movie is fetched'
        Book book = bookClient.getBook("Bearer " + bearerAccessRefreshToken.accessToken, "ISBN:0451526538")

        then: 'book is returned'
        book
        book.key == "ISBN:0451526538"

    }
}
