package com.github.vmyasnik.fastREST.domain;
public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    static HttpMethod parse(String argument) {
        for (HttpMethod value : HttpMethod.values()) {
            if (value.name().equals(argument)) {
                return value;
            }
        }
        throw new IllegalArgumentException(argument + " was not a http method");
    }
}
