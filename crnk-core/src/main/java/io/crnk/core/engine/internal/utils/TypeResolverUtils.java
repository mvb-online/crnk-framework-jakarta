package io.crnk.core.engine.internal.utils;

import io.leangen.geantyref.GenericTypeReflector;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Utility for resolving generic type arguments, replacing the deprecated
 * {@code net.jodah.typetools.TypeResolver} which uses {@code sun.misc.Unsafe}.
 * <p>
 * Delegates to geantyref ({@link GenericTypeReflector}), which uses only
 * standard Java reflection APIs.
 */
public final class TypeResolverUtils {

	private TypeResolverUtils() {
	}

	/**
	 * Resolves the raw type arguments that {@code subType} provides for the type
	 * parameters declared by {@code targetType}.
	 * <p>
	 * Example: given {@code interface Repo<T, ID>} and
	 * {@code class BookRepo implements Repo<Book, Long>},
	 * {@code resolveRawArguments(Repo.class, BookRepo.class)}
	 * returns {@code [Book.class, Long.class]}.
	 *
	 * @return array of resolved raw classes, or {@code null} if resolution fails
	 */
	public static Class<?>[] resolveRawArguments(final Class<?> targetType, final Class<?> subType) {
		final Type exactSuperType = GenericTypeReflector.getExactSuperType(subType, targetType);
		if (exactSuperType == null) {
			//since we're replacing net.jodah.typetools.TypeResolver.resolveRawArguments, we return null instead of an empty array
			return null;
		}
		if (exactSuperType instanceof final ParameterizedType parameterizedtype) {
			final Type[] typeArgs = parameterizedtype.getActualTypeArguments();
			final Class<?>[] result = new Class<?>[typeArgs.length];
			for (int i = 0; i < typeArgs.length; i++) {
				result[i] = GenericTypeReflector.erase(typeArgs[i]);
			}
			return result;
		}
		// Raw type — return empty array matching parameter count
		final int paramCount = targetType.getTypeParameters().length;
		final Class<?>[] result = new Class<?>[paramCount];
		java.util.Arrays.fill(result, Object.class);
		return result;
	}

	/**
	 * Resolves a potentially generic {@code type} using concrete type variable
	 * bindings from {@code contextClass}.
	 * <p>
	 * Equivalent to the former {@code TypeResolver.reify(Type, Class)}.
	 */
	public static Type reify(final Type type, final Class<?> contextClass) {
		return GenericTypeReflector.resolveExactType(type, contextClass);
	}
}
