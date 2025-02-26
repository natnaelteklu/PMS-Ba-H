package com.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pms.entity.Role;
import com.pms.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

	UserInfo findByUsernameAndUserStatus(String username, int status);


	UserInfo findByusernameIgnoreCase(String username);

	Role findByroles_id(int roleId);

	UserInfo findByUsername(String username);

	UserInfo findByverifcationCode(String code);

	UserInfo findByUsernameAndVerifcationCode(String code, String userName);


	List<UserInfo> findByfacilitty_FacilityId(int origin);


	boolean existsByUserPhone(String lowerCase);




}
