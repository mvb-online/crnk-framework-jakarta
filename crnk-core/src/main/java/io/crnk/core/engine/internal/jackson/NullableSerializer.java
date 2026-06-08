package io.crnk.core.engine.internal.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import io.crnk.core.utils.Nullable;

public class NullableSerializer extends ValueSerializer<Nullable<Object>> {

	@Override
	public void serialize(Nullable<Object> value, JsonGenerator gen, SerializationContext serializers) throws JacksonException {
		if (value.isPresent()) {
			Object object = value.get();
			if (object == null) {
				gen.writeNull();
			} else {
				gen.writePOJO(object);
			}
		}
	}

	@Override
	public boolean isEmpty(SerializationContext provider, Nullable<Object> value) {
		return !value.isPresent();
	}
}
