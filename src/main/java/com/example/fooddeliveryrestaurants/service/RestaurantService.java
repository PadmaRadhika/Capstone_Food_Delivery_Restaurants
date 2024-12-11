package com.example.fooddeliveryrestaurants.service;

import java.util.List;
import java.util.Optional;

import com.example.fooddeliveryrestaurants.model.Restaurant;

public interface RestaurantService {
	public Restaurant addRestaurant(Restaurant restaurant);
	public Restaurant editRestaurant(Integer id, Restaurant updateData);
	public void deleteRestaurant(Integer id);
	public Optional<Restaurant> getRestaurantById(Integer id);
	public List<Restaurant> getAllRestaurants();
	public List<Restaurant> searchRestaurants(String searchStr);
	public long getRestaurantsCount();
	public int getRestaurantIdByName(String name);
}
