package com.cly.smartdoc.smartdoc.pojo;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 * @Author changleying
 * @create 2019/11/27 17:41
 */
@Builder
@Data
public class User {
    /**
     * 用戶ID
     */
    @NotNull
    private Integer userId;
    /**
     * 用戶名
     */
    private String name;
}
