package main.dao;

import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorRoom;

import java.io.*;

public interface IDao {

    void save(IBuilding building) throws IOException;

    IBuilding getBuilding() throws IOException;
}
