package com.lion.comment.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}