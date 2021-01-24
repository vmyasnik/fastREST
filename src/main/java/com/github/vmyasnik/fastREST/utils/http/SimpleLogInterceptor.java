package com.github.vmyasnik.fastREST.utils.http;

import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

public class SimpleLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //request logging
        Request request = chain.request();
        String logMessage = String.format("---> Thread: %s\n%s %s\n%s", Thread.currentThread().getId()
                , chain.request().method()
                , request.url(), request.headers());
        System.out.println(logMessage);
        String requestBody;
        Buffer requestBuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestBuffer);
            requestBody = requestBuffer.readUtf8();
            logMessage = String.format("---> Thread: %s\nSend body:\n%s", Thread.currentThread().getId()
                    , requestBody);
            System.out.println(logMessage);

        }
        long t1 = System.nanoTime();
        //response logging
        Response response = chain.proceed(request);
        MediaType contentType = response.body().contentType();
        long t2 = System.nanoTime();
        logMessage = String.format("<--- Thread: %s\nStatus code: %s %s\n%s Time: %.1fms%n%s",
                Thread.currentThread().getId(), response.code(), response.message(),
                response.request().url(), (t2 - t1) / 1e6d, response.headers());
        System.out.println(logMessage);

        String content = response.body().string();
        logMessage = String.format("<--- Thread: %s\nResponse body:\n%s", Thread.currentThread().getId(), content);
        System.out.println(logMessage);
        ResponseBody wrappedBody = ResponseBody.create(content, contentType);
        return response.newBuilder().body(wrappedBody).build();
    }
}
