package io.crnk.core.engine.internal.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.internal.document.mapper.DocumentMapperUtil;
import io.crnk.core.engine.internal.utils.SerializerUtil;

/**
 * Serializes top-level Errors object.
 */
public class ErrorDataSerializer extends ValueSerializer<ErrorData> {

	public static final String LINKS = "links";
	public static final String ID = "id";
	public static final String ABOUT_LINK = "about";
	public static final String STATUS = "status";
	public static final String CODE = "code";
	public static final String TITLE = "title";
	public static final String DETAIL = "detail";
	public static final String SOURCE = "source";
	public static final String POINTER = "pointer";
	public static final String PARAMETER = "parameter";
	public static final String META = "meta";

	private static void writeMeta(ErrorData errorData, JsonGenerator gen) throws JacksonException {
		if (errorData.getMeta() != null) {
			gen.writePOJOProperty(META, errorData.getMeta());
		}
	}

	private static void writeSource(ErrorData errorData, JsonGenerator gen) throws JacksonException {
		if (errorData.getSourceParameter() != null || errorData.getSourcePointer() != null) {
			gen.writeObjectPropertyStart(SOURCE);
			SerializerUtil.writeStringIfExists(POINTER, errorData.getSourcePointer(), gen);
			SerializerUtil.writeStringIfExists(PARAMETER, errorData.getSourceParameter(), gen);
			gen.writeEndObject();
		}
	}

	private static void writeAboutLink(ErrorData errorData, JsonGenerator gen) throws JacksonException {
		if (errorData.getAboutLink() != null) {
			SerializerUtil serializerUtil = DocumentMapperUtil.getSerializerUtil();

			gen.writeObjectPropertyStart(LINKS);
			serializerUtil.serializeLink(gen, ABOUT_LINK, errorData.getAboutLink());
			gen.writeEndObject();
		}
	}

	@Override
	public void serialize(ErrorData errorData, JsonGenerator gen, SerializationContext serializers)
			throws JacksonException {

		gen.writeStartObject();
		SerializerUtil.writeStringIfExists(ID, errorData.getId(), gen);
		writeAboutLink(errorData, gen);
		SerializerUtil.writeStringIfExists(STATUS, errorData.getStatus(), gen);
		SerializerUtil.writeStringIfExists(CODE, errorData.getCode(), gen);
		SerializerUtil.writeStringIfExists(TITLE, errorData.getTitle(), gen);
		SerializerUtil.writeStringIfExists(DETAIL, errorData.getDetail(), gen);
		writeSource(errorData, gen);
		writeMeta(errorData, gen);
		gen.writeEndObject();
	}

	@Override
	public Class<ErrorData> handledType() {
		return ErrorData.class;
	}

}
