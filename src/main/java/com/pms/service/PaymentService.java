package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.dto.PaymentDto;
import com.pms.entity.Facility;
import com.pms.entity.Notification;
import com.pms.entity.ParkingSpace;
import com.pms.entity.Payment;
import com.pms.entity.Reservation;
import com.pms.entity.Role;
import com.pms.entity.UserClass;
import com.pms.entity.UserInfo;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.ParkingSpaceDao;
import com.pms.repository.PaymentDao;
import com.pms.repository.ReservationDao;
import com.pms.repository.UserClassDao;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;
    
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private FacilityService facilityService;
    
    @Autowired
    private ParkingSpaceDao spaceDao;
    
    @Autowired
    private UserClassDao userClassDao;
    @Autowired
    private   NotificationService notificationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtAuthFilter jwtFilter;

    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 10000000));
    }
    
    private String generateReferenceNumber() {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

        Payment latestPayment = paymentDao.findTopByOrderByPaymentIdDesc();
        int nextSequence = 1;

        if (latestPayment != null) {
            String lastReference = latestPayment.getReferenceNumber();
            if (lastReference != null && lastReference.startsWith("TRN-")) {
                String[] parts = lastReference.split("-");
                if (parts.length == 4) {
                    try {
                        nextSequence = Integer.parseInt(parts[3]) + 1;
                    } catch (NumberFormatException e) {
                        nextSequence = 1; 
                    }
                }
            }
        }

        String formattedSequence = String.format("%04d", nextSequence);
        return "TRN-" + currentDate + "-PMS-" + formattedSequence;
    }
 
    public ResponseEntity<Payment> addNewPayment(PaymentDto paymentDto) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        String transactionCode = generateVerificationCode(); 
        String referenceNumber = generateReferenceNumber(); 

        Payment existingPayment = paymentDao.findByReservation_ReservationId(paymentDto.getReservationId());
        if (existingPayment != null) {
        	  return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        Payment payment = new Payment(paymentDto);
        payment.setCreatedAt(timeStamp);
        payment.setTransactionId(transactionCode);
        payment.setReferenceNumber(referenceNumber); 
        payment.setStatus("Initiated");
        paymentDao.save(payment);
        
        Notification paymentSuccessNotification = new Notification();
        paymentSuccessNotification.setContent("Your payment for Reservation ID " + paymentDto.getReservationId() 
        + " has been initiated successfully. Please complete the payment with in 5 minute  to confirm your reservation.");
        paymentSuccessNotification.setType("success");
        paymentSuccessNotification.setUserId(jwtFilter.currentUserName);
        paymentSuccessNotification.setSubject("Payment Initiated Successfully for Reservation " + paymentDto.getReservationId());
        paymentSuccessNotification.setStatus(0);
        paymentSuccessNotification.setVariable(String.valueOf(paymentDto.getPaymentId()));
        paymentSuccessNotification.setUrl("payment");
        paymentSuccessNotification.setVarname("paymentId");
        paymentSuccessNotification.setTimestamp(timeStamp);
        notificationService.saveChange(paymentSuccessNotification);
        
        return new ResponseEntity<>(payment, HttpStatus.OK);
        
        
    }

	public ResponseEntity<String> approvepayment(int paymentId,int spaceId,int reservationId,String status) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        Payment existingPayment = paymentDao.findById(paymentId).orElse(null);
        if(existingPayment!=null) {
        	
            ParkingSpace spaceData = spaceDao.findById(spaceId).orElse(null);
            if (spaceData == null) {
                return new ResponseEntity<>("Parking space not found.", HttpStatus.NOT_FOUND);
            }
            
            spaceData.setSpaceStatus("Reserved");
            spaceDao.save(spaceData);

            Reservation reserveData = reservationDao.findById(reservationId).orElse(null);
            if (reserveData == null) {
                return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
            }
            reserveData.setStatus("Processed");
            reservationDao.save(reserveData);

            existingPayment.setPaymentDate(timeStamp);
            existingPayment.setStatus("Approved");
            paymentDao.save(existingPayment);
            
            Notification paymentSuccessNotification = new Notification();
            paymentSuccessNotification.setContent("Your payment for reservation ID " + reservationId
                    + " has been approved successfully. The parking space is now reserved for you. You can access the space for the duration of your reservation.");
            paymentSuccessNotification.setType("success");
            paymentSuccessNotification.setUserId(reserveData.getUsers().getUsername());
            paymentSuccessNotification.setSubject("Payment Approved Successfully for Reservation ID " + reservationId);
            paymentSuccessNotification.setStatus(0);
            paymentSuccessNotification.setVariable(String.valueOf(paymentId));
            paymentSuccessNotification.setUrl("payment");
            paymentSuccessNotification.setVarname("paymentId");
            paymentSuccessNotification.setTimestamp(timeStamp);
            notificationService.saveChange(paymentSuccessNotification);

        
            Facility facilityData = facilityService.findFacilityByReservationId(reserveData.getReservationId());
            if (facilityData != null) {
                int paymentCount = paymentDao.countPaymentsByUserAndFacility(reserveData.getUsers().getUsername(), facilityData.getFacilityId());
                if (paymentCount >5 ) {
                    UserClass userClass = new UserClass();
                    userClass.setUserInf(reserveData.getUsers());
                    userClass.setFacilitie(facilityData);
                    userClass.setClassType("VIP");
                    userClass.setCustomerSince(timeStamp);
                    userClassDao.save(userClass);
                    Notification vipNotification = new Notification();
                    vipNotification.setContent("Congratulations! You are now a VIP customer at " + facilityData.getCompanyName() + ". Thank you for your continued support!");
                    vipNotification.setType("success");
                    vipNotification.setUserId(reserveData.getUsers().getUsername());
                    vipNotification.setSubject("VIP Status Granted");
                    vipNotification.setStatus(0);
                    vipNotification.setVariable(String.valueOf(facilityData.getFacilityId()));
                    vipNotification.setUrl("facility");
                    vipNotification.setVarname("facilityId");
                    vipNotification.setTimestamp(timeStamp);
                    notificationService.saveChange(vipNotification);
                }

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
                            agentNotification.setContent("Payment for parking space " + spaceData.getTitle()
                                    + " (Space ID: " + spaceData.getSpaceId() + ") has been approved successfully. The space is now reserved for a customer.");
                            agentNotification.setType("info");
                            agentNotification.setUserId(user.getUsername());
                            agentNotification.setSubject("Payment Approved for Parking Space " + spaceData.getTitle());
                            agentNotification.setStatus(0);
                            agentNotification.setVariable(String.valueOf(spaceData.getSpaceId()));
                            agentNotification.setUrl("slot");
                            agentNotification.setVarname("spaceId");
                            agentNotification.setTimestamp(timeStamp);
                            notificationService.saveChange(agentNotification);
                        }
                    }
                }
            }
            
            
        return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
	}

    public List<PaymentDto> getAllPayments() {
        return paymentDao.findAll().stream()
                .map(PaymentDto::new)
                .sorted(Comparator.comparing(PaymentDto::getPaymentId).reversed()) // Sort in descending order
                .collect(Collectors.toList());
    }

    public PaymentDto getPaymentById(int id) {
        return paymentDao.findById(id)
            .map(PaymentDto::new)
            .orElse(null);
    }

    public ResponseEntity<String> updatePayment(PaymentDto paymentDto, int paymentId) {
   	 String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
     Payment paymentData = paymentDao.findById(paymentId).orElse(null);
		if (paymentData != null) {
			paymentData.setUpdatedAt(timeStamp);
			paymentData.setRefundAmount(paymentDto.getAmmount());
			paymentData.setAmmountBefoerVat(paymentDto.getAmmountBefoerVat());
			paymentData.setCurrency(paymentDto.getCurrency());
			paymentData.setPaymentMode(paymentDto.getPaymentMode());
			
			paymentDao.save(paymentData);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deletePaymentById(int id) {
        if (paymentDao.existsById(id)) {
            paymentDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
