package main.entities;

public class ElevatorThread {
    private boolean suspend = false;
    private IElevatorAutomate elevator;
    private Building building;

    private final Integer speed = 1;
    private final Integer acceleration = 2;

    public ElevatorThread(IElevatorAutomate elevator,Building building) {
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
        Integer path = building.getFloorHeight();
        Integer accelerationTime = speed / acceleration;
        Integer stopTime = accelerationTime;
        if (path / 2 < acceleration * accelerationTime * accelerationTime / 2) {
            stopTime = accelerationTime / 2;
        }
        Integer stopPath = stopTime * stopTime * acceleration / 2;
        Integer constantSpeedPath = path - 2 * stopPath;
        Thread.sleep(stopTime);
        Boolean isPathLong = true;
        if (!elevator.stopNextFloor() && stopTime != accelerationTime) {
            Thread.sleep(accelerationTime - stopTime);
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
        }
        Integer constantSpeedTime = constantSpeedPath / speed;
        Thread.sleep(constantSpeedTime);
        Integer moveTime = stopPath / speed;
        while (elevator.canMove() && !elevator.stopNextFloor()) {
            Thread.sleep(moveTime);
            elevator.changeCurrentFloor();
            if (isPathLong) {
                Thread.sleep(moveTime);
            }
            Thread.sleep(constantSpeedTime);
        }
        Thread.sleep(stopTime);
*/

        while (elevator.canMove() && !elevator.stopNextFloor()) {
            Thread.sleep(1000);
            elevator.changeCurrentFloor();

        }
        Thread.sleep(1000);
        if (elevator.canMove()) {
            elevator.changeCurrentFloor();
        }
    }
}
