package com.github.vmyasnik.fastREST.utils;

import org.aeonbits.owner.Config;


//@Config.HotReload
@Config.Sources("classpath:config/configuration.properties")
public interface Configuration extends Config {

    @Key("json")
    String getJsonFolder();

    @Key("os")
    String getOs();

}
