package main.dao;

import main.entities.interfaces.IBuilding;
import main.entities.interfaces.IElevatorRoom;
import java.io.File;

import java.io.*;
import java.nio.file.Paths;

/**
 *
 */
public class DaoState implements  IDaoStaitabel {
    private File file;

    public DaoState() throws IOException {
        file = CurrentDir();
    }

    @Override
    public IElevatorRoom getElevator() throws IOException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            try {
                return (IElevatorRoom) stream.readObject();
            }
           catch (ClassNotFoundException e) {
                e.printStackTrace();
               throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void saveElevator(IElevatorRoom room) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(room);
        }
    }

    @Override
    public void saveBuilding(IBuilding room) throws IOException {
       throw new UnsupportedOperationException();
    }

    @Override
    public IBuilding getBuilding() throws IOException {
        throw new UnsupportedOperationException();
    }

    private static File CurrentDir() throws IOException {
        String path = DaoState.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(1);
        Integer index = path.lastIndexOf("classes");
        path = path.subSequence(0, index).toString();
        File file = Paths.get(path, "lift").toFile();
        return file;
    }

}
