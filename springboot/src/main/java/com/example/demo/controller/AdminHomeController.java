package com.example.demo.controller;

import com.example.demo.bean.Food;
import com.example.demo.bean.TotalMoney;
import com.example.demo.bean.WxOrderRoot;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.OrderRootRepository;
import com.example.demo.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 点餐后台首页
 * */
@Controller
@RequestMapping("/home")
@Slf4j
public class AdminHomeController {

    @Autowired
    OrderRootRepository orderRootRepository;
    @Autowired
    FoodRepository foodRepository;

    /*
     * 页面相关
     * 1,查询当月收入
     * 2，库存预警
     * 3，账单查询
     * */
    @GetMapping("/homeList")
    public String homeList(ModelMap map) {
        int year = TimeUtils.getCurrentYear();
        int month = TimeUtils.getCurrentMonth();
        List<TotalMoney> totalMoneyList = new ArrayList<>();
        for (int i = 1; i <= month; i++) {
            TotalMoney totalMoney = new TotalMoney();
            totalMoney.setTime(year + "年" + i + "月");
            totalMoney.setTotalMoney(getMonthMoney(year, i));
            totalMoneyList.add(totalMoney);
        }
        map.put("totalMoneyList", totalMoneyList);
        map.put("yearMoney", getYearMoney(year));
        map.put("foodList", getFoodKuCunList());
        return "home/list";
    }

    //获取今年年收入
    private BigDecimal getYearMoney(int year) {
        Specification<WxOrderRoot> spec1 = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            try {
                Integer[] statusArr = {
                        OrderStatusEnum.NEW_PAYED.getCode(),
                        OrderStatusEnum.FINISHED.getCode(),
                        OrderStatusEnum.COMMENT.getCode()
                };
                list.add(root.get("orderStatus").in(statusArr));
                //大于或等于传入时间
                list.add(cb.greaterThanOrEqualTo(root.get("updateTime").as(Date.class), TimeUtils.getFisrtDayOfMonth(year, 1)));
                //小于或等于传入时间
                list.add(cb.lessThanOrEqualTo(root.get("updateTime").as(Date.class), TimeUtils.getLastDayOfMonth(year, 12)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
        List<WxOrderRoot> orderList = orderRootRepository.findAll(spec1);
        BigDecimal totalMoney = new BigDecimal(0);
        for (WxOrderRoot root : orderList) {
            totalMoney = totalMoney.add(root.getOrderAmount());
        }
        return totalMoney;
    }

    //获取每个月份的收入
    private BigDecimal getMonthMoney(int year, int month) {
        //查询当月订单
        Specification<WxOrderRoot> spec1 = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            try {
                Integer[] statusArr = {
                        OrderStatusEnum.NEW_PAYED.getCode(),
                        OrderStatusEnum.FINISHED.getCode(),
                        OrderStatusEnum.COMMENT.getCode()
                };
                list.add(root.get("orderStatus").in(statusArr));
                //大于或等于传入时间
                list.add(cb.greaterThanOrEqualTo(root.get("updateTime").as(Date.class), TimeUtils.getFisrtDayOfMonth(year, month)));
                //小于或等于传入时间
                list.add(cb.lessThanOrEqualTo(root.get("updateTime").as(Date.class), TimeUtils.getLastDayOfMonth(year, month)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
        List<WxOrderRoot> orderList = orderRootRepository.findAll(spec1);
        BigDecimal totalMoney = new BigDecimal(0);
        for (WxOrderRoot root : orderList) {
            totalMoney = totalMoney.add(root.getOrderAmount());
        }
//        log.error("查询到指定月份的订单列表={}", orderList);
        return totalMoney;
    }

    //查询库存少于5的菜品，提醒管理员及时补充库存
    private List<Food> getFoodKuCunList() {
        return foodRepository.findByFoodStockLessThan(5);
    }
}
