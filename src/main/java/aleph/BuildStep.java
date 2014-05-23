package aleph;

public interface BuildStep<E> {

	void build(E target);
}
