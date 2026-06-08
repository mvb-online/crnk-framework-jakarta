package io.crnk.core.engine.internal.information.resource;

import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.introspect.AnnotatedClass;
import tools.jackson.databind.introspect.AnnotatedClassResolver;

/**
 * Builder for creating {@link AnnotatedClass} instances using Jackson's {@link AnnotatedClassResolver}.
 */
public class AnnotatedClassBuilder {

	private AnnotatedClassBuilder() {
	}

	public static AnnotatedClass build(final Class<?> declaringClass, final SerializationConfig serializationConfig) {
		JavaType javaType = serializationConfig.constructType(declaringClass);
		return AnnotatedClassResolver.resolve(serializationConfig, javaType, serializationConfig);
	}
}
