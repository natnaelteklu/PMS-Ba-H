package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pms.config.JwtConfig;
import com.pms.dto.ParkingSpaceDto;
import com.pms.entity.Facility;
import com.pms.entity.Notification;
import com.pms.entity.ParkingSpace;
import com.pms.entity.Payment;
import com.pms.entity.Reservation;
import com.pms.entity.UserInfo;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.ParkingSpaceDao;
import com.pms.repository.PaymentDao;
import com.pms.repository.ReservationDao;

@Service
public class ParkingSpaceService {

    @Autowired
    private ParkingSpaceDao parkingSpaceDao;
    
    @Autowired
    private PaymentDao paymentDao;
    
    @Autowired
    private ReservationDao reservationDao;
    
    @Autowired
    private JwtAuthFilter jwtFilter;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FacilityService facilityService;
    
    @Autowired
    private  CustomizationService customizationService;
  

    @Autowired
    private   NotificationService notificationService;
    
    
    private final JwtConfig jwtConfig;

    @Autowired
    public ParkingSpaceService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public ResponseEntity<String> addNewParkingSpace(ParkingSpaceDto parkingSpaceDto) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        ParkingSpace existingSpace = parkingSpaceDao.findByTitleIgnoreCase(parkingSpaceDto.getTitle());
        if (existingSpace != null) {
            return new ResponseEntity<>("A parking space with this title already exists.", HttpStatus.BAD_REQUEST);
        }
        ParkingSpace parkingSpace = new ParkingSpace(parkingSpaceDto);
        parkingSpace.setCreatedBy(jwtFilter.currentUserName);
        
        parkingSpace.setCreatedAt(timeStamp);
        parkingSpace.setSpaceStatus("Available");

        parkingSpaceDao.save(parkingSpace);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    
    public List<ParkingSpaceDto> getAllParkingSpaces() {
        return parkingSpaceDao.findAll().stream()
                .map(ParkingSpaceDto::new)
                .sorted(Comparator.comparing(ParkingSpaceDto::getSpaceId).reversed()) // Sort in descending order
                .collect(Collectors.toList());
    }
    

    public List<ParkingSpaceDto> getAvailableSlots() {
        // Assuming the method findByStatus returns a list of ParkingSpace entities
        return parkingSpaceDao.findBySpaceStatus("Available").stream()
            .map(ParkingSpaceDto::new)  // Convert each ParkingSpace entity to ParkingSpaceDto
            .collect(Collectors.toList());
    }

    public ParkingSpaceDto getParkingSpaceById(int id) {
        return parkingSpaceDao.findById(id).map(ParkingSpaceDto::new).orElse(null);
    }

