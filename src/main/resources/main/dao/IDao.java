package main.dao;

import main.entities.interfaces.IBuilding;
import main.entities.interfaces.IElevatorRoom;

import java.io.*;

public interface IDao {

    IElevatorRoom getElevatorRoom() throws IOException;

    void save(IElevatorRoom room,IBuilding building) throws IOException;

    IBuilding getBuilding() throws IOException;
}
