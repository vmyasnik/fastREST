package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.domain.HttpMethod;
import okhttp3.OkHttpClient;
import com.github.vmyasnik.fastREST.utils.http.OkHttpFactory;
import com.github.vmyasnik.fastREST.utils.http.UniversalSender;
import com.github.vmyasnik.fastREST.utils.http.UrlResolver;

public class FastRest {
    private final OkHttpClient okHttpClient;
    private final UrlResolver resolver;

    private FastRest(OkHttpClient okHttpClient, UrlResolver resolver) {
        this.resolver = resolver;
        this.okHttpClient = okHttpClient;
    }

    public static class FastRestBuilder {
        private OkHttpClient okHttpClient = OkHttpFactory.getOkHttp();
        private UrlResolver resolver = new UrlResolver() {
        };

        public FastRestBuilder setOkHttpClient(OkHttpClient httpClient) {
            okHttpClient = httpClient;
            return this;
        }

        public FastRestBuilder setUrlResolver(UrlResolver myresolver) {
            resolver = myresolver;
            return this;
        }

        public FastRest build() {
            return new FastRest(okHttpClient, resolver);
        }
    }

    public void makeGetRequest(String path) {
        UniversalSender.makeRequest(path, HttpMethod.GET, resolver);
    }

    public void send() {
        UniversalSender.send(okHttpClient);
    }
}
