package com.example.demo.controller;

import com.example.demo.bean.AdminInfo;
import com.example.demo.global.GlobalConst;
import com.example.demo.enums.AdminStatusEnum;
import com.example.demo.repository.AdminRepository;
import com.example.demo.request.AdminForm;
import com.example.demo.exception.DianCanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminRepository repository;

    //管理员页面相关
    @GetMapping("/list")
    public String list(HttpServletRequest request, ModelMap map) {
        List<AdminInfo> adminList = repository.findAll();
        map.put("adminList", adminList);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(GlobalConst.COOKIE_TOKEN)) {
                    String cookieValue = cookie.getValue();
                    log.info("获取到存储的cookie={}", cookieValue);
                    if (!StringUtils.isEmpty(cookieValue)) {
                        AdminInfo adminInfo = repository.findByAdminId(Integer.parseInt(cookieValue));
                        if (adminInfo != null && Objects.equals(AdminStatusEnum.SUPER_ADMIN.getCode(), adminInfo.getAdminType())) {
                            map.put("isAdmin", true);
                        }
                    }
                }
            }
        }
        return "admin/list";
    }

    //管理员详情页
    @GetMapping("/index")
    public String index(@RequestParam(value = "adminId", required = false) Integer adminId,
                        ModelMap map) {
        AdminInfo adminInfo = repository.findByAdminId(adminId);
        map.put("adminInfo", adminInfo);
        map.put("enums", AdminStatusEnum.values());
        log.error("管理员枚举={}", AdminStatusEnum.values());
        return "admin/index";
    }

    //保存/更新
    @PostMapping("/save")
    public String save(@Valid AdminForm form,
                       BindingResult bindingResult,
                       ModelMap map) {
        log.info("SellerForm={}", form);
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diancan/admin/index");
            return "component/error";
        }
        AdminInfo admin = new AdminInfo();
        try {
            if (form.getAdminId() != null) {
                admin = repository.findByAdminId(form.getAdminId());
            }
            BeanUtils.copyProperties(form, admin);
            repository.save(admin);
        } catch (DianCanException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diancan/admin/index");
            return "component/error";
        }

        map.put("url", "/diancan/admin/list");
        return "component/success";
    }
}
