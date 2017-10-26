package main.entities;

import main.entities.events.Action;

import java.util.*;

/**view for IElevatorAutomateble,IElevatorUi
 * this class keep information about people
 * @param <T>
 */
public class ElevatorRoom<T extends IElevatorAutomateble&IElevatorUi> implements IElevaterRoom, IElevatorAutomateble {
    private Integer counterPeopleId = 0;
    private final T elevatorCondition;
    private final Map<Integer, List<Integer>> sendElevatorPersons = new HashMap<Integer, List<Integer>>();
    private final Map<Integer, List<Integer>> callElevatorPersons = new HashMap<Integer, List<Integer>>();
    private final Set<Integer> personsInLift = new HashSet<Integer>();

    public ElevatorRoom(T elevatorCondition) {
        this.elevatorCondition = elevatorCondition;
        this.elevatorCondition.getElevatorAutomate().onStop(new Action() {
            @Override
            public void execut() {
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
    public Boolean SendElevator(int floor, int personId) {
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

    private void stop() {
        Integer floor =  elevatorCondition.getCurrentFloor();
        this.personsInLift.addAll(getList(callElevatorPersons, floor));
        getList(callElevatorPersons, floor).clear();
        getList(sendElevatorPersons, floor).clear();
    }

    @Override
    public IElevatorAutomate getElevatorAutomate() {
        return elevatorCondition.getElevatorAutomate();
    }

    public String getPersonCondition(Integer personId) {
        if(personId == null)
        {
            return "call lift";
        }
        if(personsInLift.contains(personId))
        {
            return "in lift";
        }
        for (List<Integer> waits: callElevatorPersons.values())
        {
            if(waits.contains(personId)) {
                return "wait to in";
            }
        }
        for (List<Integer> waits: sendElevatorPersons.values())
        {
            if(waits.contains(personId)) {
                return "wait to out";
            }
        }
        return "call lift";
    }
}
