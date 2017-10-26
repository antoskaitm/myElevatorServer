package main.dao;

import main.entities.IElevaterRoom;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import java.io.*;
import java.nio.file.Paths;

public class DaoElevatorState {
    private File file;

    public DaoElevatorState() throws IOException {
        file = CurrentDir();
    }

    public synchronized IElevaterRoom getElevator() throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            return (IElevaterRoom) stream.readObject();
        }
    }

    private static File CurrentDir() throws IOException {
        String path = DaoElevatorState.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(1);
        Integer index = path.lastIndexOf("classes");
        path = path.subSequence(0, index).toString();
        File file = Paths.get(path, "lift").toFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public synchronized void saveElevator(IElevaterRoom room) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(room);
        }
    }
}
