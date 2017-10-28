package main.dao;

import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorRoom;
import java.io.File;

import java.io.*;
import java.nio.file.Paths;

/**
 *
 */
public class DaoState implements IDao {
    private File file;
    private IBuilding building;

    public DaoState(IBuilding building) throws IOException {
        this.building = building;
        file = CurrentDir();
    }

    @Override
    public void save(IBuilding building) throws IOException {
       /*
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(building);
        }
        */
    }

    @Override
    public IBuilding getBuilding() throws IOException {
        return this.building;
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
        String path = DaoState.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(1);
        Integer index = path.lastIndexOf("classes");
        path = path.subSequence(0, index).toString();
        File file = Paths.get(path, "building").toFile();
        return file;
    }

}
