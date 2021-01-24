package utils.http;

import domain.HttpMethod;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.persist.Context;

import java.io.IOException;

public class UniversalSender {
    public static void send(OkHttpClient okHttpClient) {
        Response response;
        String responseBody;
        Request.Builder requestB = Context.getValue("builder");
        try {
            long a = System.currentTimeMillis();
            final Request build = requestB.build();
            response = okHttpClient.newCall(build).execute();
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

    public static void makeRequest(String path, HttpMethod method, UrlResolver resolver) {
        HttpUrl httpUrl = makeUrl(null, path, resolver.getResolvedUrl(path));
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

    public static HttpUrl makeUrl(String subdomain, String path, String ref) {
        return HttpUrl
                .parse(path)
                .newBuilder()
                .build();
    }
}
