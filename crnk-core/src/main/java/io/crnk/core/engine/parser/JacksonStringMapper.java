package io.crnk.core.engine.parser;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import tools.jackson.databind.node.NumericNode;
import tools.jackson.databind.node.StringNode;

public class JacksonStringMapper<T> implements StringMapper<T> {

	private final ObjectReader reader;

	private final ObjectMapper mapper;


	public JacksonStringMapper(ObjectMapper mapper, Class clazz) {
		this.reader = mapper.readerFor(clazz);
		this.mapper = mapper;
	}

	@Override
	public T parse(String input) {
		JsonNode node = StringNode.valueOf(input);
		try {
			return reader.readValue(node);
		} catch (JacksonException e) {
			throw new ParserException("Cannot parse " + input, e);
		}
	}

	@Override
	public String toString(T input) {
		JsonNode jsonNode = mapper.valueToTree(input);

		if (jsonNode instanceof StringNode) {
			return jsonNode.textValue();
		}
		if (jsonNode instanceof NumericNode) {
			return jsonNode.asText();
		}

		// fallback to String for complex type
		return input.toString();
	}
}
