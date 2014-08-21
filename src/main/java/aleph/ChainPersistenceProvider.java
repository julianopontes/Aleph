package aleph;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ChainPersistenceProvider implements PersistenceProvider {

	@Autowired
	private List<PersistenceProvider> providers;

	@Override
	public void save(List<AbstractBuilder<?>> builders) {
		for (PersistenceProvider p : providers) {
			p.save(builders);
		}

	}

	@Override
	public void clear() {
		for (PersistenceProvider p : providers) {
			p.clear();
		}

	}

}
