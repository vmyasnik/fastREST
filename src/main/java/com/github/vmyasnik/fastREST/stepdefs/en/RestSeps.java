package com.github.vmyasnik.fastREST.stepdefs.en;

import com.github.vmyasnik.fastREST.utils.FastRest;
import com.github.vmyasnik.fastREST.utils.FastRestSettings;
import com.github.vmyasnik.fastREST.utils.http.UrlResolver;
import io.cucumber.java.en.And;

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
}
