package com.SpecialProjects.coupons;

/**
 * Created by David Smith on 7/5/2014.
 */
public class Coordinates {
    private int _id;
    private double _latitude;
    private double _longitude;

    public Coordinates(){

    }
    public Coordinates(int id, double latitude, double longitude){
        this._id = id;
        this._latitude = latitude;
        this._longitude = longitude;
    }
    public Coordinates(double latitude, double longitude) {
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public Double get_latitude() {
        return _latitude;
    }

    public Double get_longitude() {
        return _longitude;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public int get_id() {
        return _id;
    }
}
