package com.github.vmyasnik.fastREST.utils.variables;

import com.github.vmyasnik.fastREST.domain.Const;
import com.github.vmyasnik.fastREST.utils.persist.Context;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    final static Logger LOGGER = Logger.getLogger(Expression.class);
    public static final String VAR_REGEXP = "\\$\\{(.*?)}";

    public static Object smartExecute(String script) {
        Pattern pattern = Pattern.compile(VAR_REGEXP);
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            //escaped execute if var signature exist
            return execute("`" + script + "`");
        } else {
            return execute(script);
        }
    }


    public static Object execute(String script) {
        Map<String, Object> funcs = new HashMap<>();
//        funcs.put("System", System.class);
        JexlEngine jexl = new JexlBuilder()
                .namespaces(funcs)
                .create();
        LOGGER.debug("Try to execute script:" + script);
        JexlScript e = jexl.createScript(script);
        JexlContext jc = new JexlContext() {
            @Override
            public Object get(String s) {
                return Context.getValue(Const.EL_VAR_PREFIX + s);
            }

            @Override
            public void set(String s, Object o) {
                Context.put(Const.EL_VAR_PREFIX + s, o);
            }

            @Override
            public boolean has(String s) {
                return Context.has(s);
            }
        };
        Object execute = e.execute(jc);
        LOGGER.debug("Exec result is:" + execute);
        return execute;
    }
}
