package aleph;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.DirectFieldAccessor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AbstractBuilder<E> implements Builder<E> {

	private List<BuildStep<E>> steps = new ArrayList<BuildStep<E>>();
	private E object;
	private boolean building;

	public AbstractBuilder() {
		TestPersistentContext.get().add(this);
	}

	public Long getId() {
		return (Long) new DirectFieldAccessor(get()).getPropertyValue("id");
	}

	protected E create() {
		try {
			E instance = (E) ((Class) ((ParameterizedType) this.getClass().
					getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public E get() {
		return object;
	}

	@Override
	public void build() {
		if (building) {
			return;
		}
		building = true;
		if (object == null) {
			object = create();
		}
		invokeSteps();
		afterBuild();
		building = false;
	}

	private void invokeSteps() {
		Iterator<BuildStep<E>> iterator = steps.iterator();
		while (iterator.hasNext()) {
			iterator.next().build(object);
			iterator.remove();
		}
	}

	public void afterBuild() {

	}

	protected <B extends Builder<E>> B add(BuildStep<E> step) {
		steps.add(step);
		return (B) this;
	}

	protected <B extends Builder<E>> B set(final String property, final Object value) {
		return add(new PropertySetBuildStep<E>(property, value));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + steps;
	}

	private class PropertySetBuildStep<T> implements BuildStep<T> {
		private final String propertyName;
		private final Object propertyValue;

		public PropertySetBuildStep(String propertyName, Object propertyValue) {
			super();
			this.propertyName = propertyName;
			this.propertyValue = propertyValue;
		}

		@Override
		public void build(T target) {
			Object realValue = propertyValue;
			if (propertyValue instanceof AbstractBuilder<?>) {
				AbstractBuilder<?> builder = (AbstractBuilder<?>) propertyValue;
				builder.build();
				realValue = builder.get();
			}
			DirectFieldAccessor acessor = new DirectFieldAccessor(target);
			acessor.setPropertyValue(propertyName, realValue);
		}

		@Override
		public String toString() {
			return propertyName + " < " + propertyValue;
		}

	}

}
