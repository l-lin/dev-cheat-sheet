package lin.louis.reflection.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;


public class InvokeSetter {

	@Test
	public void invokeSetter() throws InvocationTargetException, IllegalAccessException {
		Foo foo = new Foo();
		BeanUtils.setProperty(foo, "id", 123);
		assertThat(foo.getId()).isEqualTo(123);
	}

	public class Foo {

		private Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
	}
}
