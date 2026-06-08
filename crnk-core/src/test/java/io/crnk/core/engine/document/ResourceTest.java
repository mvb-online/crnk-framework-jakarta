package io.crnk.core.engine.document;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.StringNode;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ResourceTest {

	@Test
	public void testResourceEqualsContract() {
		EqualsVerifier.forClass(Resource.class)
				.usingGetClass()
				.suppress(Warning.NONFINAL_FIELDS)
				// https://github.com/jqno/equalsverifier/issues/486
				.withPrefabValues(JsonNode.class, NullNode.instance, StringNode.valueOf("foo"))
				.verify();
	}
}
