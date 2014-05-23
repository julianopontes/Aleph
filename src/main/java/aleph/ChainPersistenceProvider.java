package aleph;

import java.util.List;

public class ChainPersistenceProvider implements PersistenceProvider {
	private final List<PersistenceProvider> providers;

	public ChainPersistenceProvider(List<PersistenceProvider> providers) {
		this.providers = providers;
	}

	@Override
	public void save(AbstractBuilder<?> builder) {
		for (PersistenceProvider p : providers) {
			p.save(builder);
		}

	}

	@Override
	public void close() {
		for (PersistenceProvider p : providers) {
			p.close();
		}

	}

	@Override
	public void clear() {
		for (PersistenceProvider p : providers) {
			p.clear();
		}

	}

}
