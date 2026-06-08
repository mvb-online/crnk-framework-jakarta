package io.crnk.core.engine.internal.information.resource;

import tools.jackson.core.JacksonException;
import java.util.Map;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.ObjectReader;
import io.crnk.core.engine.document.Relationship;
import io.crnk.core.engine.document.Resource;
import io.crnk.core.engine.information.resource.ResourceFieldType;
import io.crnk.core.engine.internal.utils.PreconditionUtil;

public class RawResourceFieldAccessor implements io.crnk.core.engine.information.resource.ResourceFieldAccessor {


	private final ResourceFieldType fieldType;
	private final String fieldName;
	private final Class type;

	private final ObjectReader reader;

	public RawResourceFieldAccessor(String fieldName, ResourceFieldType fieldType, Class type) {
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.type = type;

		ObjectMapper mapper = JsonMapper.builder().build();
		reader = mapper.readerFor(type);
	}

	@Override
	public Object getValue(Object objResource) {
		Resource resource = (Resource) objResource;
		switch (fieldType) {
			case ID:
				return resource.getId();
			case ATTRIBUTE:
				JsonNode jsonNode = resource.getAttributes().get(fieldName);
				return toObject(jsonNode);
			case RELATIONSHIP:
				Map<String, Relationship> relationships = resource.getRelationships();
				Relationship relationship = relationships.get(fieldName);
				if (relationship != null && relationship.getData().isPresent()) {
					return relationship.getData().get();
				}
				return null;
			case META_INFORMATION:
				return toObject(resource.getMeta());
			default:
				PreconditionUtil.verifyEquals(fieldType, ResourceFieldType.LINKS_INFORMATION, "invalid type");
				return toObject(resource.getLinks());
		}
	}

	private Object toObject(JsonNode node) {
		try {
			if (node == null) {
				return null;
			}
			return reader.readValue(node);
		} catch (JacksonException e) {
			throw new IllegalStateException(e);
		}
	}


	@Override
	public void setValue(Object resource, Object fieldValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class getImplementationClass() {
		return type;
	}

}
