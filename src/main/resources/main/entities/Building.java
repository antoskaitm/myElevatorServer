package main.entities;

public class Building {
    private final Integer lastFloor;
    private final Integer groundFloor;
    private final Integer buildingHeight;
    private final Integer floorHeight;

    public Building(int lastFloor,int groundFloor, int buildingHeight)
    {
        Integer minGroundFloor = 0;
        if(groundFloor < minGroundFloor)
        {
            throw new IllegalArgumentException("Value of ground floor must be "+minGroundFloor+" or more");
        }
        if ( lastFloor <= groundFloor)
        {
            throw new IllegalArgumentException("Value of ground floor must be less than value of last floor");
        }
        Integer minFloorHeight = 3;
        if(buildingHeight < lastFloor*minFloorHeight )
        {
            throw new IllegalArgumentException("Value of building height for lastFloor must be more than "
                    + (lastFloor*minFloorHeight));
        }
        this.lastFloor = lastFloor;
        this.buildingHeight = buildingHeight;
        this.groundFloor = groundFloor;
        floorHeight =  this.buildingHeight/lastFloor;
    }

    public void checkFloor(int floor)
    {
        if(floor>groundFloor && floor< getLastFloor())
        {
            throw new NullPointerException("Floor does not exist");
        }
    }

    public Integer getLastFloor() {
        return lastFloor;
    }


    public Integer getFloorHeight()
    {
        return floorHeight;
    }
}
