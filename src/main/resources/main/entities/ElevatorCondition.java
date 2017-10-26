package main.entities;

import main.entities.events.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**numbers of floors begin from 0
 *
 */
public class ElevatorCondition implements IElevatorUi,IElevatorAutomateble,Serializable {
    static final long serialVersionUID = -2000000000000L;

    private Integer lastStopFloor = null;
    private Integer currentFloor = null;
    private final Boolean[] callPoints;
    private Integer direction = 0;
    private final Integer lastFloorNumber;
    private IElevatorAutomate automate;

    public ElevatorCondition(Integer floorCount) {
        Integer minFloorCount = 3;
        if (floorCount < minFloorCount) {
            throw new IllegalArgumentException("Floor count must be " + minFloorCount + " or more");
        }
        lastFloorNumber = floorCount - 1;
        callPoints = new Boolean[floorCount];
        Arrays.fill(callPoints, false);
        this.currentFloor = 0;
    }

    @Override
    public Integer getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public Boolean callup(Integer floor) {
        if (!hasFloor(floor)) {
            return false;
        }
        callPoints[floor] = true;
        if (lastStopFloor == null) {
            setLastFloor();
        }
        return callPoints[floor];
    }

    public IElevatorAutomate getElevatorAutomate() {
        if (automate == null) {
            automate = new IElevatorAutomate() {
                private List<Action> actions = new ArrayList<Action>();

                @Override
                public Boolean stopNextFloor() {
                    return callPoints[currentFloor + direction];
                }

                @Override
                public void stop() {
                    callPoints[currentFloor] = false;
                    for (Action action : actions) {
                        action.execute();
                    }
                    if (currentFloor.equals(lastStopFloor) || lastStopFloor == null) {
                        setLastFloor();
                    }
                }

                @Override
                public void changeCurrentFloor() {
                    currentFloor += direction;
                }

                @Override
                public Boolean canMove() {
                    return canMoveUp() || canMoveDown();
                }

                @Override
                public void onStop(Action action) {
                    this.actions.add(action);
                }

                private Boolean canMoveUp() {
                    return direction > 0 && !currentFloor.equals(lastFloorNumber);
                }

                private Boolean canMoveDown() {
                    return direction < 0 && !currentFloor.equals(0);
                }
            };
        }
        return automate;
    }

    private Boolean hasFloor(Integer floor) {
        return floor >= 0 && floor <= lastFloorNumber;
    }

    private void setLastFloor() {
        Integer lastStopFloor;
        if (direction >= 0) {

            lastStopFloor = Arrays.asList(callPoints).lastIndexOf(true);
        } else {
            lastStopFloor = Arrays.asList(callPoints).indexOf(true);
        }
        this.lastStopFloor = lastStopFloor == -1 ? null : lastStopFloor;
        changeDirection();
    }

    private void changeDirection() {
        if (currentFloor.equals(lastStopFloor) || lastStopFloor == null) {
            lastStopFloor = null;
            direction = 0;
        } else if (lastStopFloor - currentFloor < 0) {
            direction = -1;
        } else {
            direction = 1;
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }
}