package com.example.info6205_team02.SimulatedAnnealing;

public class City {
    private double x;
    private double y;
    private int c;

    public City(int c,double x,double y) {
        this.c = c;
        this.x = x;
        this.y = y;
    }

    public double distanceToCity(City city1, City city2) {
        double radius = 6371;
        double x1 = city1.x*Math.PI/180;
        double y1= city1.y*Math.PI/180;
        double x2 = city2.x*Math.PI/180;
        double y2= city2.y*Math.PI/180;
        double deltaLat = x2-x1;
        double deltaLon = y2-y1;
        double a = Math.pow(Math.sin(deltaLat/2),2)+(Math.cos(x1)*Math.cos(x2)) * Math.pow(Math.sin(deltaLon/2),2);
        double c = 2 *Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double distance = (radius * c)*1000;
        return distance;
    }

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
