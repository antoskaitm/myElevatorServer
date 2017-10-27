package main.entities.interfaces.primitive;

import main.entities.events.Action;

public interface IElevatorAutomate {
    void stop();

    Boolean stopNextFloor();

    void changeCurrentFloor();

    Boolean canMove();

    void onStop(Action event);
}
