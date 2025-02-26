package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pms.entity.ContactUs;

public interface ContactUsDao extends JpaRepository<ContactUs, Long> {

}
