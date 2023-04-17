package com.example.info6205_team02.Christofides;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// This class is used to remove the duplicate vertices from the given tour and return a list of distinct vertices
public class RemoveDuplicate {
	
	
	private RemoveDuplicate() {
	}
	
public static List<Vertex> run(List<Vertex> tour) {
	return new ArrayList<>(tour)
			.stream()
			.distinct() //for duplicated elements, the element appearing first in the encounter order is preserved.
			.collect(Collectors.toCollection(ArrayList::new));
	}
}
