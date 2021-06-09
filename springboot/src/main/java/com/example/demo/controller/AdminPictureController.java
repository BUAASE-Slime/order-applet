package com.example.demo.controller;

import com.example.demo.api.ResultVO;
import com.example.demo.bean.PictureInfo;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.PictureRepository;
import com.example.demo.request.PictureForm;
import com.example.demo.utils.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户相关
 */
@Controller
@RequestMapping("/picture")
@Slf4j
public class AdminPictureController {

    @Autowired
    PictureRepository repository;

    /*
     * 页面相关
     * */
    @GetMapping("/list")
    public String list(ModelMap map) {
        List<PictureInfo> pictures = repository.findAll();
        map.put("categoryList", pictures);
        return "picture/list";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "picId", required = false) Integer picId,
                        ModelMap map) {
        PictureInfo picture = repository.findByPicId(picId);
        map.put("category", picture);
        return "picture/index";
    }

    //删除轮播图
    @GetMapping("/remove")
    public String remove(@RequestParam(value = "picId", required = false) Integer picId,
                         ModelMap map) {
        repository.deleteById(picId);
        map.put("url", "/diancan/picture/list");
        return "zujian/success";
    }

    //保存/更新
    @PostMapping("/save")
    public String save(@Valid PictureForm form,
                       BindingResult bindingResult,
                       ModelMap map) {
        log.info("SellerForm={}", form);
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diancan/picture/index");
            return "zujian/error";
        }
        PictureInfo picture = new PictureInfo();
        try {
            if (form.getPicId() != null) {
                picture = repository.findByPicId(form.getPicId());
            }
            BeanUtils.copyProperties(form, picture);
            repository.save(picture);
        } catch (DianCanException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/picture/index");
            return "zujian/error";
        }

        map.put("url", "/diancan/picture/list");
        return "zujian/success";
    }


    /*
     * 返回json给小程序
     * */
    @GetMapping("/getAll")
    @ResponseBody
    public ResultVO getUserInfo() {
        List<PictureInfo> pictures = repository.findAll();
        return ApiUtil.success(pictures);
    }

}
