package aleph;

public interface PersistenceProvider {

	void save(AbstractBuilder<?> builder);

	void close();

	void clear();

}
