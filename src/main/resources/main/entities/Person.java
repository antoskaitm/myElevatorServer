package main.entities;

import main.entities.interfaces.IPersonCondition;

public class Person {
    private Integer id;
    private IPersonCondition condition;
    private Integer callFloor;
    private Integer sendFloor;



    public Person(Integer id,Integer callFloor)
    {
        this.id = id;
        this.callFloor = callFloor;
        condition = PersonCondition.CALLED_ELEVATOR;
    }

    public void setSendFloor(Integer sendFloor)
    {
        this.sendFloor = sendFloor;
    }

    public Integer getId()
    {
        return id;
    }

    public Integer getCallFloor() {
        return callFloor;
    }

    public Integer getSendFloor() {
        return sendFloor;
    }

    public IPersonCondition getCondition()
    {
        return condition;
    }

    public void setCondition(IPersonCondition newCondition) {
        if(condition == newCondition)
        {
            return;
        }
        if (condition == PersonCondition.DIDNOT_CALL_ELEVATOR) {
            condition = PersonCondition.CALLED_ELEVATOR;
        } else if (condition == PersonCondition.CALLED_ELEVATOR
                && newCondition == PersonCondition.STAND_IN_ELEVATOR) {
            condition = PersonCondition.STAND_IN_ELEVATOR;
        } else if (condition == PersonCondition.STAND_IN_ELEVATOR
                && newCondition == PersonCondition.SENDED_ELEVATOR) {
            condition = PersonCondition.SENDED_ELEVATOR;
        } else if (condition == PersonCondition.SENDED_ELEVATOR
                && newCondition == PersonCondition.DIDNOT_CALL_ELEVATOR) {
            condition = PersonCondition.DIDNOT_CALL_ELEVATOR;
        }
        throw new IllegalStateException("Incorrect new state, current state: "+condition.getMessage());
    }
}
