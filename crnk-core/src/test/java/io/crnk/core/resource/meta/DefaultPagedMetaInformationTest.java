package io.crnk.core.resource.meta;


import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;

public class DefaultPagedMetaInformationTest {

	@Test
	public void nullMustNotBeSerialized() throws JacksonException {
		ObjectMapper mapper = JsonMapper.builder().build();
		ObjectWriter writer = mapper.writerFor(DefaultPagedMetaInformation.class);

		DefaultPagedMetaInformation metaInformation = new DefaultPagedMetaInformation();
		String json = writer.writeValueAsString(metaInformation);
		Assert.assertEquals("{}", json);
	}

	@Test
	public void nonNullMustBeSerialized() throws JacksonException {
		ObjectMapper mapper = JsonMapper.builder().build();
		ObjectWriter writer = mapper.writerFor(DefaultPagedMetaInformation.class);

		DefaultPagedMetaInformation metaInformation = new DefaultPagedMetaInformation();
		metaInformation.setTotalResourceCount(12L);

		String json = writer.writeValueAsString(metaInformation);
		Assert.assertEquals("{\"totalResourceCount\":12}", json);
	}
}
