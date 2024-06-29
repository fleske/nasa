package com.plenigo.nasa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class NasaClientInterceptor implements RequestInterceptor {

    @Value("${nasa.api.key}")
    private String apiKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("api_key", apiKey);
    }
}
