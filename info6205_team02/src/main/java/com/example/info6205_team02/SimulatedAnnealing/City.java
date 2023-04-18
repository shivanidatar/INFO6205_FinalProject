//The code defines a class called "City" which represents a city on a map with its longitude and latitude coordinates.
package com.example.info6205_team02.SimulatedAnnealing;

//These lines declare the instance variables of the class "City",
// which are the longitude (x), latitude (y) and crimeIds (c) for the city.
public class City {
    private double x;
    private double y;
    private int c;
//This is a constructor of the class "City" that initializes the instance variables with the given arguments.
    public City(int c,double x,double y) {
        this.c = c;
        this.x = x;
        this.y = y;
    }
//This method calculates the distance between two cities using the haversine formula,
// which takes into account the curvature of the earth. It takes two City objects as input parameters,
// representing the two cities to calculate the distance between
    public double distanceToCity(City city1, City city2) {
        //These lines define the radius of the earth in kilometers and convert the longitude and
        // latitude values of each city from degrees to radians, which is required for the haversine formula.
        double radius = 6371;
        double x1 = city1.x*Math.PI/180;
        double y1= city1.y*Math.PI/180;
        double x2 = city2.x*Math.PI/180;
        double y2= city2.y*Math.PI/180;
        //These lines calculate the distance between the two cities using the haversine formula, which takes into account the
        //curvature of the earth. The calculated distance is returned as a double value.
        double deltaLat = x2-x1;
        double deltaLon = y2-y1;
        double a = Math.pow(Math.sin(deltaLat/2),2)+(Math.cos(x1)*Math.cos(x2)) * Math.pow(Math.sin(deltaLon/2),2);
        double c = 2 *Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double distance = (radius * c);
        return distance;
    }
//These are getter and setter methods for the instance variables of the "City" class,
// which provide access to the values of these variables and allow them to be modified.
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
