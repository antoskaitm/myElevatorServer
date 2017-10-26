package main.entities;

public class ElevatorThread {
    private boolean suspend = false;
    private IElevatorAutomate elevator;
    private Building building;

    private final Double speed = 1.;
    private final Double acceleration = 2.;

    public ElevatorThread(IElevatorAutomate elevator, Building building) {
        this.building = building;
        this.elevator = elevator;
    }

    public void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!suspend) {
                        move();
                        Thread.sleep(100);
                        elevator.stop();
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
        /*
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
        if (!elevator.stopNextFloor() && stopTime != accelerationTime) {
            moveLift(accelerationTime - stopTime);
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
        }
        double constantSpeedTime = constantSpeedPath / speed;
        moveLift(constantSpeedTime);
        double moveTime = stopPath / speed;
        while (elevator.canMove() && !elevator.stopNextFloor()) {
            moveLift(moveTime);
            elevator.changeCurrentFloor();
            if (isPathLong) {
                moveLift(moveTime);
            }
            moveLift(constantSpeedTime);
        }
        moveLift(stopTime);
          if (elevator.canMove()) {
            elevator.changeCurrentFloor();

        */
        //более удобный аналог
        while (elevator.canMove() && !elevator.stopNextFloor()) {
            moveLift(1);
            elevator.changeCurrentFloor();

        }
        moveLift(1);
        if (elevator.canMove()) {
            elevator.changeCurrentFloor();
        }
    }

    private void moveLift(double seconds) throws InterruptedException {
        Thread.sleep((int) seconds * 1000);
    }
}
