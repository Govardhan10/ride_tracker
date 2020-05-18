package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout=3000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}

	@Test(timeout = 3000)
	public void testCreateRide() {
		RestTemplate template = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("Govardhan");
		ride.setDuration(100);

		Ride resultRide = template.postForObject("http://localhost:8080/ride_tracker/ride",ride, Ride.class);

		System.out.println("Ride Name : "+ resultRide.getName());
	}

	@Test(timeout = 3000)
	public void testGetRide() {
		RestTemplate template = new RestTemplate();

		Ride ride = template.getForObject("http://localhost:8080/ride_tracker/ride/1",Ride.class);

		System.out.println("Ride Name : "+ ride.getName());
	}

	@Test(timeout = 3000)
	public void testUpdateRide() {
		RestTemplate template = new RestTemplate();

		Ride ride = template.getForObject("http://localhost:8080/ride_tracker/ride/1",Ride.class);
		ride.setDuration(ride.getDuration()+10);

		template.put("http://localhost:8080/ride_tracker/ride",ride);

		System.out.println("Ride Name : "+ ride.getName());
	}

	@Test(timeout = 3000)
	public void testBatchUpdate() {
		RestTemplate template = new RestTemplate();
		template.getForObject("http://localhost:8080/ride_tracker/batch",Object.class);
	}

	@Test(timeout = 3000)
	public void testDelete() {
		RestTemplate template = new RestTemplate();
		template.delete("http://localhost:8080/ride_tracker/delete/1");
	}

	@Test(timeout = 3000)
	public void testException() {
		RestTemplate template = new RestTemplate();
		template.getForObject("http://localhost:8080/ride_tracker/test",Ride.class);
	}
}
