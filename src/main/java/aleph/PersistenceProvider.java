package aleph;

import java.util.List;

public interface PersistenceProvider {

	void save(List<AbstractBuilder<?>> builder);
	
	void clear();

}
