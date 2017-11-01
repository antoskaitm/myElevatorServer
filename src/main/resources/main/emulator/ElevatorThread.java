package main.emulator;

import main.dao.IDaoObject;
import main.entities.interfaces.primitive.IAutomate;
import main.entities.interfaces.primitive.IBuilding;

import java.io.IOException;
import java.io.Serializable;

public class ElevatorThread<TBuilding extends IBuilding & Serializable> {
	private boolean suspend = false;
	private IAutomate automate;
	private TBuilding building;
	private IDaoObject<TBuilding> dao;

	private final Double speed = 1.;
	private final Double acceleration = 2.;
	private final Double maxAccelerationTime = speed / acceleration;
	private final Double maxStopPath = acceleration * maxAccelerationTime * maxAccelerationTime / 2;

	public ElevatorThread(IDaoObject<TBuilding> dao, IAutomate automate, TBuilding building) throws IOException {
		this.dao = dao;
		this.automate = automate;
		this.building = building;
	}

	public void run() {
		Thread thread = new Thread(() -> {
			try {
				while (!suspend) {
					if (automate.isCalled()) {
						Thread.sleep(100);
						move();
						automate.stop();
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
		boolean isElevatorAccelerated = false;
		double movingTime = 0.;
		double remainingPath = 0.;
		boolean stopNext = automate.isStopNext();
		while (automate.canChangeCurrentFloor() && !stopNext) {
			if (remainingPath > 0.1) {
				moveLift(remainingPath / speed);
				automate.changeCurrentFloor();
			}
			remainingPath = building.getFloorHeight(automate.getCurrentFloor()).doubleValue();
			if (isElevatorAccelerated) {
				remainingPath -= maxStopPath;
				moveLift(remainingPath / speed);
			} else if (remainingPath < maxStopPath * 2) {
				remainingPath /= 2;
				movingTime = Math.sqrt(2 * remainingPath / acceleration);
			} else {
				movingTime = maxAccelerationTime;
				remainingPath -= maxStopPath;
				remainingPath -= maxStopPath;
				movingTime += (remainingPath - maxStopPath) / speed;
				isElevatorAccelerated = true;
			}
			moveLift(movingTime);
			stopNext = automate.isStopNext();
			if (!isElevatorAccelerated && !stopNext) {
				remainingPath = remainingPath * 2 - maxStopPath;
				moveLift(maxAccelerationTime - movingTime);
				isElevatorAccelerated = true;
			}
		}
		moveLift(movingTime);
		automate.changeCurrentFloor();
	}

	private void moveLift(double seconds) throws InterruptedException {
		if (seconds < 0.001) {
			return;
		}
		Thread.sleep((int) seconds * 100);
	}
}