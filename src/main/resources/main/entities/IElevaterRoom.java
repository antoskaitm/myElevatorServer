package main.entities;

public interface IElevaterRoom {
    Integer callElevator(int floor);

    Integer getCurrentFloor();

    Boolean sendElevator(int floor, int personId);

    boolean isInElevator(Integer id);

    boolean isCallElevator(Integer id);

    boolean isSendElevator(Integer id);

    String getPersonCondition(Integer personId);
}