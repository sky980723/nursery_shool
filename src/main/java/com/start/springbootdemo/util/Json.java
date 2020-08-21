package com.start.springbootdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Sky
 */
public final class Json {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T from(String str, Class<T> clazz) throws IOException {
        return mapper.readValue(str, clazz);
    }

    public static String to(Object json) throws IOException {
        return mapper.writeValueAsString(json);
    }
}
