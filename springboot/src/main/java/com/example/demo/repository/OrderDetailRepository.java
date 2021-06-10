package com.example.demo.repository;

import com.example.demo.bean.WxOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<WxOrderDetail, Integer> {

    List<WxOrderDetail> findByOrderId(Integer orderId);
}
