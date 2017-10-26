package main.dao;

import main.entities.IBuilding;
import main.entities.IElevatorRoom;

import java.io.*;

public interface IDaoStaitabel {

    IElevatorRoom getElevator() throws IOException;

    void saveElevator(IElevatorRoom room) throws IOException;

    void saveBuilding(IBuilding room) throws IOException;

    IBuilding getBuilding() throws IOException;
}
