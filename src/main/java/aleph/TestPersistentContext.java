package aleph;

import java.util.ArrayList;
import java.util.List;

public class TestPersistentContext {
	private static TestPersistentContext ctx;

	private List<AbstractBuilder<?>> builders = new ArrayList<AbstractBuilder<?>>();

	private TestPersistentContext() {
		super();
	}

	public void add(AbstractBuilder<?> builder) {
		this.builders.add(builder);
	}

	public void saveAll() {

		ChainPersistenceProvider persistenceProvider = ContextUtil.getBean(ChainPersistenceProvider.class);
		persistenceProvider.save(builders);
		builders.clear();
	}

	public void clear() {
		ChainPersistenceProvider persistenceProvider = ContextUtil.getBean(ChainPersistenceProvider.class);
		persistenceProvider.clear();
	}

	public static TestPersistentContext get() {
		if (ctx == null) {
			ctx = new TestPersistentContext();
		}
		return ctx;
	}

}
