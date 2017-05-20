package io.crnk.jpa.meta;

import io.crnk.meta.model.MetaPrimitiveType;
import org.junit.Test;

import java.io.Serializable;
import java.util.UUID;

public class MetaPrimitiveTypeTest {

	@Test
	public void testString() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(String.class);
	}

	@Test
	public void testInteger() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Integer.class);
	}

	@Test
	public void testShort() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Short.class);
	}

	@Test
	public void testLong() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Long.class);
	}

	@Test
	public void testFloat() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Float.class);
	}

	@Test
	public void testDouble() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Double.class);
	}

	@Test
	public void testBoolean() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Boolean.class);
	}

	@Test
	public void testByte() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(Byte.class);
	}

	@Test
	public void testUUID() {
		UUID uuid = UUID.randomUUID();
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(UUID.class);
	}

	@Test
	public void testEnum() {
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(TestEnum.class);
	}

	@Test
	public void testParse() {
		TestObjectWithParse value = new TestObjectWithParse();
		value.value = 12;
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(TestObjectWithParse.class);
	}

	@Test
	public void testOther() {
		TestObjectWithConstructor value = new TestObjectWithConstructor();
		value.value = 12;
		MetaPrimitiveType type = new MetaPrimitiveType();
		type.setImplementationType(TestObjectWithConstructor.class);
	}

	enum TestEnum {
		A
	}

	public static class TestObjectWithParse {

		int value;

		public static TestObjectWithParse parse(String value) {
			TestObjectWithParse parser = new TestObjectWithParse();
			parser.value = Integer.parseInt(value);
			return parser;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestObjectWithParse other = (TestObjectWithParse) obj;
			return value == other.value;
		}
	}

	public static class TestObjectWithConstructor implements Serializable {

		int value;

		public TestObjectWithConstructor() {
		}

		public TestObjectWithConstructor(String value) {
			this.value = Integer.parseInt(value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestObjectWithConstructor other = (TestObjectWithConstructor) obj;
			return value == other.value;
		}
	}

}
