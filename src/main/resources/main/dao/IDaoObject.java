package main.dao;

import java.io.IOException;
import java.io.Serializable;

public interface IDaoObject<T extends Serializable> {

	void save(T object) throws IOException;

	T load() throws IOException, ClassNotFoundException;
}
