package io.crnk.core.engine.internal.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.JsonNode;
import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.document.ErrorDataBuilder;
import io.crnk.core.engine.internal.utils.SerializerUtil;

import java.util.Map;

/**
 * Serializes top-level Errors object.
 */
public class ErrorDataDeserializer extends ValueDeserializer<ErrorData> {

    @SuppressWarnings("unchecked")
    private static Map<String, Object> readMeta(JsonNode errorNode, DeserializationContext context) throws JacksonException {
        JsonNode metaNode = errorNode.get(ErrorDataSerializer.META);
        if (metaNode != null) {
            return context.readTreeAsValue(metaNode, Map.class);
        }
        return null;
    }

    private static String readSourcePointer(JsonNode errorNode) {
        JsonNode node = errorNode.get(ErrorDataSerializer.SOURCE);
        if (node != null) {
            return SerializerUtil.readStringIfExists(ErrorDataSerializer.POINTER, node);
        }
        return null;
    }

    private static String readSourceParameter(JsonNode errorNode) {
        JsonNode node = errorNode.get(ErrorDataSerializer.SOURCE);
        if (node != null) {
            return SerializerUtil.readStringIfExists(ErrorDataSerializer.PARAMETER, node);
        }
        return null;
    }

    private static String readAboutLink(JsonNode errorNode) {
        JsonNode node = errorNode.get(ErrorDataSerializer.LINKS);
        if (node != null) {
            return SerializerUtil.deserializeLink(ErrorDataSerializer.ABOUT_LINK, node);
        }
        return null;
    }

    @Override
    public ErrorData deserialize(JsonParser jp, DeserializationContext context) throws JacksonException {
        JsonNode errorNode = jp.readValueAsTree();
        ErrorDataBuilder builder = ErrorData.builder();
        builder.setId(SerializerUtil.readStringIfExists(ErrorDataSerializer.ID, errorNode));
        builder.setAboutLink(readAboutLink(errorNode));
        builder.setStatus(SerializerUtil.readStringIfExists(ErrorDataSerializer.STATUS, errorNode));
        builder.setCode(SerializerUtil.readStringIfExists(ErrorDataSerializer.CODE, errorNode));
        builder.setTitle(SerializerUtil.readStringIfExists(ErrorDataSerializer.TITLE, errorNode));
        builder.setDetail(SerializerUtil.readStringIfExists(ErrorDataSerializer.DETAIL, errorNode));
        builder.setMeta(readMeta(errorNode, context));
        builder.setSourcePointer(readSourcePointer(errorNode));
        builder.setSourceParameter(readSourceParameter(errorNode));
        return builder.build();
    }

}
