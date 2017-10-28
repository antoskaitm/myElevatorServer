package main.entities.primitive;

import main.entities.constants.PersonsConditions;
import main.entities.interfaces.primitive.IPersonCondition;

public class Person {
    private Integer id;
    private IPersonCondition condition;
    private Integer callFloor;
    private Integer sendFloor;

    public Person(Integer id,Integer callFloor)
    {
        this.id = id;
        this.callFloor = callFloor;
        condition = PersonsConditions.CALLED_ELEVATOR;
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
        if (condition == newCondition) {
            return;
        }
        if ((condition == PersonsConditions.DIDNOT_CALL_ELEVATOR || condition == PersonsConditions.TRY_CALL_AGAIN_ELEVATOR)
                && newCondition == PersonsConditions.CALLED_ELEVATOR) {
            condition = newCondition;
        } else if (condition == PersonsConditions.CALLED_ELEVATOR
                && newCondition == PersonsConditions.STAND_IN_ELEVATOR) {
            condition = newCondition;
        }else if (condition == PersonsConditions.CALLED_ELEVATOR
                && newCondition == PersonsConditions.TRY_CALL_AGAIN_ELEVATOR) {
            condition = newCondition;
        } else if (condition == PersonsConditions.STAND_IN_ELEVATOR
                && newCondition == PersonsConditions.SENDED_ELEVATOR) {
            condition =newCondition;
        } else if (condition == PersonsConditions.SENDED_ELEVATOR
                && newCondition == PersonsConditions.DIDNOT_CALL_ELEVATOR) {
            condition = newCondition;
        } else {
            throw new IllegalStateException("Incorrect new state:"+newCondition.getMessage()+". Current state: " + condition.getMessage());
        }
    }
}
