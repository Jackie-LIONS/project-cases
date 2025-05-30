package com.lion.comment.controller;

import com.lion.comment.dto.CommentDTO;
import com.lion.comment.dto.CommentRequest;
import com.lion.comment.service.CommentService;
import com.lion.common.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论树
     */
    @GetMapping
    public BaseResult<List<CommentDTO>> getAllComments() {
        return BaseResult.ok(commentService.getCommentTree());
    }

    /**
     * 获取单个评论及其所有回复
     */
    @GetMapping("/{id}")
    public BaseResult<CommentDTO> getCommentById(@PathVariable Long id) {
        return BaseResult.ok(commentService.getCommentById(id));
    }

    /**
     * 添加评论
     */
    @PostMapping
    public BaseResult<CommentDTO> addComment(@RequestBody CommentRequest request) {
        return  BaseResult.ok(commentService.addComment(request));
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    public BaseResult deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return BaseResult.ok();
    }
}