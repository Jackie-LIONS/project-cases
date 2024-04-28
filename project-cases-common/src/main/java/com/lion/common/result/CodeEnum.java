package com.lion.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回状态码枚举类
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {
    // 正常
    SUCCESS(200, "OK"),
    // 系统异常
    SYSTEM_ERROR(500, "系统异常"),
    // 业务异常
    PARAMETER_ERROR(601, "参数异常"),
    INSERT_PRODUCT_TYPE_ERROR(602,"该商品类型不能添加子类型"),
    DELETE_PRODUCT_TYPE_ERROR(603,"该商品类型有子类型，不能被删除"),
    UPLOAD_FILE_ERROR(604,"文件上传失败"),
    REGISTER_CODE_ERROR(605,"验证码不正确"),
    REGISTER_REPEAT_PHONE_ERROR(606,"输入手机号已存在"),
    REGISTER_REPEAT_NAME_ERROR(607,"输入姓名已存在"),
    LOGIN_NAME_PASSWORD_ERROR(608,"用户名或密码错误"),
    LOGIN_CODE_ERROR(609,"登录验证码错误"),
    VERIFY_TOKEN_ERROR(611,"验证令牌失败"),
    QR_CODE_ERROR(612,"二维码生成异常"),
    CHECK_SIGN_ERROR(613,"验签错误"),
    NO_STOCK_ERROR(614,"库存不足异常"),
    ORDER_EXPIRED_ERROR(615,"订单过期异常")    ;


    private final Integer code;
    private final String message;
}
