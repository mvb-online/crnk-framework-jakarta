package io.crnk.core.engine.internal.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.crnk.core.engine.information.resource.ResourceField;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Is used when a resource class is annotated with {@link JsonPropertyOrder}.
 */
public class FieldOrderedComparator implements Comparator<ResourceField> {

	private final Map<String, Integer> fieldNames;
	private final boolean alphabetic;

	public FieldOrderedComparator(String[] orderedValues, boolean alphabetic) {
		this.fieldNames = new HashMap<>();
		this.alphabetic = alphabetic;

		init(orderedValues);
	}

	private void init(String[] orderedValues) {
		for (int i = 0; i < orderedValues.length; i++) {
			this.fieldNames.put(orderedValues[i], i);
		}
	}

	@Override
	public int compare(ResourceField o1, ResourceField o2) {
		// null objects go last
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return 1;
		if (o2 == null) return -1;
		final String name1 = o1.getJsonName();
		final String name2 = o2.getJsonName();
		// compare(x, x) must return 0
		if (name1.equals(name2)) return 0;

		if (fieldNames.containsKey(name1)) {
			if (fieldNames.containsKey(name2)) {
				return fieldNames.get(name1) - fieldNames.get(name2);
			} else {
				return -1;
			}
		} else {
			if (alphabetic) {
				return name1.compareToIgnoreCase(name2);
			} else {
				return 1;
			}
		}
	}
}
