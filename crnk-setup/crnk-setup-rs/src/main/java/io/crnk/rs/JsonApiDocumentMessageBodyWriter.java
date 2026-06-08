package io.crnk.rs;

import io.crnk.core.engine.document.Document;
import io.crnk.rs.type.JsonApiMediaType;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import tools.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Serializes Crnk JSON:API documents with Crnk's Jackson mapper.
 */
@Provider
@Priority(Priorities.ENTITY_CODER)
@Produces(JsonApiMediaType.APPLICATION_JSON_API)
public class JsonApiDocumentMessageBodyWriter implements MessageBodyWriter<JsonApiDocumentResponse> {

	private final CrnkFeature feature;

	public JsonApiDocumentMessageBodyWriter(CrnkFeature feature) {
		this.feature = feature;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return JsonApiDocumentResponse.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(JsonApiDocumentResponse response, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		feature.getBoot().getObjectMapper()
				.rebuild()
				.disable(SerializationFeature.INDENT_OUTPUT)
				.build()
				.writeValue(entityStream, response.getDocument());
	}
}

class JsonApiDocumentResponse {

	private final Document document;

	JsonApiDocumentResponse(Document document) {
		this.document = document;
	}

	Document getDocument() {
		return document;
	}
}
