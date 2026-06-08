package io.crnk.core.engine.internal.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import io.crnk.core.engine.document.ResourceIdentifier;
import io.crnk.core.engine.internal.utils.PreconditionUtil;
import io.crnk.core.utils.Nullable;

import java.util.Arrays;

public class RelationshipDataDeserializer extends ValueDeserializer<Nullable<Object>> {

	@Override
	public Nullable<Object> deserialize(JsonParser jp, DeserializationContext context) throws JacksonException {
		JsonToken currentToken = jp.currentToken();
		if (currentToken == JsonToken.START_ARRAY) {
			ResourceIdentifier[] resources = jp.readValueAs(ResourceIdentifier[].class);
			return Nullable.of(Arrays.asList(resources));
		} else if (currentToken == JsonToken.VALUE_NULL) {
			return Nullable.of(null);
		} else {
			PreconditionUtil.verifyEquals(currentToken, JsonToken.START_OBJECT, "parsing failed");
			return Nullable.of(jp.readValueAs(ResourceIdentifier.class));
		}
	}

	@Override
	public Nullable<Object> getNullValue(DeserializationContext ctxt) {
		return Nullable.nullValue();
	}

}
