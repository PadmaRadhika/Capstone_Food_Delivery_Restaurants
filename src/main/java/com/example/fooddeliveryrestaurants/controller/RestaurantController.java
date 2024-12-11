package com.example.fooddeliveryrestaurants.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fooddeliveryrestaurants.Exception.RestaurantNotFoundException;
import com.example.fooddeliveryrestaurants.model.Restaurant;
import com.example.fooddeliveryrestaurants.service.RestaurantService;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow CORS from Angular app
@RequestMapping(value = "/api/restaurants")
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping("/add")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant addedRestaurant= restaurantService.addRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRestaurant);
    }
	@GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {		
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);        
        return restaurant.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

	@GetMapping("/all")
	public ResponseEntity<List<Restaurant>> getAllRestaurants() {
		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		restaurantList = restaurantService.getAllRestaurants();
		return ((restaurantList != null && !restaurantList.isEmpty()) ? ResponseEntity.ok(restaurantList)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	@PutMapping("/edit/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Integer id, @RequestBody Restaurant restaurant) {		
		Restaurant updatedRestaurant= restaurantService.editRestaurant(id, restaurant);
		return( updatedRestaurant == null ?  ResponseEntity.notFound().build() : ResponseEntity.ok(updatedRestaurant));
    }
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable Integer id) {
		Optional<Restaurant> deletedRestaurant= restaurantService.getRestaurantById(id);
		restaurantService.deleteRestaurant(id);
		return deletedRestaurant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String searchStr) {
		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		restaurantList = restaurantService.searchRestaurants(searchStr);
		return ((restaurantList != null && !restaurantList.isEmpty()) ? ResponseEntity.ok(restaurantList)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	@GetMapping("/count")
	public ResponseEntity<Long> getRestaurantsCount(){
		long count = restaurantService.getRestaurantsCount();
		return ResponseEntity.ok(count);
	}
	@GetMapping("/fetchRestaurantId")
	public ResponseEntity<Integer> getRestaurantIdByName(@RequestParam String restaurantName) {
		try {
			int restaurantId =  restaurantService.getRestaurantIdByName(restaurantName);
			return ResponseEntity.ok(restaurantId);
		} catch(RestaurantNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	@ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFound(RestaurantNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
