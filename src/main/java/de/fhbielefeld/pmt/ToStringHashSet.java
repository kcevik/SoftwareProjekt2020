package de.fhbielefeld.pmt;

import java.util.HashSet;

import org.checkerframework.checker.units.qual.Length;

public class ToStringHashSet<E> extends HashSet<E> {
	@Override
	public String toString() {
		String s = "";
		for (E e : this) {
			s += e.toString() + ", ";
		}

		if (s.length() > 2) {
			s = s.substring(0,s.length()-2);
		}
		return s;
	}
}
