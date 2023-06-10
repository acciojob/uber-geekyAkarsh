package com.driver.model;

import com.driver.model.TripStatus;

import javax.persistence.*;

@Entity
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer tripBookingId;

    String fromLocation;

    String toLocation;

    Integer distanceInKm;

    @Enumerated(EnumType.STRING)
    TripStatus status;

    Integer bill;

    public TripBooking(){
    }

    public TripBooking(String fromLocation, String toLocation, Integer distanceInKm, TripStatus status, Integer bill) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.status = status;
        this.bill = bill;
    }

    public Integer getTripBookingId() {
        return tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Integer getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(Integer distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    @ManyToOne
    @JoinColumn
    Customer customer;

    @ManyToOne
    @JoinColumn
    Driver driver;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}