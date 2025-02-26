package com.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pms.entity.Facility;

@Repository
public interface FacilityDao extends JpaRepository<Facility, Integer> {
	
	Facility findByCompanyNameIgnoreCase(String companyName);
	List<Facility> findByStatus(String string);


	@Query(value = "SELECT f.facility_id AS FacilityId, " +
	        "f.company_name AS CompanyName, " +
	        "f.contact_number AS ContactNumber, " +
	        "f.adress AS Address, " +
	        "f.vip_fee_per_hr AS VipFeePerHr, " +
	        "f.regular_fee_per_hr AS RegularFeePerHr, " +
	        "f.account_description AS AccountDescription, " +
	        "f.bank_account_number AS BankAccountNumber, " +
	        "f.contact_person AS ContactPerson, " +
	        "f.status AS FacilityStatus, " +
	        "s.space_id AS SpaceId, " +
	        "s.title AS SpaceTitle, " +
	        "s.space_status AS SpaceStatus, " +
	        "s.time_left AS TimeLeft, " +
	        "s.space_type AS SpaceType, " +
	        "s.last_released_time AS ReleasedTime, " +
	        "s.description AS Descriptions, " +
	        "r.reservation_id AS ReservationId, " +
	        "r.start_time AS StartTime, " +
	        "r.end_time AS EndTime, " +
	        "r.status AS ReservationStatus, " +
	        "v.vehicle_id AS VehicleId, " +
	        "v.vehicle_model AS VehicleModel, " +
	        "v.color AS Color, " +
	        "v.license_plate AS LicensePlate, " +
	        "v.fuel_type AS FuelType, " +
	        "v.year AS Year, " +
	        "v.owner_id AS VehicleOwnerId, " +
	        "u.username AS Username, " +
	        "u.user_first_name AS UserFirstName, " +
	        "u.user_middle_name AS UserMiddleName, " +
	        "u.user_last_name AS UserLastName, " +
	        "u.user_phone AS UserPhone, " +
	        "p.payment_id AS PaymentId, " +
	        "p.ammount AS PaymentAmount, " +
	        "p.ammount_befoer_vat AS PaymentAmountBeforeVAT, " +
	        "p.status AS PaymentStatus, " +
	        "p.payment_mode AS PaymentMode, " +
	        "p.currency AS PaymentCurrency, " +
	        "p.payment_date AS PaymentDate, " +
	        "p.transaction_id AS TransactionId, " +
	        "p.reference_number AS ReferenceNumber, " +
	        "p.is_refund AS IsRefund, " +
	        "p.refund_amount AS RefundAmount, " +
	        "p.payment_description AS PaymentDescription, " +
	        "p.payer_name AS PayerName, " +
	        "p.payment_status_details AS PaymentStatusDetails, " +
	        "p.company_bank_account AS CompanyBankAccount, " +
	        "p.created_at AS PaymentCreatedAt, " +
	        "p.updated_at AS PaymentUpdatedAt " +
	        "FROM facility f " +
	        "INNER JOIN parking_space s ON f.facility_id = s.facility_id " +
	        "LEFT JOIN reservation r ON s.space_id = r.space_id " +
	        "LEFT JOIN vehicle v ON r.vehicle_id = v.vehicle_id " +
	        "LEFT JOIN users u ON r.user_id = u.username " +
	        "LEFT JOIN payment p ON r.reservation_id = p.reserve_id " +
	        "WHERE f.status = 'Active' " +
	        "ORDER BY f.facility_id, s.space_id, r.reservation_id, v.vehicle_id",
	    nativeQuery = true)
	List<FacilityReservationProjection> findActiveFacilitiesWithSpacesAndReservationsNative();

	
	public interface FacilityReservationProjection {

	    // Facility details
	    Integer getFacilityId();
	    String getCompanyName();
	    String getContactNumber();
	    String getAddress();
	    String getVipFeePerHr(); 
	    String getRegularFeePerHr();
	    String getAccountDescription();
	    String getBankAccountNumber();
	    String getContactPerson();
	    String getFacilityStatus();

	    // Parking space details
	    Integer getSpaceId();
	    String getSpaceTitle();
	    String getReleasedTime();
	    String getSpaceStatus();
	    String getTimeLeft();
	    String getSpaceType();
	    String getDescriptions();

	    // Reservation details
	    Integer getReservationId();
	    String getStartTime();
	    String getEndTime();
	    String getReservationStatus();

	    // Vehicle details
	    Integer getVehicleId();
	    String getVehicleModel();
	    String getColor();
	    String getLicensePlate();
	    String getFuelType();
	    String getYear(); 
	    String getVehicleOwnerId();

	    // User details
	    String getUsername();
	    String getUserFirstName();
	    String getUserMiddleName();
	    String getUserLastName();
	    String getUserPhone();

	    // Payment details
	    Integer getPaymentId();
	    Long getPaymentAmount();
	    Long getPaymentAmountBeforeVAT();
	    String getPaymentStatus();
	    String getPaymentMode();
	    String getPaymentCurrency();
	    String getPaymentDate();
	    String getTransactionId();
	    String getReferenceNumber();
	    Boolean getIsRefund();
	    Long getRefundAmount();
	    String getPaymentDescription();
	    String getPayerName();
	    String getPaymentStatusDetails();
	    String getCompanyBankAccount();
	    String getPaymentCreatedAt();
	    String getPaymentUpdatedAt();
	}


}
