package com.github.vmyasnik.fastREST.utils.variables;

import com.github.vmyasnik.fastREST.domain.Const;
import com.github.vmyasnik.fastREST.utils.persist.Context;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;

import java.util.HashMap;
import java.util.Map;

public class Expression {
    public static Object execute(String script) {
        Map<String, Object> funcs = new HashMap<>();
//        funcs.put("System", System.class);
        JexlEngine jexl = new JexlBuilder()
                .namespaces(funcs)
                .create();
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
        return e.execute(jc);
    }
}
