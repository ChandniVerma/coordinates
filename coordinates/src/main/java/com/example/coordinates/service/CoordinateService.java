package com.example.coordinates.service;

public interface CoordinateService {

	public double findDistanceOnBasisCoordinates(double lat1, double lon1, double lat2, double lon2);

	public double[] getCoordinatesOfAddress(String address);
}
