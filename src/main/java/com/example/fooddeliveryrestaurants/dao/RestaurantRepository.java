package com.example.fooddeliveryrestaurants.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.fooddeliveryrestaurants.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
	// Custom query to search by name or cuisine, or address 
	@Query("SELECT r FROM Restaurant r WHERE " +
	           "(r.name LIKE %:searchStr% OR :name IS NULL) OR " +
	           "(r.cuisine LIKE %:searchStr% OR :cuisine IS NULL) OR " +
	           "(r.address LIKE %:searchStr% OR :address IS NULL)")
	List<Restaurant> searchRestaurants(String searchStr); 
	@Query("SELECT r.id FROM Restaurant r WHERE r.name = :restaurantName")
	Optional<Integer> findIdByName(@Param("restaurantName") String restaurantName);
}