    public ResponseEntity<String> updateParkingSpaceBySpaceId(ParkingSpaceDto parkingSpaceDto, int spaceId) {
        ParkingSpace existingSpace = parkingSpaceDao.findById(spaceId).orElse(null);
       
        if (existingSpace != null) {
        	parkingSpaceDto.setSpaceId(existingSpace.getSpaceId());
        	parkingSpaceDto.setSpaceStatus(existingSpace.getSpaceStatus());
        	parkingSpaceDto.setCreatedAt(existingSpace.getCreatedAt());
        	parkingSpaceDto.setCreatedBy(existingSpace.getCreatedBy());
        	parkingSpaceDto.setLastReleasedTime(existingSpace.getLastReleasedTime());
        	parkingSpaceDto.setTimeLeft(existingSpace.getTimeLeft());
        	existingSpace = new ParkingSpace(parkingSpaceDto);
        	parkingSpaceDao.save(existingSpace);
			return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteParkingSpaceById(int id) {
        if (parkingSpaceDao.existsById(id)) {
            parkingSpaceDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Scheduled(fixedRateString = "#{@jwtConfig.jobscheduleTime}")
    public void updateExpiredParkingSpaces() {
        String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date());

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            Date currentTime = formatter.parse(currentTimeStamp);

            // Fetch expired reservations
            List<Reservation> expiredReservations = reservationDao.findExpiredReservations(currentTimeStamp);

            for (Reservation reservation : expiredReservations) {
                if (reservation.getEndTime() != null) {
                    Date reservationEndTime = formatter.parse(reservation.getEndTime());

                    if (currentTime.after(reservationEndTime)) {
                        int spaceId = reservation.getSpaces().getSpaceId();

                        // Fetch parking space by spaceId
                        ParkingSpace parkingSpace = parkingSpaceDao.findById(spaceId).orElse(null);

                        if (parkingSpace != null && "Reserved".equals(parkingSpace.getSpaceStatus())) {
                            // Update parking space status to "Available"
                            parkingSpace.setSpaceStatus("Available");
                            parkingSpace.setLastReleasedTime(reservation.getEndTime());
                            parkingSpaceDao.save(parkingSpace);
                            
                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date());

                            // Notify the reservist
                            Notification customerNotification = new Notification();
                            customerNotification.setContent("We wanted to inform you that the parking space you reserved with reservation id " + reservation.getReservationId() + " has now been updated to Available following the end of your reservation period.\n\n"
                                    + "If you need to reserve a parking space again or require further assistance, please feel free to contact us or visit our booking platform.\n\n"
                                    + "Thank you for using our services!");
                            customerNotification.setType("success");
                            customerNotification.setUserId(reservation.getUsers().getUsername());
                            customerNotification.setSubject("Dear " + reservation.getUsers().getUserFirstName());
                            customerNotification.setStatus(0);
                            customerNotification.setVariable(String.valueOf(parkingSpace.getSpaceId()));
                            customerNotification.setUrl("spaces");
                            customerNotification.setVarname("spaceId");
                            customerNotification.setTimestamp(timeStamp);
                            notificationService.saveChange(customerNotification);

                            // Notify the parking agent
                            Facility facilityData = facilityService.findFacilityByReservationId(reservation.getReservationId());
                            if (facilityData != null) {
                                // Get users by origin (facility ID)
                                List<UserInfo> users = userService.getUserByOrogin(facilityData.getFacilityId());
                                if (users != null) {
                                    // Find the user with the "FACILITYAGENT" role
                                    UserInfo parkingAgent = users.stream()
                                        .filter(user -> user.getRoles().stream()
                                            .anyMatch(role -> "FACILITYAGENT".equalsIgnoreCase(role.getRolename())))
                                        .findFirst().orElse(null);

                                    if (parkingAgent != null) {
                                        Notification agentNotification = new Notification();
                                        agentNotification.setContent("A reservation for parking space " + parkingSpace.getTitle()
                                                + " in your facility (" + facilityData.getCompanyName() + ") has ended. The space is now available for use.");
                                        agentNotification.setType("info");
                                        agentNotification.setUserId(parkingAgent.getUsername());
                                        agentNotification.setSubject("Dear " + parkingAgent.getUserFirstName());
                                        agentNotification.setStatus(0);
                                        agentNotification.setVariable(String.valueOf(parkingSpace.getSpaceId()));
                                        agentNotification.setUrl("spaces");
                                        agentNotification.setVarname("spaceId");
                                        agentNotification.setTimestamp(timeStamp);
                                        notificationService.saveChange(agentNotification);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    /**
     * Scheduled Task: Handle pending and reserved parking spaces.
     * Runs every minute.
     */
    @Scheduled(fixedRateString = "#{@jwtConfig.jobscheduleTime}")
    public void handlePendingAndReservedSpaces() {
        String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date());

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            Date currentTime = formatter.parse(currentTimeStamp);

          
            List<Reservation> reservations = reservationDao.findAll();

            for (Reservation reservation : reservations) {
                if (reservation.getEndTime() != null) {
                    int spaceId = reservation.getSpaces().getSpaceId();
                    ParkingSpace parkingSpace = parkingSpaceDao.findById(spaceId).orElse(null);

                    if (parkingSpace == null) continue;
                    
                    Payment paymentData = paymentDao.findByReservation_ReservationIdAndStatus(reservation.getReservationId(),"Initiated");
                   

                    String spaceStatus = parkingSpace.getSpaceStatus();
                    String reservationStatus = reservation.getStatus();
                    Date reservationEndTime = formatter.parse(reservation.getEndTime());

         
                    if ("Pending".equals(spaceStatus) && "Processing".equals(reservationStatus)) {
                        Date reservationStartTime = formatter.parse(reservation.getStartTime());
                        long diffInMillis = currentTime.getTime() - reservationStartTime.getTime();
                        long timeToCancel = customizationService.getTimeTocancel();
                    
                        if (diffInMillis >= timeToCancel*60000) { 
                            parkingSpace.setSpaceStatus("Available");
                            reservation.setStatus("Canceled");
                            parkingSpace.setTimeLeft("00:00:00");
                            parkingSpaceDao.save(parkingSpace);
                            reservationDao.save(reservation);
                            if(paymentData!=null) {
                            	paymentData.setStatus("Failed");
                            	paymentDao.save(paymentData);
                            }
                            
                            Notification cancelNotification = new Notification();
                            cancelNotification.setContent("Your reservation for the space " +parkingSpace.getTitle() +" with reservation ID " + reservation.getReservationId() 
                                + " has been canceled,  because payment was not received within "+timeToCancel+" minutes.");
                            cancelNotification.setType("danger");
                            cancelNotification.setUserId(reservation.getUsers().getUsername());
                            cancelNotification.setSubject("Reservation Canceled");
                            cancelNotification.setStatus(0);
                            cancelNotification.setVariable(String.valueOf(spaceId));
                            cancelNotification.setUrl("spaces");
                            cancelNotification.setVarname("spaceId");
                            cancelNotification.setTimestamp(currentTimeStamp);
                            notificationService.saveChange(cancelNotification);

                        } else {
                            String timeLeft = calculateTimeLeft(currentTime, reservationStartTime, 120000);
                            parkingSpace.setTimeLeft(timeLeft);
                            parkingSpaceDao.save(parkingSpace);

                        
                            Notification timeLeftNotification = new Notification();
                            timeLeftNotification.setContent("You have left " + timeLeft + "minute  to complete your reservation payment.");
                            timeLeftNotification.setType("info");
                            timeLeftNotification.setUserId(reservation.getUsers().getUsername());
                            timeLeftNotification.setSubject("Payment Time Remaining");
                            timeLeftNotification.setStatus(0);
                            timeLeftNotification.setVariable(String.valueOf(reservation.getReservationId()));
                            timeLeftNotification.setUrl("reservation");
                            timeLeftNotification.setVarname("reservationId");
                            timeLeftNotification.setTimestamp(currentTimeStamp);
                            notificationService.saveChange(timeLeftNotification);
                        }
                    }

                   
                    if ("Reserved".equals(spaceStatus)) {
                        if (currentTime.before(reservationEndTime)) {
                            String timeLeft = calculateTimeLeft(currentTime, reservationEndTime, 0);
                            parkingSpace.setTimeLeft(timeLeft);
                            parkingSpaceDao.save(parkingSpace);

                          
                            long timeDifference = reservationEndTime.getTime() - currentTime.getTime();
                            long timeToNotify = customizationService.getTimeToNotify();
                   
                            long timeInMils = timeToNotify * 60000;
                            
                            
                            long lowerBound = timeInMils - 60000; 
                            long upperBound = timeInMils; 
                            
                            if (timeDifference > lowerBound && timeDifference <= upperBound) { 
                           
                                Notification finalWarningNotification = new Notification();
                                finalWarningNotification.setContent("You have about "+ timeToNotify +" minutes remaining for your reservation with reserve ID " 
                                	    + reservation.getReservationId() + ". Please ensure to take your vehicle and complete your activities on time. Thank you for using our services!");
                                finalWarningNotification.setType("info");
                                finalWarningNotification.setUserId(reservation.getUsers().getUsername());
                                finalWarningNotification.setSubject("Reservation Ending Soon");
                                finalWarningNotification.setStatus(0);
                                finalWarningNotification.setVariable(String.valueOf(reservation.getReservationId()));
                                finalWarningNotification.setUrl("reservation");
                                finalWarningNotification.setVarname("reservationId");
                                finalWarningNotification.setTimestamp(currentTimeStamp);
                                notificationService.saveChange(finalWarningNotification);

                              
                                Facility facilityData = facilityService.findFacilityByReservationId(reservation.getReservationId());
                                List<UserInfo> responsiveUser = null;
                                if (facilityData != null) {
                                    responsiveUser = userService.getUserByOrogin(facilityData.getFacilityId());
                                }

                           
                                UserInfo parkingAgent = responsiveUser.stream()
                                    .filter(user -> user.getRoles().stream()
                                        .anyMatch(role -> "FACILITYAGENT".equals(role.getRolename())))
                                    .findFirst().orElse(null);

                                if (parkingAgent != null) {
                       
                        
                                    Notification agentNotification = new Notification();
                                    agentNotification.setContent("The reservation with ID " + reservation.getReservationId() 
                                        + " for parking space " + spaceId + " will expire in " + timeToNotify + " minutes. Please prepare.");

                                    
                                    
                                    agentNotification.setType("info");
                                    agentNotification.setUserId(parkingAgent.getUsername());
                                    agentNotification.setSubject("Parking Space Ending Soon");
                                    agentNotification.setStatus(0);
                                    agentNotification.setVariable(String.valueOf(reservation.getReservationId()));
                                    agentNotification.setUrl("reservation");
                                    agentNotification.setVarname("reservationId");
                                    agentNotification.setTimestamp(currentTimeStamp);
                                    notificationService.saveChange(agentNotification);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility method to calculate time left in "HH:mm:ss" format.
     * Adds a timeout duration (in milliseconds) for cases like "Pending".
     */
    private String calculateTimeLeft(Date currentTime, Date targetTime, long timeoutDurationInMillis) {
        long diffInMillis = targetTime.getTime() + timeoutDurationInMillis - currentTime.getTime();

        if (diffInMillis <= 0) {
            return "00:00:00";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    
}
