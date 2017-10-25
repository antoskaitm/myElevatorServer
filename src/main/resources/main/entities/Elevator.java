package main.entities;


import java.io.Serializable;
import java.util.LinkedHashSet;

public class Elevator  implements Serializable{
    private Integer lastFloor = 7;
    private Integer currentFloor = 1;
    private LinkedHashSet<Integer> stopPoints=new LinkedHashSet<Integer>();

    private Integer speed=1;
    private Integer acceleration=2;

    private Integer floorsCount=7;
    private Integer buildingHeight=30;
}
