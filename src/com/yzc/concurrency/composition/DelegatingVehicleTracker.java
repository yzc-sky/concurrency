package com.yzc.concurrency.composition;

import jdk.nashorn.internal.parser.JSONParser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        this.locations = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    /**静态拷贝*/
    public Map<String, Point> getLocations() {
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    /**实时拷贝*/
    public Map<String, Point> getLocations1() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocations(String id, int x, int y){
        if(locations.replace(id, new Point(x,y)) == null){
            throw new IllegalArgumentException("invalid vehicle name:" + id);
        }
    }

    public static class Point {
        public final int x,y;
        public Point(int x,int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) {
        Map<String, Point> points = new HashMap<>();
        points.put("car1", new Point(1,2));
        points.put("car2", new Point(3,4));
        DelegatingVehicleTracker vehicleTracker = new DelegatingVehicleTracker(points);
        System.out.println("静态拷贝：" + vehicleTracker.getLocations());
        System.out.println("实时拷贝：" + vehicleTracker.getLocations1());
        vehicleTracker.setLocations("car2", 5,6);
        System.out.println("静态拷贝：" + vehicleTracker.getLocations());
        System.out.println("实时拷贝：" + vehicleTracker.getLocations1());
    }
}
