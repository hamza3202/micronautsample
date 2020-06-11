package com.hamza3202.auth;

import com.hamza3202.config.AuthConfiguration;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider
{
    private final AuthConfiguration config;

    public AuthenticationProviderUserPassword(AuthConfiguration config)
    {
        this.config = config;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest)
    {
        return Flowable.create(emitter -> {
            if (authenticationRequest.getIdentity().equals(config.getUsername()) &&
                    authenticationRequest.getSecret().equals(config.getPassword()))
            {
                emitter.onNext(new UserDetails((String) authenticationRequest.getIdentity(), new ArrayList<>()));
            }
            else
            {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
            emitter.onComplete();
        }, BackpressureStrategy.ERROR);
    }

}
