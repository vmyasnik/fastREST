package utils;

import domain.HttpMethod;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UniversalSender {
    private static final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private static final OkHttpClient client =  builder
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new SimpleLogInterceptor())
            .build();;

    public static void send() {
        Response response;
        String responseBody;
        Request.Builder requestB = Context.getValue("builder");
        try {
            long a = System.currentTimeMillis();
            final Request build = requestB.build();

            response = client.newCall(build).execute();
            long b = System.currentTimeMillis();
            responseBody = response.body().string();
            Context.put("responseBody", responseBody);
            Context.put("responseHeaders", response.headers());
            Context.put("responseHttpCode", response.code());
            Context.put("responseHttpTime", b - a);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeRequest(String path, HttpMethod method) {
        HttpUrl httpUrl = makeUrl(null, path, UrlResolver.resolveUrl(path));
        Request.Builder requestB = new Request.Builder();
        requestB.url(httpUrl);
        switch (method) {
            case GET:
                requestB.get();
                break;
            case DELETE:
                requestB.delete();
                break;
            default:
                throw new AssertionError();
        }
        Context.put("builder", requestB);
    }

    static HttpUrl makeUrl(String subdomain, String path, String ref) {
        return HttpUrl
                .parse(path)
                .newBuilder()
                .build();
    }
}
