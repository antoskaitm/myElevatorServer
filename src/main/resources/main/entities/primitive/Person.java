package main.entities.primitive;

import main.entities.constants.PersonCondition;
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
        if (condition == newCondition) {
            return;
        }
        if ((condition == PersonCondition.DIDNOT_CALL_ELEVATOR || condition == PersonCondition.TRY_CALL_AGAIN_ELEVATOR)
                && newCondition == PersonCondition.CALLED_ELEVATOR) {
            condition = newCondition;
        } else if (condition == PersonCondition.CALLED_ELEVATOR
                && newCondition == PersonCondition.STAND_IN_ELEVATOR) {
            condition = newCondition;
        }else if (condition == PersonCondition.CALLED_ELEVATOR
                && newCondition == PersonCondition.TRY_CALL_AGAIN_ELEVATOR) {
            condition = newCondition;
        } else if (condition == PersonCondition.STAND_IN_ELEVATOR
                && newCondition == PersonCondition.SENDED_ELEVATOR) {
            condition =newCondition;
        } else if (condition == PersonCondition.SENDED_ELEVATOR
                && newCondition == PersonCondition.DIDNOT_CALL_ELEVATOR) {
            condition = newCondition;
        } else {
            throw new IllegalStateException("Incorrect new state:"+newCondition.getMessage()+". Current state: " + condition.getMessage());
        }
    }
}
