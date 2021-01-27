package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.domain.Const;
import com.github.vmyasnik.fastREST.utils.persist.Context;

public class VariableUtil {

    public static void define(String var, String value) {
        Context.put(Const.EL_VAR_PREFIX + var, value);
    }

    public static String get(String path) {
        return Context.getValue(Const.EL_VAR_PREFIX + path);
    }
}
