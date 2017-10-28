package main.emulator;

import main.dao.IDaoObject;
import main.entities.interfaces.*;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorAutomate;
import main.entities.primitive.Building;

import java.io.IOException;

public class ElevatorThread {
    private boolean suspend = false;
    private final IAutomobileElevatorRoom elevator;
    private final IBuilding building;
    private IDaoObject<Building> dao;

    private final Double speed = 1.;
    private final Double acceleration = 2.;

    public ElevatorThread(IDaoObject<Building> dao, Integer elevatorNumber) throws IOException {
        this.dao = dao;
        building = dao.load();
        elevator = building.getElevator(elevatorNumber);
    }

    public void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!suspend) {
                        move();
                        Thread.sleep(100);
                        getAutomate().stop();
//                        dao.save(elevator,building);
                    }
                } catch (Throwable ex) {
                    //сделать нормальное логирование
                    ex.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void move() throws InterruptedException {
        //расчет движения лифта с учетом физики
        double path = building.getFloorHeight().doubleValue();
        double accelerationTime = speed / acceleration;
        double stopTime = accelerationTime;
        if (path / 2 < acceleration * accelerationTime * accelerationTime / 2) {
            stopTime = accelerationTime / 2;
        }
        double stopPath = stopTime * stopTime * acceleration / 2;
        double constantSpeedPath = path - 2 * stopPath;
        moveLift(stopTime);
        Boolean isPathLong = true;
        if (!getAutomate().stopNextFloor() && stopTime != accelerationTime) {
            moveLift(accelerationTime - stopTime);
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
        }
        double constantSpeedTime = constantSpeedPath / speed;
        moveLift(constantSpeedTime);
        double moveTime = stopPath / speed;
        while (getAutomate().canMove() && !getAutomate().stopNextFloor()) {
            moveLift(moveTime);
            getAutomate().changeCurrentFloor();
            if (isPathLong) {
                moveLift(moveTime);
            }
            moveLift(constantSpeedTime);
        }
        moveLift(stopTime);
        if (getAutomate().canMove()) {
            getAutomate().changeCurrentFloor();
        }
    }

    private void moveLift(double seconds) throws InterruptedException {
        Thread.sleep((int) seconds * 500);
    }

    private IElevatorAutomate getAutomate() {
        return elevator.getElevatorAutomate();
    }
}
