package main.entities;

import main.entities.interfaces.IPersonCondition;

public class PersonCondition implements IPersonCondition {
    private final String message;

    private PersonCondition(String message) {
        this.message = message;
    }

    public static PersonCondition DIDNOT_CALL_ELEVATOR = new PersonCondition("Person didn't call elevator room");
    public static PersonCondition CALLED_ELEVATOR = new PersonCondition("Person wait to into elevator room");
    public static PersonCondition STAND_IN_ELEVATOR = new PersonCondition("Person stand in elevator room");
    public static PersonCondition SENDED_ELEVATOR = new PersonCondition("Person wait to out from elevator room");

    public String getMessage() {
        return message;
    }
}
