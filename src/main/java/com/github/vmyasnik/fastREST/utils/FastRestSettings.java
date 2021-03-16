package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.utils.http.OkHttpFactory;
import com.github.vmyasnik.fastREST.utils.http.UrlResolver;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import okhttp3.OkHttpClient;
import org.aeonbits.owner.ConfigFactory;

/**
 *
 */
public class FastRestSettings {
    private static OkHttpClient okHttpClient = OkHttpFactory.getOkHttp();
    private static UrlResolver urlResolver = new UrlResolver() {
    };
    private static final Configuration configuration = ConfigFactory.create(Configuration.class);


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

    public static String getFile(String fileName) throws FastException {
        return FileHelper.getFile(fileName, FileHelper.getConfigFolder(configuration.getJsonFolder()));
    }

}
