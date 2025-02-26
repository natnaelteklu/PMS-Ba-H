package com.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pms.entity.Reservation;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {

	 List<Reservation> findBySpaces_SpaceId(int spaceId);
	 


	  @Query( value = "SELECT * FROM reservation r WHERE r.end_time <= :currentTime",nativeQuery = true )
	  List<Reservation> findExpiredReservations(@Param("currentTime") String currentTime);


	 @Query(value = "SELECT DISTINCT r.* " +
             "FROM reservation r " +
             "JOIN parking_space p ON r.space_id = p.space_id " +
             "JOIN facility f ON p.facility_id = f.facility_id " +
             "WHERE f.facility_id = :facilityId AND r.status = :status", 
             nativeQuery = true)
     List<Reservation> findByFacilityIdandStatus(@Param("facilityId") int facilityId, @Param("status") String status);



	boolean existsByVehicles_VehicleIdAndStatusIn(int vehicleId, List<String> asList);



	boolean existsByUsers_UsernameAndStatusIn(String username, List<String> asList);





	}