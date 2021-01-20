package utils;

import domain.HttpMethod;

public class FastRest {

    public void makeGetRequest(String path) {
        System.out.println("get");
        UniversalSender.makeRequest(path, HttpMethod.GET);
    }

    public void send() {
        UniversalSender.send();
    }
}
