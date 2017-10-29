package main.entities.primitive.general;

import java.io.Serializable;
import java.util.Arrays;

public class BitSet implements Serializable {
	private Integer offset = 0;
	private Boolean[] bits;

	public BitSet(int lowerBound, int length) {
		offset = -lowerBound;
		bits = new Boolean[length];
		Arrays.fill(bits, false);
	}

	public void set(int index) {
		bits[index + offset] = true;
	}

	public boolean get(int index) {
		return bits[index + offset];
	}

	public void clear(int index) {
		bits[index + offset] = false;
	}

	public int getSize() {
		return bits.length;
	}

	public int getLowerBorder() {
		return -offset;
	}

	public int getUpperBorder() {
		return bits.length - (offset + 1);
	}

	public Integer findIndex(Boolean reversed) {
		Integer index;
		if (reversed) {
			index = Arrays.asList(bits).lastIndexOf(true);
		} else {
			index = Arrays.asList(bits).indexOf(true);
		}
		if (index != -1) {
			return index - offset;
		}
		return null;
	}
}
