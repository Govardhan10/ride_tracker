package com.pluralsight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	private RideRepository rideRepository;

	@Autowired
	public void setRideRepository(RideRepository rideRepository) {
		this.rideRepository = rideRepository;
	}

	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

	@Override
	public Ride createRide(Ride ride) {
		rideRepository.createRide(ride);
		return ride;
	}
}
