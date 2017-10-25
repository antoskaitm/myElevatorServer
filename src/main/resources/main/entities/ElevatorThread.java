package main.entities;

public class ElevatorThread {
    private boolean suspend = false;
    private ElevatorCondition elevator;
    private Building building;

    private final Integer speed=1;
    private final Integer acceleration=2;

    public ElevatorThread(ElevatorCondition elevator)
    {
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
                    //куданибудь логировать
                    ex.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void move() {
        /*Integer path = building.getFloorHeight();
        Integer accelerationTime = speed / acceleration;
        Integer stopTime = accelerationTime;
        if (path / 2 < acceleration * accelerationTime * accelerationTime / 2) {
            stopTime = accelerationTime / 2;
        }
        Integer stopPath = stopTime * stopTime * acceleration / 2;
        Integer constantSpeedPath = path - 2 * stopPath;
        //ускорились//Thread.sleep(stopTime);
        Boolean isPathLong = true;
        if (!stopNextFloor() && stopTime != accelerationTime) {
            //доускорились////Thread.sleep(accelerationTime-stopTime);
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
        }
        Integer constantSpeedTime = constantSpeedPath / speed;
        //постоянное////Thread.sleep(constantSpeedTime);
        Integer moveTime = stopPath / speed;
        while (!stopNextFloor()) {
            //движемся с постоянной участок остановки//Thread.sleep(moveTime);
            changeCurrentFloor();
            if (isPathLong) {
                //движемся с постоянной участок ускорения//Thread.sleep(moveTime);
            }
            //движемся с постоянной//Thread.sleep(constantSpeedTime);
            if (currentFloor == 0 || currentFloor == building.getLastFloor()) {
                break;
            }
        }
        //остановка//Thread.sleep(stopTime);
        */

        while (elevator.canMove() && !elevator.stopNextFloor()) {
            elevator.changeCurrentFloor();
        }
        if(elevator.canMove()) {
            elevator.changeCurrentFloor();
        }
    }
}
