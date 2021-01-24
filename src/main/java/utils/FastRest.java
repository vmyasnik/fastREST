package utils;

import domain.HttpMethod;
import okhttp3.OkHttpClient;
import utils.http.OkHttpFactory;
import utils.http.UniversalSender;
import utils.http.UrlResolver;

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
