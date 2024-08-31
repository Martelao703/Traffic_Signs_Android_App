package com.example.its_app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import OBUSDK.GPSLocation;

public class WayPointIterator {
    private List<GPSLocation> wayPoints;
    private int currentIndex;

    public WayPointIterator() {
        this.wayPoints = new ArrayList<GPSLocation>();
        this.currentIndex = 0;
    }

    public int currentWayPointPosition() {
        return currentIndex;
    }

    public int wayPointsCount() {
        return wayPoints.size();
    }

    public void ResetIterator() {
        currentIndex = 0;
    }

    public GPSLocation getNextCoordinate(){
        if (currentIndex >= this.wayPoints.size())
        {
            return null;
        }
        else
        {
            GPSLocation wayPoint = this.wayPoints.get(this.currentIndex);
            this.currentIndex++;

            return wayPoint;
        }
    }

}
