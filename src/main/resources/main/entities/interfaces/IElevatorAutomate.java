package main.entities.interfaces;

import main.entities.events.Action;

interface IElevatorAutomate {
    void stop();

    Boolean stopNextFloor();

    void changeCurrentFloor();

    Boolean canMove();

    void onStop(Action event);
}
