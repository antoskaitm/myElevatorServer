package main.entities;

import main.entities.interfaces.IPersonCondition;

public class PersonCondition implements IPersonCondition {
    private final String message;

    private PersonCondition(String message) {
        this.message = message;
    }

    public static final IPersonCondition DIDNOT_CALL_ELEVATOR = new PersonCondition("Person didn't call elevator room");
    public static final IPersonCondition CALLED_ELEVATOR = new PersonCondition("Person wait to into elevator room");
    public static final IPersonCondition STAND_IN_ELEVATOR = new PersonCondition("Person stand in elevator room");
    public static final IPersonCondition SENDED_ELEVATOR = new PersonCondition("Person wait to out from elevator room");

    public String getMessage() {
        return message;
    }
}
