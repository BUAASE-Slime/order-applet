package com.example.demo.controller;

import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.DianCanException;
import com.example.demo.response.WxOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 卖家端订单页
 */
@Controller
@RequestMapping("/adimOrder")
@Slf4j
public class AdminOrderController {

    @Autowired
    private WxOrderUtils wxOrder;

    //订单列表
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "20") Integer size,
                       ModelMap map) {
        //最新的订单在最前面
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        Page<WxOrderResponse> orderDTOPage = wxOrder.findList(request);
        log.error("后台显示的订单列表={}", orderDTOPage.getTotalElements());
        log.error("后台显示的订单列表={}", orderDTOPage.getContent());
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return "order/list";
    }

    //只有取消的订单才可以删除
    @GetMapping("/remove")
    public String remove(@RequestParam(value = "orderId", required = false) Integer orderId,
                         ModelMap map) {
        wxOrder.remove(orderId);
        map.put("url", "/diancan/adimOrder/list");
        return "zujian/success";
    }

    //取消订单
    @GetMapping("/cancel")
    public String cancel(@RequestParam("orderId") int orderId,
                         ModelMap map) {
        try {
            WxOrderResponse orderDTO = wxOrder.findOne(orderId);
            wxOrder.cancel(orderDTO);
        } catch (DianCanException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/adimOrder/list");
            return "zujian/error";
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/diancan/adimOrder/list");
        return "zujian/success";
    }

    //订单详情
    @GetMapping("/detail")
    public String detail(@RequestParam("orderId") int orderId,
                         ModelMap map) {
        WxOrderResponse orderDTO = new WxOrderResponse();
        try {
            orderDTO = wxOrder.findOne(orderId);
        } catch (DianCanException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/adimOrder/list");
            return "zujian/error";
        }

        map.put("orderDTO", orderDTO);
        return "order/detail";
    }

    //上菜完成订单
    @GetMapping("/finish")
    public String finished(@RequestParam("orderId") int orderId,
                           ModelMap map) {
        try {
            WxOrderResponse orderDTO = wxOrder.findOne(orderId);
            wxOrder.finish(orderDTO);
        } catch (DianCanException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/adimOrder/list");
            return "zujian/error";
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/diancan/adimOrder/list");
        return "zujian/success";
    }

    //导出菜品订单到excel
    @GetMapping("/export")
    public String export(HttpServletResponse response, ModelMap map) {
        try {
            wxOrder.exportOrderToExcel(response);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "导出excel失败");
            map.put("url", "/diancan/adimOrder/list");
            return "zujian/error";
        }
        map.put("url", "/diancan/adimOrder/list");
        return "zujian/success";
    }

}
