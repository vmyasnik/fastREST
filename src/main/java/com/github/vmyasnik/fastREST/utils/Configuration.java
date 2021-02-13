package com.github.vmyasnik.fastREST.utils;

import org.aeonbits.owner.Config;
@Config.HotReload
@Config.Sources({"file:${ENV_PATH}/Configuration.properties"})
public interface Configuration extends Config {

    @Key("json")
    @DefaultValue("json")
    String getJsonFolder();

    @Key("test")
    String getTest();
}
