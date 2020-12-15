package com.hti.pos.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hti.pos.domain.Setting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class SettingCombinedSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    private final String JSON_1 = "{\"name\":\"setting-1\",\"desc\":\"setting-1 desc\"}";
    private final String JSON_2 = "{\"name\":\"setting-2\",\"desc\":\"setting-2 desc\"}";

    @Test
    public void testSerialize() throws JsonProcessingException {
        Setting setting = new Setting("setting-1", "setting-1 desc");
        String json = objectMapper.writeValueAsString(setting);
        System.out.println("setting: " + setting);
        System.out.println("json: " + json);
        assertEquals(JSON_1, json);
    }

    @Test
    public void testDeserialize() throws IOException {
        Setting setting = objectMapper.readValue(JSON_2, Setting.class);
        System.out.println("setting: " + setting);
        System.out.println("json: " + JSON_2);
        assertEquals("setting-2", setting.getName());
        assertEquals("setting-2 desc", setting.getDescription());
    }

}