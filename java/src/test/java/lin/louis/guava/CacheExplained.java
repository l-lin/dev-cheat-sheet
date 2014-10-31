package lin.louis.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 31/10/14 11:02
 */
public class CacheExplained {
    private static final Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.MINUTES) // Arbitrary value (because I said so!)
            .build();
    private static Callable<String> RETURN_BAR = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "bar";
        }
    };

    @Test
    public void cache() throws ExecutionException {
        String str = cache.get("foo", RETURN_BAR);
        assertThat(str).isEqualTo("bar");

        cache.put("foo", "moliku");
        str = cache.get("foo", RETURN_BAR);
        assertThat(str).isEqualTo("moliku");
    }
}
