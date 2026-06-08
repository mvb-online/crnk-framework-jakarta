package io.crnk.core.queryspec;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PathSpecTest {

	@Test
	public void testSerialization() throws IOException {
		PathSpec pathSpec = PathSpec.of("a.b.c");
		ObjectMapper objectMapper = JsonMapper.builder().build();
		String json = objectMapper.writerFor(PathSpec.class).writeValueAsString(pathSpec);
		Assert.assertEquals("\"a.b.c\"", json);

		PathSpec clone = objectMapper.readerFor(PathSpec.class).readValue(json);
		Assert.assertEquals(pathSpec, clone);
	}
}
