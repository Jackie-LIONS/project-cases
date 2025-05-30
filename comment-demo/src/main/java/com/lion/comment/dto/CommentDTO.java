package com.lion.comment.dto;

import com.lion.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private String username;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long parentId;
    private Long rootId;
    private String replyToUsername;
    private List<CommentDTO> children = new ArrayList<>();

    // 从实体转换为DTO
    public static CommentDTO fromEntity(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUsername(comment.getUsername());
        dto.setAvatar(comment.getAvatar());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setParentId(comment.getParentId());
        dto.setRootId(comment.getRootId());
        dto.setReplyToUsername(comment.getReplyToUsername());
        
        // 递归转换子评论
        if (comment.getChildren() != null && !comment.getChildren().isEmpty()) {
            List<CommentDTO> childrenDTOs = new ArrayList<>();
            for (Comment child : comment.getChildren()) {
                childrenDTOs.add(fromEntity(child));
            }
            dto.setChildren(childrenDTOs);
        }
        
        return dto;
    }

    // 从DTO转换为实体
    public static Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setContent(dto.getContent());
        comment.setUsername(dto.getUsername());
        comment.setAvatar(dto.getAvatar());
        comment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        comment.setUpdatedAt(dto.getUpdatedAt());
        comment.setParentId(dto.getParentId());
        comment.setRootId(dto.getRootId());
        comment.setReplyToUsername(dto.getReplyToUsername());
        
        return comment;
    }
}