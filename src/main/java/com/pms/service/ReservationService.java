package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.dto.ReservationDto;
import com.pms.entity.Facility;
import com.pms.entity.Notification;
import com.pms.entity.ParkingSpace;
import com.pms.entity.Reservation;
import com.pms.entity.Role;
import com.pms.entity.UserInfo;
import com.pms.entity.Vehicle;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.ParkingSpaceDao;
import com.pms.repository.ReservationDao;
import com.pms.repository.VehicleDao;



@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ParkingSpaceDao parkingSpaceDao;

    @Autowired
    private VehicleDao vehicleDao;
    
	@Autowired
	private JwtAuthFilter jwtFilter;
	
    @Autowired
    private   NotificationService notificationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CustomizationService customizationService;

    public ResponseEntity<String> addNewReservation(ReservationDto reservationDto) {
        long timeToCancel = customizationService.getTimeTocancel();

  
        boolean vehicleExists = reservationDao.existsByVehicles_VehicleIdAndStatusIn(
            reservationDto.getVehicleId(), Arrays.asList("Processing")
        );

        if (vehicleExists) {
            return new ResponseEntity<>("vehicleExists", HttpStatus.BAD_REQUEST);
        }

        // Check if a reservation already exists for the given userId with status "Processing"
        boolean userExists = reservationDao.existsByUsers_UsernameAndStatusIn(
        		jwtFilter.currentUserName, Arrays.asList("Processing")
        );

        if (userExists) {
            return new ResponseEntity<>("userExists", HttpStatus.BAD_REQUEST);
        }

        // Find parking space
        ParkingSpace parkingSpace = parkingSpaceDao.findById(reservationDto.getSpaceId()).orElse(null);
        if (parkingSpace == null) {
            return new ResponseEntity<>("Parking space not found", HttpStatus.NOT_FOUND);
        }

        // Update space status
        parkingSpace.setSpaceStatus("Pending");

        // Create and save reservation
        reservationDto.setStatus("Processing");
        reservationDto.setUsername(jwtFilter.currentUserName);
        Reservation reservation = new Reservation(reservationDto);
        reservation.setSpaces(parkingSpace);

        parkingSpaceDao.save(parkingSpace);
        reservationDao.save(reservation);

        // Send payment reminder notification
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date());

        Notification paymentReminderNotification = new Notification();
        paymentReminderNotification.setContent("Your reservation with reserve ID " + reservation.getReservationId()
                + " is Processing. Please initiate your payment within the next " + timeToCancel + " minutes, or the reservation will be canceled.");
        paymentReminderNotification.setType("info");
        paymentReminderNotification.setUserId(reservation.getUsers().getUsername());
        paymentReminderNotification.setSubject("Urgent: Payment Reminder for Reservation with reserve ID " + reservation.getReservationId());
        paymentReminderNotification.setStatus(0);
        paymentReminderNotification.setVariable(String.valueOf(reservation.getReservationId()));
        paymentReminderNotification.setUrl("reservation");
        paymentReminderNotification.setVarname("reservationId");
        paymentReminderNotification.setTimestamp(timeStamp);
        notificationService.saveChange(paymentReminderNotification);

        return new ResponseEntity<>(HttpStatus.OK);
    }




    public List<ReservationDto> getAllReservations() {
        return reservationDao.findAll().stream()
                .map(ReservationDto::new)
                .sorted(Comparator.comparing(ReservationDto::getReservationId).reversed()) // Sort in descending order
                .collect(Collectors.toList());
    }

    public ReservationDto getReservationById(int id) {
        return reservationDao.findById(id)
            .map(ReservationDto::new)
            .orElse(null);
    }

    
    public List<ReservationDto> getAllCustomers(int facilityId) {
        return reservationDao.findByFacilityIdandStatus(facilityId, "Processed").stream()
                .map(ReservationDto::new)
                .sorted(Comparator.comparing(ReservationDto::getReservationId).reversed()) 
                .collect(Collectors.toList());
    }
    
    public ResponseEntity<String> updateReservation(ReservationDto reservationDto, int reservationId) {
    	 long timeToCancel = customizationService.getTimeTocancel();
        Reservation reservationData = reservationDao.findById(reservationId).orElse(null);
        if (reservationData != null) {
            ParkingSpace oldSpace = reservationData.getSpaces();
            if (oldSpace != null) {
                oldSpace.setSpaceStatus("Available");
                parkingSpaceDao.save(oldSpace);
            }

            ParkingSpace newSpace = parkingSpaceDao.findById(reservationDto.getSpaceId()).orElse(null);
            if (newSpace != null) {
                newSpace.setSpaceStatus("Pending");
                parkingSpaceDao.save(newSpace);
                reservationData.setSpaces(newSpace);
            }
            Vehicle vehicleData = vehicleDao.findById(reservationDto.getVehicleId()).orElse(null);
            if (vehicleData != null) {
                reservationData.setVehicles(vehicleData);
            }

            reservationData.setStartTime(reservationDto.getStartTime());
            reservationData.setEndTime(reservationDto.getEndTime());
            reservationDao.save(reservationData);
            
    	    String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date());

    	    Notification paymentReminderNotification = new Notification();
    	    paymentReminderNotification.setContent("Your reservation with with reserve ID " + reservationId
    	            + " is updated successfully. Please initiate your payment within the next "+timeToCancel+" minutes, or the reservation will be canceled.");
    	    paymentReminderNotification.setType("info");
    	    paymentReminderNotification.setUserId(reservationData.getUsers().getUsername());
    	    paymentReminderNotification.setSubject("Urgent: Payment Reminder for Reservation with reserve id " + reservationData.getReservationId());
    	    paymentReminderNotification.setStatus(0);
    	    paymentReminderNotification.setVariable(String.valueOf( reservationData.getReservationId()));
    	    paymentReminderNotification.setUrl("reservation"); 
    	    paymentReminderNotification.setVarname("reservationId");
    	    paymentReminderNotification.setTimestamp(timeStamp);
    	    notificationService.saveChange(paymentReminderNotification);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    
	public ResponseEntity<String> extendReservationTime(ReservationDto reservationDto, int reserveId) {
        Reservation reservationData = reservationDao.findById(reserveId).orElse(null);
        if (reservationData != null) {
            reservationData.setStartTime(reservationDto.getStartTime());
            reservationData.setEndTime(reservationDto.getEndTime());
            reservationDao.save(reservationData);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> deleteReservationById(int id) {
        if (reservationDao.existsById(id)) {
            Reservation reservation = reservationDao.findById(id).orElse(null);
            
            if (reservation != null && reservation.getSpaces() != null) {
                ParkingSpace parkingSpace = reservation.getSpaces();
                parkingSpace.setSpaceStatus("Available");
                parkingSpaceDao.save(parkingSpace);
                
      
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
                Facility facilityData = parkingSpace.getFacilities();
                
               
                List<UserInfo> users = userService.getUserByOrogin(facilityData.getFacilityId());
                if (users != null) {
                    for (UserInfo user : users) {
                        Role parkingAgentRole = user.getRoles()
                                .stream()
                                .filter(role -> "FACILITYAGENT".equalsIgnoreCase(role.getRolename()))
                                .findFirst()
                                .orElse(null);

                        if (parkingAgentRole != null) { 
                            Notification agentNotification = new Notification();
                            agentNotification.setContent("A reservation for parking space " + parkingSpace.getTitle()
                                    + " (Space ID: " + parkingSpace.getSpaceId() + ") has been canceled by customer. The space is now available.");
                            agentNotification.setType("info");
                            agentNotification.setUserId(user.getUsername());
                            agentNotification.setSubject("Reservation Canceled for Parking Space " + parkingSpace.getTitle());
                            agentNotification.setStatus(0);
                            agentNotification.setVariable(String.valueOf(parkingSpace.getSpaceId()));
                            agentNotification.setUrl("slot");
                            agentNotification.setVarname("spaceId");
                            agentNotification.setTimestamp(timeStamp);
                            notificationService.saveChange(agentNotification);
                        }
                    }
                }
            }
            
            reservationDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
    }




}
