package com.pluralsight.controller;

import java.util.List;

import com.pluralsight.util.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@DeleteMapping(value = "/delete/{id}")
	public @ResponseBody Object deleteRide(@PathVariable(value = "id") Integer id) {
		rideService.deleteRide(id);
		return null;
	}

	@GetMapping(value = "test")
	public @ResponseBody Object test() {
		throw new DataAccessException("Testing exception thrown"){
		};
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ServiceError> handle(RuntimeException e) {
		ServiceError serviceError = new ServiceError(HttpStatus.OK.value(),e.getMessage());
		return new ResponseEntity<>(serviceError,HttpStatus.OK);
	}
}
