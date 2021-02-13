package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.domain.HttpMethod;
import com.github.vmyasnik.fastREST.utils.http.UniversalSender;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import com.github.vmyasnik.fastREST.utils.variables.VariableUtil;
import io.cucumber.datatable.DataTable;

import java.util.Map;

public class FastRest {
    public static void makeGetRequest(String path) throws FastException {
        UniversalSender.makeRequest(VariableUtil.replace(path), HttpMethod.GET, FastRestSettings.getUrlResolver());
    }

    public static void makePostRequest(String path, DataTable dataTable) throws FastException {
        UniversalSender.makeRequest(VariableUtil.replace(path), HttpMethod.POST, dataTable, FastRestSettings.getUrlResolver());
    }

    public static void send() {
        UniversalSender.send(FastRestSettings.getOkHttpClient());
    }

    public static void define(String var, String value) {
        VariableUtil.define(var, value);
    }

    public static void addHeaders(DataTable table) throws FastException {
        Map<String, String> headersMap = (table != null) ? table.asMap(String.class, String.class) : null;
        if (headersMap != null) {
            UniversalSender.addHeaders(headersMap);
        }
    }
}
