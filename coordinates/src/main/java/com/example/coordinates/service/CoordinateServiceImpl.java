package com.example.coordinates.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.coordinates.model.NominatimResponse;

@Service
public class CoordinateServiceImpl implements CoordinateService {
	private static final double EARTH_RADIUS = 6371.0;

	@Override
	public double findDistanceOnBasisCoordinates(double latitudeFrom, double longitudeFrom, double latitudeTo,
			double longitudeTo) {
		// Convert latitude and longitude from degrees to radians
		latitudeFrom = Math.toRadians(latitudeFrom);
		longitudeFrom = Math.toRadians(longitudeFrom);
		latitudeTo = Math.toRadians(latitudeTo);
		longitudeTo = Math.toRadians(longitudeTo);

		// Differences in coordinates
		double deltaLat = latitudeTo - latitudeFrom;
		double deltaLon = longitudeTo - longitudeFrom;

		// Haversine formula
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(latitudeFrom) * Math.cos(latitudeTo) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		// Calculate the distance
		double distance = EARTH_RADIUS * c;
		return distance;

	}

	public double[] getCoordinatesOfAddress(String address) {
		// Make a request to the Nominatim API
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + address;
		NominatimResponse[] response = restTemplate.getForObject(url, NominatimResponse[].class);

		if (response != null && response.length > 0) {
			double lat = Double.parseDouble(response[0].lat);
			double lon = Double.parseDouble(response[0].lon);
			return new double[] { lat, lon };
		}

		return null;
	}

}
