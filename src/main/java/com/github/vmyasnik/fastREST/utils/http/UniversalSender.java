package com.github.vmyasnik.fastREST.utils.http;

import com.github.vmyasnik.fastREST.domain.HttpMethod;
import com.github.vmyasnik.fastREST.utils.FileHelper;
import com.github.vmyasnik.fastREST.utils.persist.Context;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import com.github.vmyasnik.fastREST.utils.variables.VariableUtil;
import io.cucumber.datatable.DataTable;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public static void makeRequest(String path, HttpMethod method, DataTable dataTable, UrlResolver resolver) throws FastException {
        HttpUrl httpUrl = makeUrl(null, path, resolver.getResolvedUrl(path));
        Request.Builder requestB = new Request.Builder();
        requestB.url(httpUrl);
        List<List<String>> dataTableList = dataTable.asLists();
        String temp = "";
        RequestBody body = null;

//        надо побить на функции
        switch (dataTableList.get(0).get(0).toLowerCase()) {
            case "body": {
                String raw = dataTableList.get(0).get(1);
                temp = FileHelper.getFile(raw);
                temp = VariableUtil.replace(temp);
                body = RequestBody.create(null, temp);
                break;
            }
            case "from-data":
            case "multipart": {
                break;
            }
            case "x-www-urlencoded": {
                break;
            }
            default:{
                throw new FastException("Parameter body is null");
            }
        }
        switch (method) {
            case POST: {
                requestB.post(body);
                break;
            }
            case PUT: {
                requestB.put(body);
                break;
            }
            case PATCH: {
                requestB.patch(body);
                break;
            }
            case DELETE: {
                requestB.delete(body);
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        Context.put("builder", requestB);
    }

    public static HttpUrl makeUrl(String subdomain, String path, String ref) {
        return HttpUrl
                .parse(path)
                .newBuilder()
                .build();
    }

    public static void addHeaders(Map<String, String> table) throws FastException {
        Request.Builder requestB = Context.getValue("builder");
        for (Map.Entry<String, String> entry : table.entrySet()) {
            requestB.header(entry.getKey(), VariableUtil.replace(entry.getValue()));
        }
    }
}
