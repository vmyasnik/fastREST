package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.utils.http.OkHttpFactory;
import com.github.vmyasnik.fastREST.utils.http.UrlResolver;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import okhttp3.OkHttpClient;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;

import static com.github.vmyasnik.fastREST.domain.Const.CONFIG_PATH;
import static com.github.vmyasnik.fastREST.domain.Const.ENV_PATH;

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
        File resourcesDirectory = new File(CONFIG_PATH);
        ConfigFactory.setProperty(ENV_PATH, resourcesDirectory.getAbsolutePath());
        return FileHelper.getFile(fileName, FileHelper.getConfigFolder(configuration.getJsonFolder()));
    }

}
