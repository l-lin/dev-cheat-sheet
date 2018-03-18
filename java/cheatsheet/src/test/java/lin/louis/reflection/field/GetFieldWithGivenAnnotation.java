package lin.louis.reflection.field;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14 16:08
 */
public class GetFieldWithGivenAnnotation {

    @Test
    public void getFieldsWithGivenAnnotation() {
        // Search on parent class
        List<Field> fieldList = getFields(Foobar.class, Deprecated.class, Lists.<Field>newArrayList());
        assertThat(fieldList).isNotEmpty().hasSize(2);
        for (Field field : fieldList) {
            assertThat(field.getType()).isIn(String.class, Integer.class);
        }

        // Search on child class
        fieldList = getFields(FoobarChild.class, Deprecated.class, Lists.<Field>newArrayList());
        assertThat(fieldList).isNotEmpty().hasSize(2);
        for (Field field : fieldList) {
            assertThat(field.getType()).isIn(String.class, Integer.class);
        }
    }

    /**
     * Gets fields.
     *
     * @param clazz           the clazz
     * @param annotationClass the annotation class
     * @param fieldList       the field list
     * @return the fields
     */
    public static List<Field> getFields(@Nullable Class<?> clazz, Class<? extends Annotation> annotationClass, List<Field> fieldList) {
        checkNotNull(annotationClass, "The annotation class must be set");

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

    /**
     * Check if the given field has the given annotation.
     *
     * @param field           the field
     * @param annotationClass the annotation class
     * @return the boolean
     */
    private static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        Optional<Annotation[]> annotations = fromNullable(field.getDeclaredAnnotations());
        if (annotations.isPresent() && annotations.get().length > 0) {
            for (Annotation annotation : annotations.get()) {
                if (annotationClass.equals(annotation.annotationType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Data
    private class Foobar {
        @Deprecated
        private String foobarString;
        @Deprecated
        private Integer foobarInt;
        private Boolean foobarBoolean;
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    private class FoobarChild extends Foobar {
        private Long foobarLong;
        private Double foobarDouble;
    }
}
