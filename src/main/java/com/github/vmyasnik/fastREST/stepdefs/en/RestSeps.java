package com.github.vmyasnik.fastREST.stepdefs.en;

import com.github.vmyasnik.fastREST.domain.DefinedVar;
import com.github.vmyasnik.fastREST.utils.FastRest;
import com.github.vmyasnik.fastREST.utils.variables.Expression;
import com.github.vmyasnik.fastREST.utils.variables.FastCommandLineException;
import com.github.vmyasnik.fastREST.utils.variables.FastException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
    public void makeGetRequest(String path) throws FastException {
        FastRest.makeGetRequest(path);
    }

    @And("make POST request {string}")
    public void makePostRequest(String path, DataTable dataTable) throws FastException {
        FastRest.makePostRequest(path, dataTable);
    }

    @And("make PUT request {string}")
    public void makePutRequest(String path, DataTable dataTable) throws FastException {
        FastRest.makePutRequest(path, dataTable);
    }

    @And("make PATCH request {string}")
    public void makePatchRequest(String path, DataTable dataTable) throws FastException {
        FastRest.makePatchRequest(path, dataTable);
    }

    @And("make DELETE request {string}")
    public void makeDeleteRequest(String path) throws FastException {
        FastRest.makeDeleteRequest(path);
    }

    @And("send")
    public void send() {
        FastRest.send();
    }

    @And("define")
    public void define(List<DefinedVar> vars) {
        for (DefinedVar var : vars) {
            FastRest.define(var.getVariable(), var.getValue().toString());
        }
    }

    @And("add headers")
    public void addHeaders(DataTable table) throws FastException {
        FastRest.addHeaders(table);
    }

    @And("print {string}")
    public void print(String path) {
        System.out.println(Expression.smartExecute(path));
    }

    @And("echo {string}")
    public void echo(String path) {
        System.out.println(Expression.smartExecute(path));
    }

    @When("^script$")
    public void script(String script) {
        System.out.println(Expression.smartExecute(script));
    }

    @Then("status code {string}")
    public void statusCode(String code) {
        FastRest.assertCode(code);
    }

    @Then("execute command line:")
    public void executeCommandLine(String command) throws FastException, FastCommandLineException {
        Expression.executeCommandLine(command);
    }

}
