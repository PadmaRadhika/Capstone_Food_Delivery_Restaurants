package com.example.fooddeliveryrestaurants.Exception;

public class RestaurantNotFoundException extends RuntimeException{
	public RestaurantNotFoundException(String message) {
		super(message);
	}
}
