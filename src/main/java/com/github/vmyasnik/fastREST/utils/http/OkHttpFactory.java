package com.github.vmyasnik.fastREST.utils.http;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpFactory {
    public static OkHttpClient getOkHttp() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new SimpleLogInterceptor())
                .build();
    }
}
