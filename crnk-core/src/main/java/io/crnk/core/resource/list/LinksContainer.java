package io.crnk.core.resource.list;

import tools.jackson.databind.node.ObjectNode;

public interface LinksContainer {

	ObjectNode getLinks();

	void setLinks(ObjectNode meta);
}
