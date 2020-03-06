package lin.louis.reflection.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Test;


public class GetFieldWithGivenAnnotation {

    @Test
    public void getFieldsWithGivenAnnotation() {
        // Search on parent class
        List<Field> fieldList = getFields(Foobar.class, Deprecated.class, new ArrayList<>());
        assertThat(fieldList).isNotEmpty().hasSize(2);
        for (Field field : fieldList) {
            assertThat(field.getType()).isIn(String.class, Integer.class);
        }

        // Search on child class
        fieldList = getFields(FoobarChild.class, Deprecated.class, new ArrayList<>());
        assertThat(fieldList).isNotEmpty().hasSize(2);
        for (Field field : fieldList) {
            assertThat(field.getType()).isIn(String.class, Integer.class);
        }
    }

    public static List<Field> getFields(Class<?> clazz, Class<? extends Annotation> annotationClass, List<Field> fieldList) {
        if (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (hasAnnotation(field, annotationClass)) {
                        fieldList.add(field);
                    }
                }
            }
            // If no field is found with @{Annotation}, then search in super class
            return getFields(clazz.getSuperclass(), annotationClass, fieldList);
        }
        return fieldList;
    }

    private static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        Optional<Annotation[]> annotations = Optional.ofNullable(field.getDeclaredAnnotations());
        if (annotations.isPresent() && annotations.get().length > 0) {
            for (Annotation annotation : annotations.get()) {
                if (annotationClass.equals(annotation.annotationType())) {
                    return true;
                }
            }
        }
        return false;
    }

    private class Foobar {
        @Deprecated
        private String foobarString;
        @Deprecated
        private Integer foobarInt;
        private Boolean foobarBoolean;

		private String getFoobarString() {
			return foobarString;
		}

		private void setFoobarString(String foobarString) {
			this.foobarString = foobarString;
		}

		private Integer getFoobarInt() {
			return foobarInt;
		}

		private void setFoobarInt(Integer foobarInt) {
			this.foobarInt = foobarInt;
		}

		private Boolean getFoobarBoolean() {
			return foobarBoolean;
		}

		private void setFoobarBoolean(Boolean foobarBoolean) {
			this.foobarBoolean = foobarBoolean;
		}
	}

    private class FoobarChild extends Foobar {
        private Long foobarLong;
        private Double foobarDouble;

		private Long getFoobarLong() {
			return foobarLong;
		}

		private void setFoobarLong(Long foobarLong) {
			this.foobarLong = foobarLong;
		}

		private Double getFoobarDouble() {
			return foobarDouble;
		}

		private void setFoobarDouble(Double foobarDouble) {
			this.foobarDouble = foobarDouble;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			FoobarChild that = (FoobarChild) o;
			return Objects.equals(foobarLong, that.foobarLong) &&
					Objects.equals(foobarDouble, that.foobarDouble);
		}

		@Override
		public int hashCode() {
			return Objects.hash(foobarLong, foobarDouble);
		}
	}
}
