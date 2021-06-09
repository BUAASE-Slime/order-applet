package com.example.demo.controller;

import com.example.demo.api.ResultVO;
import com.example.demo.bean.WxOrderDetail;
import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRootRepository;
import com.example.demo.request.OrderReq;
import com.example.demo.response.WxOrderResponse;
import com.example.demo.utils.ApiUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userOrder")
@Slf4j
public class WxOrderController {


    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRootRepository orderRootRepository;

    @Autowired
    private WxOrderUtils wxOrder;

    //创建订单
    @PostMapping("/create")
    @Transactional//数据库事务
    public ResultVO<Map<String, String>> create(@Valid OrderReq orderReq,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, orderReq={}", orderReq);
            throw new DianCanException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //数据转换
        WxOrderResponse orderBean = new WxOrderResponse();
        orderBean.setBuyerName(orderReq.getName());
        orderBean.setBuyerPhone(orderReq.getPhone());
        orderBean.setBuyerAddress(orderReq.getAddress());
        orderBean.setBuyerOpenid(orderReq.getOpenid());
        List<WxOrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = new Gson().fromJson(orderReq.getItems(),
                    new TypeToken<List<WxOrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderReq.getItems());
            throw new DianCanException(ResultEnum.PARAM_ERROR);
        }
        orderBean.setOrderDetailList(orderDetailList);

        if (CollectionUtils.isEmpty(orderBean.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new DianCanException(ResultEnum.CART_EMPTY);
        }
        WxOrderResponse createResult = wxOrder.createOrder(orderBean);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", "" + createResult.getOrderId());
        return ApiUtil.success(map);
    }

    @GetMapping("/cuidan")
    public void cuidan(@RequestParam("zhuoHao") String zhuoHao,
                       @RequestParam("orderId") Integer orderId) {
        wxOrder.cuiDan(zhuoHao, orderId);
    }

    //订单列表
    @GetMapping("/listByStatus")
    public ResultVO<List<WxOrderResponse>> listByStatus(@RequestParam("openid") String openid,
                                                        @RequestParam(value = "orderStatus", defaultValue = "0") Integer orderStatus) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new DianCanException(ResultEnum.PARAM_ERROR);
        }

        List<WxOrderResponse> list = new ArrayList<>();
        list.clear();


        List<WxOrderResponse> listStats = wxOrder.findListStats(openid, orderStatus);
        listStats.forEach((orderBean) -> {
            WxOrderResponse one = wxOrder.findOne(orderBean.getOrderId());
            list.add(one);
        });
        return ApiUtil.success(list);
    }


    //订单详情
    @GetMapping("/detail")
    public ResultVO<WxOrderResponse> detail(@RequestParam("openid") String openid,
                                            @RequestParam("orderId") int orderId) {
        WxOrderResponse orderDTO = wxOrder.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new DianCanException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return ApiUtil.success(orderDTO);
    }


    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") int orderId) {
        WxOrderResponse orderDTO = wxOrder.findOne(orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new DianCanException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new DianCanException(ResultEnum.ORDER_OWNER_ERROR);
        }
        wxOrder.cancel(orderDTO);
        return ApiUtil.success();
    }

}
