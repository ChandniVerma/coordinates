package com.example.coordinates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coordinates.service.CoordinateService;

@RestController
@RequestMapping("/coordinates")
public class CoordinateController {
	@Autowired
	CoordinateService coordinateService;

	@GetMapping("/distance")
	public ResponseEntity<String> calculateDistance(@RequestParam("addressFrom") String addressFrom,
			@RequestParam("addressTo") String addressto) {
		if (addressFrom.length() < 2 || addressFrom.length() < 2 || addressFrom.matches(addressto)) {
			return ResponseEntity.badRequest().body("Please pass correct address");
		}

		// Use the Nominatim API to convert addresses to coordinates
		double[] coordinatesFrom = coordinateService.getCoordinatesOfAddress(addressFrom);
		double[] coordinatesTo = coordinateService.getCoordinatesOfAddress(addressto);

		// Calculate the distance

		double distance = coordinateService.findDistanceOnBasisCoordinates(coordinatesFrom[0], coordinatesFrom[1],
				coordinatesTo[0], coordinatesTo[1]);

		String result = "Distance between " + addressFrom + " and " + addressto + ": " + distance + " km";

		return ResponseEntity.ok(result);

	}
}
