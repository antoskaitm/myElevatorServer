package main.entities.interfaces;

import main.entities.interfaces.primitive.IAutomobileElevator;
import main.entities.interfaces.primitive.IElevator;
import main.entities.interfaces.primitive.IRequesting;

public interface IAutomobileElevatorRoom<T extends IRequesting> extends IElevator<T>, IAutomobileElevator {
}
