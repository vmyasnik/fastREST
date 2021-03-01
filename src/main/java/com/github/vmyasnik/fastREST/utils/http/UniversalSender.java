package com.github.vmyasnik.fastREST.utils.http;

import com.github.vmyasnik.fastREST.domain.HttpMethod;
import com.github.vmyasnik.fastREST.utils.FastRestSettings;
import com.github.vmyasnik.fastREST.utils.persist.Context;
import com.github.vmyasnik.fastREST.utils.variables.Expression;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import com.github.vmyasnik.fastREST.utils.variables.VariableUtil;
import io.cucumber.datatable.DataTable;
import okhttp3.*;
import org.apache.http.client.utils.URIBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        Request.Builder requestB = getRequestBuilder(null, path, resolver);

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

    private static Request.Builder getRequestBuilder(String subdomain, String path, UrlResolver resolver) {
        HttpUrl httpUrl = makeUrl(subdomain, path, resolver.getResolvedUrl(path));
        Request.Builder requestB = new Request.Builder();
        return requestB.url(httpUrl);
    }

    public static void makeRequest(String path, HttpMethod method, DataTable dataTable, UrlResolver resolver) throws FastException {
        Request.Builder requestB = getRequestBuilder(null, path, resolver);
        List<List<String>> dataTableList = dataTable.asLists();
        RequestBody body;
        MultipartBody.Builder application = new MultipartBody.Builder();
        body = getRequestBody(dataTableList, application);

        getHttpMethod(method, requestB, body);
        Context.put("builder", requestB);
    }

    private static void getHttpMethod(HttpMethod method, Request.Builder requestB, RequestBody body) {
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
    }

    @NotNull
    private static RequestBody getRequestBody(List<List<String>> dataTableList, MultipartBody.Builder application) throws FastException {
        RequestBody body;
        switch (dataTableList.get(0).get(0).toLowerCase()) {
            case "body": {
                body = getRequestBody(dataTableList);
                break;
            }
            case "from-data":
            case "multipart": {
                body = getMultipartBody(dataTableList, application);
                break;
            }
            case "x-www-urlencoded": {
                body = getXWWWUrlencodedBody(dataTableList);
                break;
            }
            default: {
                throw new FastException("Parameter body is null");
            }
        }
        return body;
    }


    //    TODO
    private static RequestBody getXWWWUrlencodedBody(List<List<String>> dataTableList) throws FastException {
        RequestBody body;
        int i = 0;
        HashMap<String, String> map = new HashMap<>();
        for (List<String> sub : dataTableList) {
            if (++i == 1) {
                continue;
            }
            map.put(sub.get(0), sub.get(1));
        }
        String temp = null;
        try {
            temp = httpBuildQuery(map);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded");
        temp = VariableUtil.replace(temp);
        body = RequestBody.create(JSON, temp);
        return body;
    }

    private static MultipartBody getMultipartBody(List<List<String>> dataTableList, MultipartBody.Builder application) throws FastException {
        int varLine = 0;
        for (List<String> sub : dataTableList) {
            if (++varLine == 1) {
                continue;
            }
            application
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(sub.get(0), VariableUtil.replace(sub.get(1)));
        }
        return application.build();

    }

    private static RequestBody getRequestBody(List<List<String>> dataTableList) throws FastException {
        String temp;
        RequestBody body;
        String raw = dataTableList.get(0).get(1);
        temp = FastRestSettings.getFile(raw);
        temp = VariableUtil.replace(temp);
        body = RequestBody.create(null, temp);
        return body;
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

    public static void assertCode(String code) {
        if (!Context.getValue("responseHttpCode").toString().equals(code)) {
            throw new AssertionError(String.format("http code %s expected but %s in response", code, Context.getValue("responseHttpCode")));
        }
    }

    public static String httpBuildQuery(Map<String, String> map) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        for (Map.Entry<String, String> str : new TreeMap<>(map).entrySet()) {
            String text = String.valueOf(Expression.execute(str.getValue()));
            builder.addParameter(str.getKey(), text);
        }
        return builder.build().toString().replace("?", "");
    }
}
