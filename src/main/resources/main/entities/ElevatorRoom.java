package main.entities;

import main.entities.events.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**view for object implementing IElevatorAutomateble,IElevatorUi
 * this class keep information about people
 * @param <T>
 */
public class ElevatorRoom<T extends IElevatorAutomateble&IElevatorUi> implements IElevatorRoom, IElevatorAutomateble , Serializable {

    static final long serialVersionUID = -1000000000000L;

    private Integer counterPeopleId = 0;
    private T elevatorCondition;
    private Map<Integer, List<Integer>> sendElevatorPersons = new HashMap<Integer, List<Integer>>();
    private Map<Integer, List<Integer>> callElevatorPersons = new HashMap<Integer, List<Integer>>();
    private Set<Integer> personsInLift = new HashSet<>();

    public ElevatorRoom(T elevatorCondition) {
        this.elevatorCondition = elevatorCondition;
        this.elevatorCondition.getElevatorAutomate().onStop(new Action() {
            public void execute() {
                stop();
            }
        });
    }

    @Override
    public Integer callElevator(int floor) {

        if (elevatorCondition.callup(floor)) {
            Integer personId = counterPeopleId++;
            getList(callElevatorPersons, floor).add(personId);
            return personId;
        }
        return null;
    }

    @Override
    public Integer getCurrentFloor() {
        return elevatorCondition.getCurrentFloor();
    }

    private List<Integer> getList(Map<Integer, List<Integer>> map, int floor) {
        if (!map.containsKey(floor)) {
            map.put(floor, new LinkedList<Integer>());
        }
        return map.get(floor);
    }

    @Override
    public Boolean sendElevator(int floor, int personId) {
        if (elevatorCondition.getCurrentFloor().equals(floor)) {
            return false;
        }
        if (personsInLift.contains(personId)) {
            if (elevatorCondition.callup(floor)) {
                getList(sendElevatorPersons, floor).add(personId);
                personsInLift.remove(personId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInElevator(Integer personId) {
        return personsInLift.contains(personId);
    }

    private boolean continsIn(Map<Integer, List<Integer>> map, Integer personId) {
        for (List<Integer> waits : map.values()) {
            if (waits.contains(personId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCallElevator(Integer personId) {
        return continsIn(callElevatorPersons, personId);
    }

    @Override
    public boolean isSendElevator(Integer personId) {
        return continsIn(sendElevatorPersons, personId);
    }

    private void stop() {
        Integer floor = elevatorCondition.getCurrentFloor();
        this.personsInLift.addAll(getList(callElevatorPersons, floor));
        getList(callElevatorPersons, floor).clear();
        getList(sendElevatorPersons, floor).clear();
    }

    @Override
    public IElevatorAutomate getElevatorAutomate() {
        return elevatorCondition.getElevatorAutomate();
    }

    @Override
    public String getPersonCondition(Integer personId) {
        if (personId == null) {
            return "stand to call up lift";
        }
        if (personsInLift.contains(personId)) {
            return "stand in lift";
        }
        if (continsIn(callElevatorPersons, personId)) {
            return "wait to in";
        }
        if (continsIn(sendElevatorPersons, personId)) {
            return "wait to out";
        }
        return "stand to call up lift";
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        long serialVersionUID = stream.readLong();
        counterPeopleId = (Integer) stream.readObject();
        elevatorCondition = (T) stream.readObject();
        sendElevatorPersons = (Map<Integer, List<Integer>>) stream.readObject();
        callElevatorPersons = new HashMap<Integer, List<Integer>>();
        personsInLift = new HashSet<>();
        elevatorCondition.getElevatorAutomate().onStop(new Action() {
            public void execute() {
                stop();
            }
        });
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeObject(counterPeopleId);
        stream.writeObject(elevatorCondition);
        stream.writeObject(sendElevatorPersons);
    }
}
