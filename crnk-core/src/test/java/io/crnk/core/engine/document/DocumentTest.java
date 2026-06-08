package io.crnk.core.engine.document;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.StringNode;
import io.crnk.core.utils.Nullable;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class DocumentTest {

	@Test
	public void testDocumentEqualsContract() {
		EqualsVerifier.forClass(Document.class)
				.usingGetClass()
				.suppress(Warning.NONFINAL_FIELDS)
				// https://github.com/jqno/equalsverifier/issues/486
				.withPrefabValues(JsonNode.class, NullNode.instance, StringNode.valueOf("foo"))
				.withIgnoredFields("jsonapi") // ignore unused fields in equals and hashcode
				.verify();
	}

	@Test
	public void getCollectionData() {
		Document doc = new Document();
		Assert.assertFalse(doc.getCollectionData().isPresent());

		doc.setData(Nullable.nullValue());
		Assert.assertTrue(doc.getCollectionData().get().isEmpty());

		Resource resource1 = Mockito.mock(Resource.class);
		doc.setData(Nullable.of(resource1));
		Assert.assertEquals(1, doc.getCollectionData().get().size());

		Resource resource2 = Mockito.mock(Resource.class);
		doc.setData(Nullable.of(Arrays.asList(resource1, resource2)));
		Assert.assertEquals(2, doc.getCollectionData().get().size());

	}

	@Test
	public void checkJsonApiServerInfoNotSerializedIfNull() throws JacksonException {
		Document document = new Document();
		document.setJsonapi(null);
		Assert.assertNull(document.getJsonapi());
		ObjectMapper objectMapper = JsonMapper.builder().build();
		ObjectWriter writer = objectMapper.writerFor(Document.class);
		String json = writer.writeValueAsString(document);
		Assert.assertEquals("{}", json);
	}

	@Test
	public void checkJsonApiServerInfoSerialized() throws IOException {
		ObjectMapper objectMapper = JsonMapper.builder().build();
		ObjectWriter writer = objectMapper.writerFor(Document.class);

		ObjectNode info = (ObjectNode) objectMapper.readTree("{\"a\" : \"b\"}");
		Document document = new Document();
		document.setJsonapi(info);
		Assert.assertSame(info, document.getJsonapi());

		String json = writer.writeValueAsString(document);
		Assert.assertEquals("{\"jsonapi\":{\"a\":\"b\"}}", json);
	}
}
