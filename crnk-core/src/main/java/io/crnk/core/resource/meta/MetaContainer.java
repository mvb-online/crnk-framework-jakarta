package io.crnk.core.resource.meta;

import tools.jackson.databind.node.ObjectNode;

public interface MetaContainer {

	ObjectNode getMeta();

	void setMeta(ObjectNode meta);
}
