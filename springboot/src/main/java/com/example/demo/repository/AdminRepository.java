package com.example.demo.repository;

import com.example.demo.bean.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminInfo, Integer> {

    AdminInfo findByPhoneOrUsername(String phone, String userName);

    AdminInfo findByAdminId(Integer adminId);

}
