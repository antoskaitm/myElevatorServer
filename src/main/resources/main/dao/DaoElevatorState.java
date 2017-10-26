package main.dao;

import com.sun.xml.internal.bind.v2.runtime.output.XMLStreamWriterOutput;
import main.entities.IElevaterRoom;

import java.io.*;

public class DaoElevatorState {
    private File file = new File("/WEB-INF/lift.txt");

    public IElevaterRoom getElevator() throws IOException, ClassNotFoundException {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file)))
        {
            return (IElevaterRoom)stream.readObject();
        }
    }

    public void saveElevator(IElevaterRoom room) throws IOException {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file)))
        {
            stream.writeObject(room);
        }
    }
}
