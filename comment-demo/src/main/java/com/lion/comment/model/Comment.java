package com.lion.comment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TableName("comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private String username;

    private String avatar;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    // 父评论ID，如果是顶级评论则为null
    @TableField("parent_id")
    private Long parentId;

    // 评论所属的根评论ID，如果是顶级评论则为自己的ID
    @TableField("root_id")
    private Long rootId;

    // 回复的目标用户名，如果是顶级评论则为null
    @TableField("reply_to_username")
    private String replyToUsername;

    // 子评论列表，不存入数据库，用于构建树状结构
    @TableField(exist = false)
    private List<Comment> children = new ArrayList<>();

    // MyBatis-Plus不支持JPA的生命周期回调，需要在Service层处理
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}