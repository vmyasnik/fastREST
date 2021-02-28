package com.github.vmyasnik.fastREST.utils.variables;

import com.github.vmyasnik.fastREST.domain.Const;
import com.github.vmyasnik.fastREST.utils.persist.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.vmyasnik.fastREST.utils.variables.Expression.VAR_REGEXP;

public class VariableUtil {

    public static void define(String var, String value) {
        Context.put(Const.EL_VAR_PREFIX + var, value);
    }

    public static String get(String path) {
        return Context.getValue(Const.EL_VAR_PREFIX + path);
    }

    public static String replace(String str) throws FastException {
        Pattern pattern = Pattern.compile(VAR_REGEXP);
        Matcher matcher = pattern.matcher(str);
        int lastStart = 0;
        StringBuilder output = new StringBuilder();
        String tail = str;
        while (matcher.find()) {
            String subString = str.substring(lastStart, matcher.start());
            String name = matcher.group(1);
            String replacement = get(name);
            if (replacement == null) {
                throw new FastException("No variable:" + name + ".");
            }
            output.append(subString).append(replacement);
            lastStart = matcher.end();
            tail = str.substring(lastStart);
        }
        if (!tail.equals("")) {
            output.append(tail);
        }
        return output.toString();
    }
}


