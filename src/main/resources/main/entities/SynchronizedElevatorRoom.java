package main.entities;

import main.entities.interfaces.IElevatorAutomate;
import main.entities.interfaces.IElevatorAutomateble;
import main.entities.interfaces.IElevatorRoom;

import java.io.Serializable;

/**view for synchronization IElevatorRoom
 *
 */
public class SynchronizedElevatorRoom implements IElevatorRoom, IElevatorAutomateble, Serializable {

    private IElevatorRoom elevatorRoom;

    public <T extends IElevatorRoom& IElevatorAutomateble & Serializable> SynchronizedElevatorRoom(T elevatorRoom) {
        this.elevatorRoom = elevatorRoom;
    }

    @Override
    public IElevatorAutomate getElevatorAutomate() {
        return ((IElevatorAutomateble)elevatorRoom).getElevatorAutomate();
    }

    @Override
    public synchronized Integer callElevator(int floor) {
        return elevatorRoom.callElevator(floor);
    }

    @Override
    public synchronized Integer getCurrentFloor() {
        return elevatorRoom.getCurrentFloor();
    }

    @Override
    public synchronized Boolean sendElevator(int floor, int personId) {
        return elevatorRoom.sendElevator(floor,personId);
    }

    @Override
    public synchronized boolean isInElevator(Integer personId) {
        return elevatorRoom.isInElevator(personId);
    }

    @Override
    public synchronized boolean isCallElevator(Integer personId) {
        return elevatorRoom.isCallElevator(personId);
    }

    @Override
    public synchronized boolean isSendElevator(Integer personId) {
        return elevatorRoom.isSendElevator(personId);
    }

    @Override
    public synchronized String getPersonCondition(Integer personId) {
        return elevatorRoom.getPersonCondition(personId);
    }
}
