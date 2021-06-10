package com.example.demo.xiaochengxuadmin;

import com.example.demo.bean.AdminInfo;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.AdminRepository;
import com.example.demo.request.AdminForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/xcxadmin")
@Slf4j
public class XcxAdminController {

    @Autowired
    AdminRepository repository;

    @GetMapping("/list")
    @ResponseBody
    public List<AdminInfo> list() {
        List<AdminInfo> adminList = repository.findAll();
        return adminList;
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody @Valid AdminForm form, BindingResult bindingResult) {
        log.info("SellerForm={}", form);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "管理员信息缺失";
        }
        AdminInfo admin = new AdminInfo();
        try {
            if (form.getAdminId() != null) {
                admin = repository.findByAdminId(form.getAdminId());
            }
            BeanUtils.copyProperties(form, admin);
            repository.save(admin);
        } catch (DianCanException e) {
            return "修改失败";
        }
        return "修改成功";
    }

}
