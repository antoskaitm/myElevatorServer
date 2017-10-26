package main.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**numbers of floors begin from 0
 *
 */
public class Building implements Serializable {
    static final long serialVersionUID = -3000000000000L;
    private Integer floorCount;
    private Integer floorHeight;

    public Building(int floorCount, int buildingHeight) {
        Integer minFloorCount = 3;
        if (floorCount < minFloorCount) {
            throw new IllegalArgumentException("Floor count must be "+minFloorCount+" or more");
        }
        Integer minFloorHeight = 3;
        if (buildingHeight < floorCount * minFloorHeight) {
            throw new IllegalArgumentException("Value of building height for lastFloor must be more than "
                    + (floorCount * minFloorHeight));
        }
        this.floorCount = floorCount;
        floorHeight = buildingHeight / floorCount;
    }

    public void checkFloor(int floor) {
        if (floor < 0 || floor >= floorCount) {
            throw new NullPointerException("Floor does not exist");
        }
    }

    public Boolean hasFloor(int floor) {
        return floor >= 0 && floor < floorCount;
    }

    public Integer getLastFloor() {
        return floorCount - 1;
    }

    public Integer getFloorCount() {
        return floorCount;
    }

    public Integer getFloorHeight() {
        return floorHeight;
    }

    public Integer getGroundFloor() {
        return 0;
    }


    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        long serialVersionUID = stream.readLong();
        floorHeight = (Integer) stream.readObject();
        floorCount = (Integer) stream.readObject();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeLong(serialVersionUID);
        stream.writeObject(floorHeight);
        stream.writeObject(floorCount);
    }
}
