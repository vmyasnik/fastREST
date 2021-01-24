package com.github.vmyasnik.fastREST.stepdefs.en;

import io.cucumber.java.en.And;
import com.github.vmyasnik.fastREST.utils.FastRest;

public class RestSeps {
    //это будут подключать в фрейме, тут соответственно надо дать возможность настройки
    private final FastRest rest = new FastRest.FastRestBuilder()
//            .setOkHttpClient(OkHttpFactory.get())
//            .setUrlResolver(new UrlResolver() {
//            })
            .build();

    @And("make GET request {string}")
    public void makeGetRequest(String path) {
        rest.makeGetRequest(path);
    }

    @And("send")
    public void send() {
        rest.send();
    }
}
