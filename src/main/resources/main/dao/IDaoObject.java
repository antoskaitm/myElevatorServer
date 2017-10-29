package main.dao;

import java.io.IOException;
import java.io.Serializable;

public interface IDaoObject<T extends Serializable> {

	void save(T building) throws IOException;

	T load() throws IOException;
}
