package com.pluralsight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;
import org.springframework.transaction.annotation.Transactional;

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
		return rideRepository.createRide(ride);
	}

	@Override
	public Ride getRide(Integer id) {
		return rideRepository.getRide(id);
	}

	@Override
	public void updateRide(Ride ride) {
		rideRepository.updateRide(ride);
	}

	@Override
    @Transactional
	public void batchUpdate() {
		List<Ride> rides = getRides();
		List<Object[]> pairs = new ArrayList<>();
        for (Ride ride : rides) {
            Object[] tmp = {new Date(),ride.getId()};
            pairs.add(tmp);
        }
		rideRepository.batchUpdate(pairs);
	}

    @Override
    public void deleteRide(Integer id) {
        rideRepository.deleteRide(id);
    }
}
