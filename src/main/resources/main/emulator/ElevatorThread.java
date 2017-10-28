package main.emulator;

import main.dao.DaoState;
import main.dao.IDao;
import main.entities.interfaces.*;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorAutomate;

public class ElevatorThread {
    private boolean suspend = false;
    private final IAutomobileElevatorRoom elevator;
    private final IBuilding building;
    private IDao dao;

    private final Double speed = 1.;
    private final Double acceleration = 2.;

    public ElevatorThread(IBuilding building,Integer elevatorNumber) {
        elevator = building.getElevator(elevatorNumber);
        this.building = building;
    }

    public void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dao = new DaoState();
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
