package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.domain.HttpMethod;
import com.github.vmyasnik.fastREST.utils.http.UniversalSender;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import com.github.vmyasnik.fastREST.utils.variables.VariableUtil;

public class FastRest {
    public static void makeGetRequest(String path) throws FastException {
        UniversalSender.makeRequest(VariableUtil.replace(path), HttpMethod.GET, FastRestSettings.getUrlResolver());
    }

    public static void send() {
        UniversalSender.send(FastRestSettings.getOkHttpClient());
    }

    public static void define(String var, String value) {
        VariableUtil.define(var, value);
    }
}
