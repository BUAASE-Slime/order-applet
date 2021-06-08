package com.example.demo.bean;

import com.example.demo.enums.AdminStatusEnum;
import com.example.demo.utils.EnumUtil;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AdminInfo {
    @Id
    @GeneratedValue
    private Integer adminId;
    private String phone;
    private String username;
    private String password;
    private Integer adminType;//1员工，2管理员
    @CreatedDate//自动添加创建时间的注解
    private Date createTime;
    @LastModifiedDate//自动添加更新时间的注解
    private Date updateTime;

    //    @Transient//忽略映射
    public AdminStatusEnum getAdminStatusEnum() {
        return EnumUtil.getByCode(this.adminType, AdminStatusEnum.class);
    }
}
