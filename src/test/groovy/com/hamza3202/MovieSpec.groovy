package com.hamza3202

import com.hamza3202.config.AppConfiguration
import com.hamza3202.config.AuthConfiguration
import com.hamza3202.movie.Movie
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
class MovieSpec extends Specification {

    @Inject
    MovieClient movieClient

    @Inject
    AuthClient authClient

    @Inject
    @Client("/")
    RxHttpClient client

    @Inject
    AuthConfiguration config

    void 'Accessing movie URL without authenticating returns unauthorized'() {
        when:
        client.toBlocking().exchange(HttpRequest.GET(AppConfiguration.MOVIE_ENDPOINT,))

        then:
        HttpClientResponseException e = thrown()
        e.status == HttpStatus.UNAUTHORIZED
    }

    void "upon successful authentication, a movie can be fetched"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(config.username, config.password)
        BearerAccessRefreshToken bearerAccessRefreshToken = authClient.login(creds)

        then:
        bearerAccessRefreshToken.username == config.username
        bearerAccessRefreshToken.accessToken

        and: 'the access token is a signed JWT'
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT

        when: 'movie with only title is fetched'
        List<Movie> movies = movieClient.getMovie("Bearer " + bearerAccessRefreshToken.accessToken, "Guardians")

        then: 'movie is returned'
        movies
        movies.size() == 1
        movies[0].title == "Guardians"

        when: 'movie with title and year is fetched'
        movies = movieClient.getMovie("Bearer " + bearerAccessRefreshToken.accessToken, "Guardians", "2018")

        then: 'movie is returned'
        movies
        movies.size() == 1
        movies[0].title == "7 Guardians of the Tomb"

        when: 'movie with title, year and plot is fetched'
        movies = movieClient.getMovie("Bearer " + bearerAccessRefreshToken.accessToken, "Guardians", "2018", "full")

        then: 'movie is returned'
        movies
        movies.size() == 1
        movies[0].title == "7 Guardians of the Tomb"
    }
}
