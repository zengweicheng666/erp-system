package com.erp.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或token已过期"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "资源不存在"),
    USERNAME_OR_PASSWORD_ERROR(1001, "用户名或密码错误"),
    USER_DISABLED(1002, "用户已被禁用"),
    USER_NOT_FOUND(1003, "用户不存在"),
    ROLE_NOT_FOUND(1004, "角色不存在"),
    MENU_NOT_FOUND(1005, "菜单不存在"),
    DICT_NOT_FOUND(1006, "字典不存在"),
    PRODUCT_NOT_FOUND(2001, "商品不存在"),
    WAREHOUSE_NOT_FOUND(2002, "仓库不存在"),
    STOCK_NOT_ENOUGH(2003, "库存不足"),
    DUPLICATE_DATA(3001, "数据重复");

    private final int code;
    private final String message;
}
