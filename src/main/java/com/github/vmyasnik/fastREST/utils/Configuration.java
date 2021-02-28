package com.github.vmyasnik.fastREST.utils;

import org.aeonbits.owner.Config;

import static com.github.vmyasnik.fastREST.domain.Const.ENV_PATH;

@Config.HotReload
@Config.Sources({"file:" + ENV_PATH + "/configuration.properties"})
public interface Configuration extends Config {

    @Key("json")
    @DefaultValue("json")
    String getJsonFolder();

    @Key("test")
    String getTest();
}
