package com.example.demo.xiaochengxuadmin;

import com.example.demo.bean.Type;
import com.example.demo.exception.DianCanException;
import com.example.demo.global.GlobalData;
import com.example.demo.repository.TypeRepository;
import com.example.demo.request.TypeReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/xcxleimu")
@Slf4j
public class XcxAdminLeiMuController {

    @Autowired
    private TypeRepository repository;

    @GetMapping("/list")
    @ResponseBody
    public List<Type> list() {
        List<Type> leimuList = repository.findAll();
        log.error("类目list={}", leimuList);
        return leimuList;
    }

    @GetMapping("/remove")
    @ResponseBody
    public void remove(@RequestBody Integer leimuId) {
        repository.deleteById(leimuId);
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody @Valid TypeReq form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "类目信息缺失";
        }

        Type leimu = new Type();
        try {
            if (form.getLeimuId() != null) {
                leimu = repository.findById(form.getLeimuId()).orElse(null);
            }
            BeanUtils.copyProperties(form, leimu);
            leimu.setAdminId(GlobalData.ADMIN_ID);//属于那个卖家
            repository.save(leimu);
        } catch (DianCanException e) {
            return "添加类目错误";
        }
        return "类目信息提交成功";
    }
}
