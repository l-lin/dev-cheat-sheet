package lin.louis.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Oodrive
 * @author llin
 * @created 19/09/14 14:19
 */
public abstract class GetGenericClass<I> {
    private final Class<I> type;

    /**
     * Instantiates a new Get generic class.
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public GetGenericClass() {
        super();
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType) && genericSuperclass instanceof Class) {
            genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
        }
        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        this.type = (Class<I>) pt.getActualTypeArguments()[0];
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Class<I> getType() {
        return type;
    }
}
