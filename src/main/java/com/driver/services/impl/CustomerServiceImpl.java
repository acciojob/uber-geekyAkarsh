package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

import static com.driver.model.TripStatus.CONFIRMED;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database

		customerRepository2.save(customer);
		return;
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function

		customerRepository2.deleteById(customerId);
		return;
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		List<Driver> drivers = driverRepository2.findAll();
		int curr_id = Integer.MAX_VALUE;
		for(Driver driver : drivers){

			if(driver.getCab().getAvailable() == Boolean.TRUE && driver.getDriverId()<curr_id){
				curr_id = driver.getDriverId();
			}
		}

		if(curr_id == Integer.MAX_VALUE){
			throw new Exception("No cab available!");
		}

		Driver driver = driverRepository2.findById(curr_id).get();
		Customer customer = customerRepository2.findById(customerId).get();
		Integer bill = driver.getCab().getPerKmRate()*distanceInKm;
		TripBooking currTrip = new TripBooking(fromLocation,toLocation,distanceInKm, CONFIRMED,bill);
		customer.getTripBookingList().add(currTrip);
		driver.getTripBookingList().add(currTrip);

		customerRepository2.save(customer);
		driverRepository2.save(driver);

		return currTrip;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking trip = tripBookingRepository2.findById(tripId).get();

		trip.setStatus(TripStatus.CANCELED);
		tripBookingRepository2.save(trip);
		return;
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking trip = tripBookingRepository2.findById(tripId).get();

		trip.setStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(trip);
		return;

	}
}
