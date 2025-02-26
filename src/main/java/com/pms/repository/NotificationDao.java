package com.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pms.entity.Notification;
@Repository
public interface NotificationDao extends JpaRepository<Notification,Long>{
	@Query(value = "select * from notification where user_id=?1 order by TIMESTAMP desc", nativeQuery=true)
	List<Notification>  getByuser(String username);
	
	@Query(value = "select * from notification where user_id=?1  AND status=0 order by TIMESTAMP desc", nativeQuery=true)
	List<Notification>  getSomeByuser(String username);
	
	@Query(value="SELECT count(*) FROM notification WHERE user_id=?1 AND status=0", nativeQuery = true)
	Long getCountUnseen(String username);

	Notification getById(Long id);
	


	
}
