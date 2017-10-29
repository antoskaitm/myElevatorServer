package main.dao;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;

/**
 *
 */
public class DaoObject<T extends Serializable> implements IDaoObject<T> {
	private File file;
	private T object;

	public DaoObject(T object) throws IOException {
		this.object = object;
		file = CurrentDir();
	}

	@Override
	public void save(T object) throws IOException {
	   /*
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(building);
        }
        */
	}

	@Override
	public T load() throws IOException {
		return this.object;
        /*
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            try {
                return (IBuilding) stream.readObject();
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        }*/
	}

	private static File CurrentDir() throws IOException {
		String path = DaoObject.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(1);
		Integer index = path.lastIndexOf("classes");
		path = path.subSequence(0, index).toString();
		File file = Paths.get(path, "building").toFile();
		return file;
	}

}
