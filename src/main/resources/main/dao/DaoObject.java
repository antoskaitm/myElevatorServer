package main.dao;

import java.io.*;
import java.nio.file.Paths;

/**
 *
 */
public class DaoObject<T extends Serializable> implements IDaoObject<T> {
	private File file;
	private T defaultObject;

	public DaoObject(T object) throws IOException {
		this.defaultObject = object;
		file = CurrentDir();
	}

	@Override
	public void save(T object) throws IOException {
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
			stream.writeObject(object);
		}
	}

	@Override
	public T load() throws IOException, ClassNotFoundException {
		if(defaultObject == null) {
			try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
				defaultObject = (T) stream.readObject();
			}
		}
		return  defaultObject;
	}

	private static File CurrentDir() throws IOException {
		String path = DaoObject.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(1);
		Integer index = path.lastIndexOf("classes");
		path = path.subSequence(0, index).toString();
		File file = Paths.get(path, "building").toFile();
		return file;
	}

}
