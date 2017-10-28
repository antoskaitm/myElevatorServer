package main.entities.interfaces.primitive;

import main.entities.primitive.Person;

public interface IRoom {
    boolean admit(Person person);

    boolean release(Person person);
}
