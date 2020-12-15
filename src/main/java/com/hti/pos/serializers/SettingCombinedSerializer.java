package com.hti.pos.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import com.hti.pos.domain.Setting;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

//@JsonComponent
public class SettingCombinedSerializer  {

    public static class SettingSerializer extends JsonSerializer<Setting> {
        @Override
        public void serialize(Setting value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
//            generator.writeStartObject();
            generator.writeStringField("name", value.getName());
            generator.writeStringField("desc", value.getDescription());
//            generator.writeEndObject();
        }
    }

    public static class SettingDeserializer extends JsonDeserializer<Setting> {
        @Override
        public Setting deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
            TreeNode treeNode = parser.getCodec().readTree(parser);
            TextNode nameNode = (TextNode) treeNode.get("name");
            TextNode descNode = (TextNode) treeNode.get("desc");
            return new Setting(nameNode.asText(), descNode.asText());
        }
    }

}