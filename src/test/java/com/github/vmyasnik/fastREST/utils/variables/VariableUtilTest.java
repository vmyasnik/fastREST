package com.github.vmyasnik.fastREST.utils.variables;

import org.junit.Assert;
import org.junit.Test;

public class VariableUtilTest {
    @Test
    public void replaceTest() throws FastException {
        VariableUtil.define("a", "1");
        VariableUtil.define("b", "2");
        Assert.assertEquals("1 2", VariableUtil.replace("${a} ${b}"));
        Assert.assertEquals("1 2___1", VariableUtil.replace("${a} ${b}___${a}"));
        Assert.assertEquals("1 2___1", VariableUtil.replace("${a} ${b}___${a}"));
    }

    @Test
    public void NotReplaceTest() throws FastException {
        VariableUtil.define("a", "1");
        VariableUtil.define("b", "2");
        Assert.assertEquals("1 $ab}", VariableUtil.replace("${a} $ab}"));
        Assert.assertEquals("1 ${ab", VariableUtil.replace("${a} ${ab"));
        Assert.assertEquals("1 {ab}", VariableUtil.replace("${a} {ab}"));
        Assert.assertEquals("1 1 $a  2", VariableUtil.replace("${a} ${a} $a  ${b}"));
        Assert.assertEquals("testString!", VariableUtil.replace("testString!"));
    }
}
