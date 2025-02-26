package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pms.entity.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

	Payment findByReservation_ReservationId(int reservationId);


	
	@Query("SELECT COUNT(p) FROM Payment p WHERE p.reservation.spaces.facilities.facilityId = :facilityId AND p.reservation.users.username = :username")
	int countPaymentsByUserAndFacility(@Param("username") String username, @Param("facilityId") int facilityId);



	Payment findTopByOrderByPaymentIdDesc();



	Payment findByReservation_ReservationIdAndStatus(int reservationId, String status);





//    @Query(value = "SELECT COUNT(p) FROM payment p " +
//            "JOIN reservation r ON p.reserve_id = r.reserve_id " +
//            "JOIN parking_space ps ON r.space_id = ps.space_id " +
//            "JOIN facility f ON ps.facility_id = f.facility_id " +
//            "WHERE r.user_id = :username AND f.facility_id = :facilityId", nativeQuery = true)
//  int countPaymentsByUserAndFacility(@Param("username") String username, @Param("facilityId") int facilityId);


}
