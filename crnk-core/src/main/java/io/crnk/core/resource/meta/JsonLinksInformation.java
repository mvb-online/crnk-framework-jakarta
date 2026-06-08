package io.crnk.core.resource.meta;

import tools.jackson.core.JacksonException;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import io.crnk.core.engine.internal.utils.CastableInformation;
import io.crnk.core.resource.links.LinksInformation;

public class JsonLinksInformation implements LinksInformation, CastableInformation<LinksInformation> {

	private JsonNode data;

	private ObjectMapper mapper;

	public JsonLinksInformation(JsonNode data, ObjectMapper mapper) {
		this.data = data;
		this.mapper = mapper;
	}

	public JsonNode asJsonNode() {
		return data;
	}

	/**
	 * Converts this generic links information to the provided type.
	 *
	 * @param linksClass to return
	 * @return links information based on the provided type.
	 */
	@Override
	public <L extends LinksInformation> L as(Class<L> linksClass) {
		try {
			if (linksClass.isInterface()) {
				return JsonMetaInformation.createInterfaceJsonAdapter(linksClass, data, mapper);
			}
			ObjectReader reader = mapper.readerFor(linksClass);
			return reader.readValue(data);
		}
		catch (JacksonException e) {
			throw new IllegalStateException(e);
		}
	}
}
