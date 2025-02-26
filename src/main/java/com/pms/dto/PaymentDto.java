package com.pms.dto;

import org.springframework.beans.BeanUtils;

import com.pms.entity.Payment;

public class PaymentDto {
	
    private int paymentId;
    private long ammount;
    private long ammountBefoerVat;
    private String status;
    private String paymentMode;
    private String currency;
    private String paymentDate;
    private String transactionId;
    private String referenceNumber;
    private boolean isRefund;
    private long refundAmount;
    private String paymentDescription;
    private String payerName;
    private String paymentStatusDetails;
    private String createdAt;
    private String updatedAt;
    
    private int reservationId;
    private String startTime;
    private String endTime;
    private String rserveStatus;
    private  String userId;
    private int spaceId;
    private int facilityId;
    private String companyBankAccount;
    private String bankAccountNumber;
    private String accountDescription;

  
    
	public PaymentDto() {

	}

	public PaymentDto(Payment payment) {
		BeanUtils.copyProperties(payment, this);

		if (payment.getReservation() != null) {
			this.reservationId = payment.getReservation().getReservationId();
			this.startTime = payment.getReservation().getStartTime();
			this.endTime = payment.getReservation().getEndTime();
			this.rserveStatus=payment.getReservation().getStatus();
            this.userId=payment.getReservation().getUsers().getUsername(); 
            this.spaceId=payment.getReservation().getSpaces().getSpaceId();
            this.facilityId=payment.getReservation().getSpaces().getFacilities().getFacilityId();
 
            this.bankAccountNumber=payment.getReservation().getSpaces().getFacilities().getBankAccountNumber();
            this.accountDescription=payment.getReservation().getSpaces().getFacilities().getAccountDescription();
            
			
		}
	

	}
    
	public String getRserveStatus() {
		return rserveStatus;
	}

	public void setRserveStatus(String rserveStatus) {
		this.rserveStatus = rserveStatus;
	}

	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public long getAmmount() {
		return ammount;
	}
	public void setAmmount(long ammount) {
		this.ammount = ammount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public boolean isRefund() {
		return isRefund;
	}

	public void setRefund(boolean isRefund) {
		this.isRefund = isRefund;
	}

	public long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPaymentStatusDetails() {
		return paymentStatusDetails;
	}

	public void setPaymentStatusDetails(String paymentStatusDetails) {
		this.paymentStatusDetails = paymentStatusDetails;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCompanyBankAccount() {
		return companyBankAccount;
	}

	public void setCompanyBankAccount(String companyBankAccount) {
		this.companyBankAccount = companyBankAccount;
	}

	public long getAmmountBefoerVat() {
		return ammountBefoerVat;
	}

	public void setAmmountBefoerVat(long ammountBefoerVat) {
		this.ammountBefoerVat = ammountBefoerVat;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}
    
    


}
