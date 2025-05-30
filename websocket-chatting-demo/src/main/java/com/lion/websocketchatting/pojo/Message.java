package com.lion.websocketchatting.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operation;
    private String content;
    private Long messageId; // 示例：消息ID，用于标识消息的唯一性
}
