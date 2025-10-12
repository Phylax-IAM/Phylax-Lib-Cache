package com.phylax.lib.mapper;

import redis.clients.jedis.json.JsonObjectMapper;
import tools.jackson.databind.ObjectMapper;

public class ExternalObjectMapper implements JsonObjectMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T fromJson(String value, Class<T> valueType) {
        return mapper.readValue(value, valueType);
    }

    @Override
    public String toJson(Object value) {
        return mapper.writeValueAsString(value);
    }
}
