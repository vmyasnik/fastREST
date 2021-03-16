package com.github.vmyasnik.fastREST.utils;

import com.github.vmyasnik.fastREST.utils.variables.FastException;
import com.github.vmyasnik.fastREST.utils.variables.VariableUtil;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static String getConfigFolder(String config) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL resource = loader.getResource(config);
        if (resource != null) {
            return resource.getFile();
        } else {
            return null;
        }
    }

    public static String getFile(String contentName, String resourceStream) throws FastException {
        if (contentName.isEmpty()) {
            return contentName;
        }
        File found = searchFile(new File(resourceStream), VariableUtil.replace(contentName));
        try {
            return new String(Files.readAllBytes(Paths.get(found.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentName;
    }


    public static File searchFile(File resourceStream, String fileName) {
        if (resourceStream.isDirectory()) {
            File[] files = resourceStream.listFiles();
            for (File f : files) {
                if (f.getName().equals(fileName)) {
                    return f;
                }
            }
        }
        throw new AssertionError();
    }
}
