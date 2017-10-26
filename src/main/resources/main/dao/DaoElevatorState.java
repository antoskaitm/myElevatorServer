package main.dao;

import main.entities.IElevaterRoom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DaoElevatorState {
    private File file = new File("/WEB-INF/lift.txt");

    public IElevaterRoom getElevator() {
        return null;
    }

    public void saveElevator(IElevaterRoom room) throws IOException {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file)))
        {

        }
    }
}
