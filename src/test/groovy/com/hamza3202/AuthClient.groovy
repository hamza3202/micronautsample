package com.hamza3202

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken

@Client("/")
interface AuthClient {

    @Post("login")
    BearerAccessRefreshToken login(@Body UsernamePasswordCredentials credentials)

}
