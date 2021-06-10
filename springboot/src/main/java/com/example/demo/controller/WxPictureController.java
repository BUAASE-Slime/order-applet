package com.example.demo.controller;

import com.example.demo.api.ResultVO;
import com.example.demo.bean.PictureInfo;
import com.example.demo.repository.PictureRepository;
import com.example.demo.utils.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序端轮播图
 */
@RestController
@RequestMapping("/wxPicture")
public class WxPictureController {
    @Autowired
    PictureRepository repository;

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
