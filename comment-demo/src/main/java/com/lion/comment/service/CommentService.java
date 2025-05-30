package com.lion.comment.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.comment.dto.CommentDTO;
import com.lion.comment.dto.CommentRequest;
import com.lion.comment.model.Comment;
import com.lion.comment.repository.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    /**
     * 添加评论
     */
    @Transactional
    public CommentDTO addComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUsername(request.getUsername());
        comment.setAvatar(request.getAvatar());
        comment.setParentId(request.getParentId());
        comment.setReplyToUsername(request.getReplyToUsername());
        
        // 设置创建时间
        comment.onCreate();
        
        // 设置根评论ID
        if (request.getParentId() == null) {
            // 如果是顶级评论，保存后设置rootId为自己的ID
            this.save(comment);
            comment.setRootId(comment.getId());
            this.updateById(comment);
        } else {
            // 如果是回复评论，设置rootId为父评论的rootId
            Comment parentComment = this.getById(request.getParentId());
            if (parentComment == null) {
                throw new RuntimeException("父评论不存在");
            }
            comment.setRootId(parentComment.getRootId());
            this.save(comment);
        }
        
        return CommentDTO.fromEntity(comment);
    }

    /**
     * 获取评论树
     */
    public List<CommentDTO> getCommentTree() {
        // 获取所有顶级评论
        List<Comment> rootComments = lambdaQuery()
                .isNull(Comment::getParentId)
                .orderByDesc(Comment::getCreatedAt)
                .list();
        List<CommentDTO> result = new ArrayList<>();
        
        // 为每个顶级评论构建评论树
        for (Comment rootComment : rootComments) {
            CommentDTO rootDTO = buildCommentTree(rootComment);
            result.add(rootDTO);
        }
        
        return result;
    }
    
    /**
     * 构建单个评论树
     */
    private CommentDTO buildCommentTree(Comment rootComment) {
        // 获取该根评论下的所有评论
        List<Comment> allComments = lambdaQuery()
                .eq(Comment::getRootId, rootComment.getId())
                .ne(Comment::getId, rootComment.getId()) // 排除根评论自身，避免重复
                .orderByAsc(Comment::getCreatedAt)
                .list();
        
        // 创建一个映射，用于快速查找评论
        Map<Long, Comment> commentMap = new HashMap<>();
        commentMap.put(rootComment.getId(), rootComment);
        
        // 将所有评论放入映射中，并确保children集合已初始化
        for (Comment comment : allComments) {
            if (comment.getChildren() == null) {
                comment.setChildren(new ArrayList<>());
            }
            commentMap.put(comment.getId(), comment);
        }
        
        // 构建评论树 - 确保多级嵌套评论正确显示
        for (Comment comment : allComments) {
            if (comment.getParentId() != null && commentMap.containsKey(comment.getParentId())) {
                Comment parent = commentMap.get(comment.getParentId());
                parent.getChildren().add(comment);
            }
        }
        
        // 转换为DTO并返回
        return CommentDTO.fromEntity(rootComment);
    }
    
    /**
     * 获取单个评论及其所有回复
     */
    public CommentDTO getCommentById(Long id) {
        Comment comment = getById(id);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        
        return buildCommentTree(comment);
    }
    
    /**
     * 删除评论
     */
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = this.getById(id);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        
        // 如果是顶级评论，需要删除所有子评论
        if (comment.getParentId() == null) {
            List<Comment> children = lambdaQuery()
                    .eq(Comment::getRootId, comment.getId())
                    .orderByAsc(Comment::getCreatedAt)
                    .list();
            for (Comment child : children) {
                this.removeById(child.getId());
            }
        }
        
        this.removeById(id);
    }
}