package com.example.fooddeliveryrestaurants.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fooddeliveryrestaurants.Exception.RestaurantNotFoundException;
import com.example.fooddeliveryrestaurants.dao.RestaurantRepository;
import com.example.fooddeliveryrestaurants.model.Restaurant;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public Restaurant addRestaurant(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant editRestaurant(Integer id, Restaurant updateData) {		
		// TODO Auto-generated method stub
		return restaurantRepository.findById(id).map(existingRestaurant -> {
            // Update the fields (only the ones provided)
            if (updateData.getName() != null) {            	
                existingRestaurant.setName(updateData.getName());
            }
            if (updateData.getCuisine() != null) {            	
                existingRestaurant.setCuisine(updateData.getCuisine());
            }
            if (updateData.getAddress() != null) {            	
                existingRestaurant.setAddress(updateData.getAddress());
            }            
            // Save the updated restaurant
            return restaurantRepository.save(existingRestaurant);
        }).orElse(null); // If not found, return null
	}

	@Override
	public void deleteRestaurant(Integer id) {
		// TODO Auto-generated method stub
		restaurantRepository.deleteById(id);
	}

	@Override
	public Optional<Restaurant> getRestaurantById(Integer id) {
		// TODO Auto-generated method stub
		return restaurantRepository.findById(id);
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		// TODO Auto-generated method stub
		return new ArrayList<Restaurant>((Collection<? extends Restaurant>) restaurantRepository.findAll());
	}
	
	 @Override
	    public List<Restaurant> searchRestaurants(String searchStr) {
	        return restaurantRepository.searchRestaurants(searchStr);
	    }
	 @Override
	    public long getRestaurantsCount() {
	        return restaurantRepository.count();
	    }
	 @Override
	 public int getRestaurantIdByName(String name) {		 
			return restaurantRepository.findIdByName(name)
					.orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with name: " + name));		 
	 }

}
