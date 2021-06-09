package com.example.demo.controller;

import com.example.demo.api.ResultVO;
import com.example.demo.bean.Comment;
import com.example.demo.bean.WxOrderRoot;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.DianCanException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.OrderRootRepository;
import com.example.demo.response.WxOrderResponse;
import com.example.demo.utils.ApiUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * desc:评论相关
 */
@RestController
public class CommentController {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private WxOrderUtils wxOrder;
    @Autowired
    private OrderRootRepository masterRepository;

    //订单详情
    @PostMapping("/comment")
    public ResultVO<Comment> detail(@RequestParam("openid") String openid,
                                    @RequestParam("orderId") int orderId,
                                    @RequestParam("name") String name,
                                    @RequestParam("avatarUrl") String avatarUrl,
                                    @RequestParam("content") String content) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            throw new DianCanException(ResultEnum.PARAM_ERROR);
        }
        //提交评论
        Comment comment = new Comment();
        comment.setName(name);
        comment.setAvatarUrl(avatarUrl);
        comment.setOpenid(openid);
        comment.setContent(content);
        Comment save = repository.save(comment);

        //修改订单状态
        WxOrderResponse orderDTO = wxOrder.findOne(orderId);
        orderDTO.setOrderStatus(OrderStatusEnum.COMMENT.getCode());
        WxOrderRoot orderMaster = new WxOrderRoot();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        WxOrderRoot updateResult = masterRepository.save(orderMaster);
        return ApiUtil.success(save);
    }

    //所有评论
    @GetMapping("/commentList")
    public ResultVO<List<Comment>> commentList() {
        List<Comment> all = repository.findAll();
        return ApiUtil.success(all);
    }

    //单个用户的所有评论
    @GetMapping("/userCommentList")
    public ResultVO<List<Comment>> userCommentList(@RequestParam("openid") String openid) {
        List<Comment> all = repository.findAllByOpenid(openid);
        return ApiUtil.success(all);
    }
}
