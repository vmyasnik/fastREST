package com.github.vmyasnik.fastREST.domain;

public class DefinedVar {
    private String variable;
    private Object value;

    public DefinedVar() {
    }

    public String getVariable() {
        return variable;
    }

    public Object getValue() {
        return value;
    }

    public void setVariable(String name) {
        this.variable = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
