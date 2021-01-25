package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.utils.http.OkHttpFactory;
import com.github.vmyasnik.fastREST.utils.http.UrlResolver;
import okhttp3.OkHttpClient;

/**
 *
 */
public class FastRestSettings {
    private static OkHttpClient okHttpClient = OkHttpFactory.getOkHttp();
    private static UrlResolver urlResolver = new UrlResolver() {
    };

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient1) {
        okHttpClient = okHttpClient1;
    }

    public static UrlResolver getUrlResolver() {
        return urlResolver;
    }

    public static void setUrlResolver(UrlResolver urlResolver1) {
        urlResolver = urlResolver1;
    }


}
