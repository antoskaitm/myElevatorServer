package main.entities;

import main.entities.events.Action;

interface IElevatorAutomateble {
    public IElevatorAutomate getElevatorAutomate();

    interface IElevaterRoom {
        Integer callElevator(int floor);

        Integer getCurrentFloor();

        Boolean SendElevator(int floor, int personId);
    }

    interface IElevatorAutomate {
        void stop();

        Boolean stopNextFloor();

        void changeCurrentFloor();

        Boolean canMove();

        void onStop(Action event);
    }

    interface IElevatorAutomateble {
        public IElevatorAutomate getElevatorAutomate();
    }

    interface IElevatorUi {
        Integer getCurrentFloor();

        Boolean callup(Integer floor);
    }
}
