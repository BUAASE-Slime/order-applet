package com.example.demo.repository;

import com.example.demo.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}
