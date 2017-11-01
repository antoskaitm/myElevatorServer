package main.entities.interfaces.primitive;

public interface IRoom<T> {
	boolean admit(T request);

	boolean release(T request);
}
