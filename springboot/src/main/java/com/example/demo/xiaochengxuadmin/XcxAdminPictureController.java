package com.example.demo.xiaochengxuadmin;

import com.example.demo.bean.PictureInfo;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.PictureRepository;
import com.example.demo.request.PictureForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/xcxpicture")
@Slf4j
public class XcxAdminPictureController {

    @Autowired
    PictureRepository repository;

    @GetMapping("/list")
    @ResponseBody
    public List<PictureInfo> list() {
        List<PictureInfo> pictures = repository.findAll();
        return pictures;
    }

    @GetMapping("/remove")
    @ResponseBody
    public void remove(@RequestParam Integer picId) {
        repository.deleteById(picId);
    }

    //保存/更新
    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody @Valid PictureForm form, BindingResult bindingResult) {
        log.info("SellerForm={}", form);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "轮播图信息不足";
        }
        PictureInfo picture = new PictureInfo();
        try {
            if (form.getPicId() != null) {
                picture = repository.findByPicId(form.getPicId());
            }
            BeanUtils.copyProperties(form, picture);
            repository.save(picture);
        } catch (DianCanException e) {
            return "添加轮播图失败";
        }
        return "轮播图信息提交成功";
    }

}
