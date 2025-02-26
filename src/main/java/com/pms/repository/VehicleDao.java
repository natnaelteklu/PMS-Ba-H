package com.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Vehicle;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Integer> {



	Vehicle findByVehiclTitle(String vehiclTitle);

	List<Vehicle> findByUser_username(String userId);

}
