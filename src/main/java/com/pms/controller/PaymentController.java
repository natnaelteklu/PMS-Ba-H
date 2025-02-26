package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.dto.PaymentDto;
import com.pms.entity.Payment;
import com.pms.service.PaymentService;

@RestController
@RequestMapping("/pms")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/savePayment")
    public ResponseEntity<Payment> addNewPayment(@RequestBody PaymentDto paymentDto ) {
        return paymentService.addNewPayment(paymentDto);
    }
    
    @PutMapping("/approvepayment/{paymentId}/{spaceId}/{reservationId}")
    public ResponseEntity<String> approvepayment( 
    		@PathVariable (name="paymentId") int paymentId,
    		@PathVariable (name="spaceId") int spaceId,
    		@PathVariable (name="reservationId") int reservationId,
    		@RequestBody String status) {

        return paymentService.approvepayment(paymentId,spaceId,reservationId,status);
    }
 
    @GetMapping("/getAllPayments")
    public List<PaymentDto> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/getPaymentById/{id}")
    public PaymentDto getPaymentById(@PathVariable int id) {
        return paymentService.getPaymentById(id);
    }

    @PutMapping("/updatePayment/{paymentId}")
    public ResponseEntity<String> updatePayment(@RequestBody PaymentDto paymentDto, @PathVariable int paymentId) {
        return paymentService.updatePayment(paymentDto, paymentId);
    }

    @DeleteMapping("/deletePayment/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        return paymentService.deletePaymentById(id);
    }
}
