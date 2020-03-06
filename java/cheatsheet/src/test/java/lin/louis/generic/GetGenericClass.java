package lin.louis.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GetGenericClass<I> {
    private final Class<I> type;

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public GetGenericClass() {
        super();
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
        }
        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        this.type = (Class<I>) pt.getActualTypeArguments()[0];
    }

    public Class<I> getType() {
        return type;
    }
}
