package main.entities.interfaces.primitive;

import main.entities.primitive.Request;

public interface IRoom {
	boolean admit(Request request);

	boolean release(Request request);
}
