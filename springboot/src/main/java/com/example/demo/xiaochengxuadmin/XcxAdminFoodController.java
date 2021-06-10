package com.example.demo.xiaochengxuadmin;

import com.example.demo.bean.Food;
import com.example.demo.enums.FoodStatusEnum;
import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.TypeRepository;
import com.example.demo.request.FoodReq;
import com.example.demo.utils.ExcelImportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/xcxfood")
@Slf4j
public class XcxAdminFoodController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TypeRepository leiMuRepository;

    @GetMapping("/list")
    @ResponseBody
    public List<Food> list() {
        List<Food> foodList = foodRepository.findAll();
        return foodList;
    }

    @GetMapping("/remove")
    @ResponseBody
    public void remove(@RequestBody Integer foodId) {
        foodRepository.deleteById(foodId);
    }

    @GetMapping("/on_sale")
    @ResponseBody
    public void onSale(@RequestBody  Integer foodId) {
        try {
            Food food = foodRepository.findById(foodId).orElse(null);
            if (food == null) {
                throw new DianCanException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (food.getFoodStatusEnum() == FoodStatusEnum.UP) {
                throw new DianCanException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
            food.setFoodStatus(FoodStatusEnum.UP.getCode());
            foodRepository.save(food);
        } catch (DianCanException e) {
            System.out.println("该物品已经上架");
        }
    }

    @GetMapping("/off_sale")
    @ResponseBody
    public void offSale(@RequestBody Integer foodId) {
        try {
            Food food = foodRepository.findById(foodId).orElse(null);
            if (food == null) {
                throw new DianCanException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (food.getFoodStatusEnum() == FoodStatusEnum.DOWN) {
                throw new DianCanException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
            food.setFoodStatus(FoodStatusEnum.DOWN.getCode());
            foodRepository.save(food);
        } catch (DianCanException e) {
            System.out.println("该物品未上架");
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@Valid @RequestBody FoodReq form, BindingResult bindingResult) {
        System.out.println(form);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "菜品信息不完整";
        }
        Food productInfo = new Food();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getFoodId())) {
                productInfo = foodRepository.findById(form.getFoodId()).orElse(null);
            }
            BeanUtils.copyProperties(form, productInfo);
            foodRepository.save(productInfo);
        } catch (Exception e) {
            log.error("添加菜品错误={}", e);
            return "添加菜品错误";
        }
        return "菜品信息提交成功";
    }

    @RequestMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              ModelMap map) {
        String name = file.getOriginalFilename();
        if (name.length() < 6 || !name.substring(name.length() - 5).equals(".xlsx")) {
            map.put("msg", "文件格式错误");
            map.put("url", "/diancan/food/excel");
            return "zujian/error";
        }
        List<Food> list;
        try {
            list = ExcelImportUtils.excelToFoodInfoList(file.getInputStream());
            log.info("excel导入的list={}", list);
            if (list == null || list.size() <= 0) {
                map.put("msg", "导入失败");
                map.put("url", "/diancan/food/excel");
                return "zujian/error";
            }
            //excel的数据保存到数据库
            try {
                for (Food excel : list) {
                    if (excel != null) {
                        foodRepository.save(excel);
                    }
                }
            } catch (Exception e) {
                log.error("某一行存入数据库失败={}", e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/food/excel");
            return "zujian/error";
        }
        map.put("url", "/diancan/food/list");
        return "zujian/success";
    }
}
