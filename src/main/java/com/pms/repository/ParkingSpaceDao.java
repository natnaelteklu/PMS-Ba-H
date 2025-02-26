package com.pms.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.dto.ParkingSpaceDto;
import com.pms.entity.ParkingSpace;

@Repository
public interface ParkingSpaceDao extends JpaRepository<ParkingSpace, Integer> {

	ParkingSpace findByTitleIgnoreCase(String title);

	List <ParkingSpace> findBySpaceStatus(String string);

	Collection<ParkingSpaceDto> findAllByOrderBySpaceIdDesc();

}
