package com.hamza3202.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(AuthConfiguration.PREFIX)
@Requires(property = AuthConfiguration.PREFIX)
public class AuthConfiguration
{
    static final String PREFIX = "auth";
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
