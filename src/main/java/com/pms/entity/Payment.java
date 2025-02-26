package com.pms.entity;

import org.springframework.beans.BeanUtils;

import com.pms.dto.PaymentDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;


@Entity
@NamedQuery(name="Payment.findAll", query="SELECT t FROM Payment t")
public class Payment {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
    private String companyBankAccount;
    private String createdAt;
    private String updatedAt;
    
    
	@ManyToOne
	@JoinColumn(name="reserve_id")
	private Reservation reservation;

	
	public Payment() {
	}

	public Payment(PaymentDto paymentDto) {
		BeanUtils.copyProperties(paymentDto, this);
		Reservation reserve = new Reservation();
		reserve.setReservationId(paymentDto.getReservationId());
		this.setReservation(reserve);

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

	public void setAmmount(long amount) {
		this.ammount = amount;
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

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
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
	
	
   
	
}