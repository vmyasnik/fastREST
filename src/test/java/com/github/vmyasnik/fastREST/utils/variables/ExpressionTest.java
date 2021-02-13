package com.github.vmyasnik.fastREST.utils.variables;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionTest {
    @Test
    public void elTest() throws FastException {
        VariableUtil.define("a", "1");
        Expression.execute("b = 2;");
        Assert.assertEquals(3, Expression.execute("new(\"java.lang.Integer\", a)+b"));
    }

    @Test
    public void elStrTest() throws FastException {
        VariableUtil.define("method", "weather");
        Assert.assertEquals("https://community-open-weather-map.p.rapidapi.com/weather",
                Expression.execute("`https://community-open-weather-map.p.rapidapi.com/${method}`"));
    }

    @Test
    public void elSafeStrTest() throws FastException {
        //VariableUtil.define("method", "weather");
        Expression.smartExecute("method = 'weather'");
        Assert.assertEquals("https://community-open-weather-map.p.rapidapi.com/weather",
                Expression.smartExecute("https://community-open-weather-map.p.rapidapi.com/${method}"));
    }
}
