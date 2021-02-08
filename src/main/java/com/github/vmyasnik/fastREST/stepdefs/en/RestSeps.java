package com.github.vmyasnik.fastREST.stepdefs.en;

import com.github.vmyasnik.fastREST.domain.DefinedVar;
import com.github.vmyasnik.fastREST.utils.FastRest;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import com.github.vmyasnik.fastREST.utils.variables.VariableUtil;
import io.cucumber.java.en.And;

import java.util.List;

public class RestSeps {
    //это будут подключать в фрейме, тут соответственно надо дать возможность настройки
//    private final FastRest rest = new FastRest.FastRestBuilder()
////            .setOkHttpClient(OkHttpFactory.get())
////            .setUrlResolver(new UrlResolver() {
////            })
//            .build();

    public RestSeps() {
        //теперь так
//        FastRestSettings.setOkHttpClient(OkHttpFactory.getOkHttp());
//        FastRestSettings.setUrlResolver(new UrlResolver() {
//        });
    }

    @And("make GET request {string}")
    public void makeGetRequest(String path) {
        FastRest.makeGetRequest(path);
    }

    @And("send")
    public void send() {
        FastRest.send();
    }

    @And("define")
    public void define(List<DefinedVar> vars) {
        for (DefinedVar var : vars) {
            VariableUtil.define(var.getVariable(), var.getValue().toString());
        }
    }

    @And("print {string}")
    public void print(String path) {
        System.out.println(VariableUtil.get(path));
    }

    @And("echo {string}")
    public void echo(String path) throws FastException {
        System.out.println(VariableUtil.replace(path));
    }
}
