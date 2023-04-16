package com.example.info6205_team02.Christofides;

/* This is the edge associated with the vertex along with its weight.
 At the same time it takes into consideration the parent and the child,
 which will be useful for deriving the minimal spanning tree of the graph */

public class Edge {
	
	int parent;
	int child;
	double weight;
	Vertex Owner;
	Vertex Child;

public Edge(Vertex parentEdge, Vertex childEdge, double distance) {
	parent = parentEdge.getID();
	child = childEdge.getID();
	weight = distance;
	Owner = parentEdge;
	Child = childEdge;
}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getChild() {
		return child;
	}

	public void setChild(Vertex child) {
		Child = child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Vertex getOwner() {
		return Owner;
	}

	public void setOwner(Vertex owner) {
		Owner = owner;
	}
}
