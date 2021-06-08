package com.example.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
//@DynamicUpdate
//@DynamicInsert
public class Comment {

    @Id
    @GeneratedValue
    private int commentId;
    private String openid;
    private String name;
    private String avatarUrl;//头像
    private String content;

    @CreatedDate//自动添加创建时间的注解
    private Date createTime;


}
