package com.pms.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;


@Entity
@NamedQuery(name="ContactUs.findAll", query="SELECT c FROM ContactUs c")
public class ContactUs implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String dateSend;

	private String email;

	private String message;

	private String name;

	private String replyStatus;

	private String subject;
	
	private String replays;
	
	private String replayedBy;
	
	private String datereplay;
	
	
	public ContactUs() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDateSend() {
		return this.dateSend;
	}

	public void setDateSend(String dateSend) {
		this.dateSend = dateSend;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReplyStatus() {
		return this.replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReplays() {
		return replays;
	}

	public void setReplays(String replays) {
		this.replays = replays;
	}

	public String getReplayedBy() {
		return replayedBy;
	}

	public void setReplayedBy(String replayedBy) {
		this.replayedBy = replayedBy;
	}

	public String getDatereplay() {
		return datereplay;
	}

	public void setDatereplay(String datereplay) {
		this.datereplay = datereplay;
	}

}