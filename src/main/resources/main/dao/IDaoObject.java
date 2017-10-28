package main.dao;

import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorRoom;

import java.io.*;

public interface IDaoObject<T extends Serializable> {

    void save(T building) throws IOException;

    T load() throws IOException;
}
