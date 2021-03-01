package com.github.vmyasnik.fastREST.utils.variables;

import com.github.vmyasnik.fastREST.domain.Const;
import com.github.vmyasnik.fastREST.utils.persist.Context;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static void executeCommandLine(String command) throws FastException, FastCommandLineException {
        try {
            Process process;
            String com = VariableUtil.replace(command);
            if (isWindows()) {
                process = new ProcessBuilder("cmd", "/c", com).start();
            } else {
                process = new ProcessBuilder("sh", "-c", com).start();
            }
            process.waitFor();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String responseBody;
            String errorBody;

            StringBuilder errorBuilder = new StringBuilder();
            StringBuilder responseBuilder = new StringBuilder();
            while ((errorBody = stdErr.readLine()) != null) {
                errorBuilder.append(errorBody).append("\n");
            }
            while ((responseBody = stdInput.readLine()) != null) {
                responseBuilder.append(responseBody).append("\n");
            }
            Context.put("errorBody", errorBuilder);
            Context.put("responseBody", responseBuilder);

            if (process.exitValue() != 0) {
                throw new FastCommandLineException(errorBuilder.toString());
            }
            System.out.println("Request: ".concat(com));
            System.out.println("Response: ".concat(responseBuilder.toString()));
            System.out.println("Error: ".concat(errorBuilder.toString()));

        } catch (InterruptedException | IOException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
