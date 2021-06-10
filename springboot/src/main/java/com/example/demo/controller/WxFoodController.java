package com.example.demo.controller;

import com.example.demo.api.FoodRes;
import com.example.demo.api.ResultVO;
import com.example.demo.api.TypeVO;
import com.example.demo.bean.Food;
import com.example.demo.bean.Type;
import com.example.demo.enums.FoodStatusEnum;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.TypeRepository;
import com.example.demo.utils.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class WxFoodController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TypeRepository leiMuRepository;

    /*
     * 返回菜单和菜品列表
     * */
    @GetMapping("/buyerfoodList")
    public ResultVO list(@RequestParam("searchKey") String searchKey) {
        log.info("搜索词={}", searchKey);
        List<Food> foodList = new ArrayList<>();
        if (StringUtils.pathEquals("all", searchKey)) {
            //返回所有菜品
            foodList = foodRepository.findByFoodStatus(FoodStatusEnum.UP.getCode());
        } else {
            //查询菜品
            foodList = foodRepository.findByFoodStatusAndFoodNameContaining(FoodStatusEnum.UP.getCode(), searchKey);
            log.info("搜索结果={}", foodList);
        }

        return zuZhuang(foodList);
    }

    public ResultVO zuZhuang(List<Food> foodList) {
        List<Integer> categoryTypeList = foodList.stream()
                .map(e -> e.getLeimuType())
                .collect(Collectors.toList());
        List<Type> leimuList = leiMuRepository.findByLeimuTypeIn(categoryTypeList);

        //3. 数据拼装
        List<TypeVO> leimuVOList = new ArrayList<>();
        for (Type leimu : leimuList) {
            TypeVO leimuVO = new TypeVO();
            leimuVO.setTypeIdx(leimu.getLeimuType());
            leimuVO.setTypeName(leimu.getLeimuName());

            List<FoodRes> foodResList = new ArrayList<>();
            for (Food food : foodList) {
                if (food.getLeimuType().equals(leimu.getLeimuType())) {
                    FoodRes foodRes = new FoodRes();
                    BeanUtils.copyProperties(food, foodRes);
                    foodResList.add(foodRes);
                }
            }
            leimuVO.setFoodResList(foodResList);
            leimuVOList.add(leimuVO);
        }

        return ApiUtil.success(leimuVOList);
    }
}
