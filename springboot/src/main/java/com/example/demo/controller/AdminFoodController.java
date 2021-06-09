package com.example.demo.controller;

import com.example.demo.bean.Food;
import com.example.demo.bean.Type;
import com.example.demo.enums.FoodStatusEnum;
import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.TypeRepository;
import com.example.demo.request.FoodReq;
import com.example.demo.utils.ExcelExportUtils;
import com.example.demo.utils.ExcelImportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/food")
@Slf4j
public class AdminFoodController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TypeRepository leiMuRepository;

    //列表
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                       ModelMap map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Food> foodPage = foodRepository.findAll(request);
        map.put("foodPage", foodPage);
        map.put("currentPage", page);
        map.put("size", size);
        return "food/list";
    }

    //删除某个菜品
    @GetMapping("/remove")
    public String remove(@RequestParam(value = "foodId", required = false) Integer foodId,
                         ModelMap map) {
        foodRepository.deleteById(foodId);
        map.put("url", "/diancan/food/list");
        return "component/success";
    }

    //菜品详情页
    @GetMapping("/index")
    public String index(@RequestParam(value = "foodId", required = false) Integer foodId,
                        ModelMap map) {
        if (foodId != null) {
            Food food = foodRepository.findById(foodId).orElse(null);
            map.put("templates/food", food);
        }
        //查询所有的类目
        List<Type> leimuList = leiMuRepository.findAll();
        map.put("leimuList", leimuList);
        return "food/index";
    }

    //菜品上架
    @RequestMapping("/on_sale")
    public String onSale(@RequestParam("foodId") int foodId,
                         ModelMap map) {
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
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/food/list");
            return "component/error";
        }

        map.put("url", "/diancan/food/list");
        return "component/success";
    }

    //菜品下架
    @RequestMapping("/off_sale")
    public String offSale(@RequestParam("foodId") int foodId,
                          ModelMap map) {
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
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/food/list");
            return "component/error";
        }

        map.put("url", "/diancan/food/list");
        return "component/success";
    }


    //菜品添加或更新
    @PostMapping("/save")
    public String save(@Valid FoodReq form,
                       BindingResult bindingResult,
                       ModelMap map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diancan/food/index");
            return "component/error";
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
            map.put("msg", "添加菜品出错");
            map.put("url", "/diancan/food/index");
            return "component/error";
        }

        map.put("url", "/diancan/food/list");
        return "component/success";
    }


    //导出菜品到excel
    @GetMapping("/export")
    public String export(HttpServletResponse response, ModelMap map) {
        String fileName = "菜品商品导出";
        String[] titles = {"菜品名", "单价", "库存", "类目", "描述", "商品图片"};
        List<Food> foodList = foodRepository.findAll();
        if (foodList == null || foodList.size() < 1) {
            map.put("msg", "菜品为空");
            map.put("url", "/diancan/food/list");
            return "component/error";
        }
        int size = foodList.size();
        String[][] dataList = new String[size][titles.length];
        for (int i = 0; i < size; i++) {
            Food food = foodList.get(i);
            dataList[i][0] = food.getFoodName();
            dataList[i][1] = "" + food.getFoodPrice();
            dataList[i][2] = "" + food.getFoodStock();//库存
            dataList[i][3] = "" + food.getLeimuType();//菜品类目的type
            dataList[i][4] = food.getFoodDesc();
            dataList[i][5] = food.getFoodIcon();
        }

        try {
            ExcelExportUtils.createWorkbook(fileName, titles, dataList, response);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "导出excel失败");
            map.put("url", "/diancan/food/list");
            return "component/error";
        }
        map.put("url", "/diancan/food/list");
        return "component/success";
    }

    //excel导入网页
    @GetMapping("/excel")
    public String excel(ModelMap map) {
        return "food/excel";
    }

    /*
     * 批量导入excel里的菜品(商品)到数据库
     * */
    @RequestMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              ModelMap map) {
        String name = file.getOriginalFilename();
        if (name.length() < 6 || !name.substring(name.length() - 5).equals(".xlsx")) {
            map.put("msg", "文件格式错误");
            map.put("url", "/diancan/food/excel");
            return "component/error";
        }
        List<Food> list;
        try {
            list = ExcelImportUtils.excelToFoodInfoList(file.getInputStream());
            log.info("excel导入的list={}", list);
            if (list == null || list.size() <= 0) {
                map.put("msg", "导入失败");
                map.put("url", "/diancan/food/excel");
                return "component/error";
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
            return "component/error";
        }
        map.put("url", "/diancan/food/list");
        return "component/success";
    }


}
