package com.example.demo.repository;

import com.example.demo.bean.WxOrderRoot;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRootRepository extends JpaRepository<WxOrderRoot, Integer> {


    List<WxOrderRoot> findByBuyerOpenidAndOrderStatus(String buyerOpenid, Integer orderStatus, Sort updateTime);

    List<WxOrderRoot> findAll(Specification specification);
}
