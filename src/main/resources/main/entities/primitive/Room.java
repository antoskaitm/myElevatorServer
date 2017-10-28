package main.entities.primitive;

import main.entities.interfaces.primitive.IRoom;

import java.util.LinkedList;
import java.util.List;

public class Room implements IRoom {

    private Integer size;
    private Integer peopleCount=0;
    private List<Person> persons;

    public Room(int size)
    {
        this.size = size;
        persons = new LinkedList<>();
    }

    public boolean admit(Person person) {
        return peopleCount < size && persons.add(person);
    }

    public boolean release(Person person)
    {
        return persons.remove(person);
    }
}
