package io.crnk.core.engine.internal.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import io.crnk.core.engine.document.Resource;
import io.crnk.core.engine.internal.utils.PreconditionUtil;
import io.crnk.core.utils.Nullable;

import java.util.Arrays;

public class DocumentDataDeserializer extends ValueDeserializer<Nullable<Object>> {

	@Override
	public Nullable<Object> deserialize(JsonParser jp, DeserializationContext context) throws JacksonException {
		JsonToken currentToken = jp.currentToken();
		if (currentToken == JsonToken.START_ARRAY) {
			Resource[] resources = jp.readValueAs(Resource[].class);
			return Nullable.of(Arrays.asList(resources));
		} else if (currentToken == JsonToken.VALUE_NULL) {
			return Nullable.nullValue();
		} else {
			PreconditionUtil.verifyEquals(currentToken, JsonToken.START_OBJECT, "parsing failed");
			return Nullable.of(jp.readValueAs(Resource.class));
		}
	}

	@Override
	public Nullable<Object> getNullValue(DeserializationContext ctxt) {
		return Nullable.nullValue();
	}
}
