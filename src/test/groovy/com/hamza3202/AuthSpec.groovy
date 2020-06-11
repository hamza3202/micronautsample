package com.hamza3202

import com.hamza3202.config.AppConfiguration
import com.hamza3202.config.AuthConfiguration
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class AuthSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client

    @Inject
    AuthClient authClient

    @Inject
    AuthConfiguration config

    void 'Accessing secure endpoint without authenticating returns unauthorized'() {
        when:
        client.toBlocking().exchange(HttpRequest.GET(AppConfiguration.MOVIE_ENDPOINT,))

        then:
        HttpClientResponseException e = thrown()
        e.status == HttpStatus.UNAUTHORIZED
    }

    void "upon successful authentication, a Json Web token is issued to the user"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(config.username, config.password)
        HttpRequest request = HttpRequest.POST(AppConfiguration.LOGIN_ENDPOINT, creds)
        HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken)

        then: 'the endpoint can be accessed'
        rsp.status == HttpStatus.OK

        when:
        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body()

        then:
        bearerAccessRefreshToken.username == config.username
        bearerAccessRefreshToken.accessToken

        and: 'the access token is a signed JWT'
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT

        when: "token is used against secure endpoint"
        String accessToken = bearerAccessRefreshToken.accessToken
        HttpRequest requestWithAuthorization = HttpRequest.GET(AppConfiguration.BOOK_ENDPOINT + '?isbn=ISBN:0451526538')
                .accept(MediaType.APPLICATION_JSON)
                .bearerAuth(accessToken)
        HttpResponse<String> response = client.toBlocking().exchange(requestWithAuthorization, String)

        then: 'response is not unauthorized'
        response.status != HttpStatus.UNAUTHORIZED
    }

    void "login with auth client works"() {
        when: 'Login is attempted with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(config.username, config.password)
        BearerAccessRefreshToken bearerAccessRefreshToken = authClient.login(creds)

        then:
        bearerAccessRefreshToken
        bearerAccessRefreshToken.username == config.username
        bearerAccessRefreshToken.accessToken

        and: 'the acces token is a signed JWT\''
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT
    }

}
