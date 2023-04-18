package com.example.info6205_team02.Christofides;

import java.util.ArrayList;


public class Vertex
{
	private int id;
	private double x_var;
	private double y_var;
	public ArrayList<Edge> connectedVertices;
	public   Edge 	   edge; //used for prims algorithm

	boolean evenEdge;

	public Vertex(int id, double x_var, double y_var)
	{
		this.id = id;
		this.x_var = x_var;
		this.y_var = y_var;
		this.connectedVertices = new ArrayList<>();
		this.edge       = null;
	}

	public int getID()
	{
		return id;
	}

	public double getX()
	{
		return x_var;
	}

	public double getY()
	{
		return y_var;
	}


}