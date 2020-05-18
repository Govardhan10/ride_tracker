package com.pluralsight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pluralsight.model.Ride;
import com.pluralsight.service.RideService;

@Controller
public class RideController {

	private RideService rideService;

	@Autowired
	public void setRideService(RideService rideService) {
		this.rideService = rideService;
	}
	
	@RequestMapping(value = "/rides", method = RequestMethod.GET)
	public @ResponseBody List<Ride> getRides() {
		return rideService.getRides();
	}

	@PostMapping(value = "/ride")
	public @ResponseBody Ride createRide(@RequestBody Ride ride) {
		return rideService.createRide(ride);
	}

	@GetMapping(value = "/ride/{id}")
	public @ResponseBody Ride getRide(@PathVariable(value = "id") Integer id) {
		return rideService.getRide(id);
	}

	@PutMapping(value = "/ride")
	public void updateRide(@RequestBody Ride ride) {
		rideService.updateRide(ride);
	}

	@GetMapping(value = "/batch")
	public void batchUpdate() {
		rideService.batchUpdate();
	}
}
