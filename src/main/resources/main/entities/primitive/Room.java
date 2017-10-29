package main.entities.primitive;

import main.entities.interfaces.primitive.IRoom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Room implements IRoom, Serializable {

	static final long serialVersionUID = -4000000000000L;

	private Integer size;
	private Integer peopleCount = 0;
	private transient List<Person> persons;

	public Room(int size) {
		this.size = size;
		persons = new LinkedList<>();
	}

	public boolean admit(Person person) {
		return peopleCount < size && persons.add(person);
	}

	public boolean release(Person person) {
		return persons.remove(person);
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		long serialVersionUID = stream.readLong();
		size = (Integer) stream.readObject();
		this.peopleCount = 0;
		persons = new LinkedList<>();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(size);
	}
}
