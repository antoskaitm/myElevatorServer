package main.emulator;

import main.dao.DaoState;
import main.dao.IDaoStaitabel;
import main.entities.Building;
import main.entities.interfaces.IBuilding;
import main.entities.interfaces.IElevatorAutomate;
import main.entities.interfaces.IElevatorAutomateble;
import main.entities.interfaces.IElevatorRoom;

import java.io.Serializable;

public class ElevatorThread<T extends IElevatorRoom &IElevatorAutomateble &Serializable> {
    private boolean suspend = false;
    private IElevatorAutomate automate;
    private T elevator;
    private IBuilding building;
    private IDaoStaitabel dao;

    private final Double speed = 1.;
    private final Double acceleration = 2.;

    public ElevatorThread(T elevator, IBuilding building) {
        this.building = building;
        this.automate = elevator.getElevatorAutomate();
        this.elevator = elevator;
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
                        automate.stop();
                        dao.saveElevator(elevator);
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
        if (!automate.stopNextFloor() && stopTime != accelerationTime) {
            moveLift(accelerationTime - stopTime);
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
        }
        double constantSpeedTime = constantSpeedPath / speed;
        moveLift(constantSpeedTime);
        double moveTime = stopPath / speed;
        while (automate.canMove() && !automate.stopNextFloor()) {
            moveLift(moveTime);
            automate.changeCurrentFloor();
            if (isPathLong) {
                moveLift(moveTime);
            }
            moveLift(constantSpeedTime);
        }
        moveLift(stopTime);
          if (automate.canMove()) {
              automate.changeCurrentFloor();
          }

        /*
        //более удобный аналог
        while (automate.canMove() && !automate.stopNextFloor()) {
            moveLift(1);
            automate.changeCurrentFloor();

        }
        moveLift(1);
        if (automate.canMove()) {
            automate.changeCurrentFloor();
        }
        */
    }

    private void moveLift(double seconds) throws InterruptedException {
        Thread.sleep((int) seconds * 1000);
    }
}
