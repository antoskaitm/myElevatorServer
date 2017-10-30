package main.emulator;

import main.dao.IDaoObject;
import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorAutomate;

import java.io.IOException;
import java.io.Serializable;

public class ElevatorThread<TBuilding extends IBuilding & Serializable> {
	private boolean suspend = false;
	private IAutomobileElevatorRoom elevator;
	private TBuilding building;
	private IDaoObject<TBuilding> dao;

	private final Double speed = 1.;
	private final Double acceleration = 2.;
	private final Integer elevatorNumber;

	public ElevatorThread(IDaoObject<TBuilding> dao, Integer elevatorNumber) throws IOException {
		this.dao = dao;
		this.elevatorNumber =elevatorNumber;
	}

	public void run() {
		Thread thread = new Thread(() -> {
			try {
				building = dao.load();
				elevator = building.getElevator(elevatorNumber);
				while (!suspend) {
					if (getAutomate().isCalled())
					{
						move();
						getAutomate().stop();
						dao.save(building);
					}
					Thread.sleep(100);
				}
			} catch (Throwable ex) {
				//сделать нормальное логирование
				ex.printStackTrace();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	private void move() throws InterruptedException {
		//расчет движения лифта с учетом физики
		double path = building.getFloorHeight(getAutomate().getCurrentFloor()).doubleValue();
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
