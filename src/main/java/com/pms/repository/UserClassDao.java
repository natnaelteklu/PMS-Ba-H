package com.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pms.dto.UserClassDto;
import com.pms.entity.UserClass;

public interface UserClassDao extends JpaRepository<UserClass, Integer> {

	List<UserClass> findByFacilitie_facilityId(int facilityId);

	UserClass findByuserInf_username(String username);

}
