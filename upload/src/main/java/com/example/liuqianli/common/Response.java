package com.example.liuqianli.common;

import lombok.Builder;
import lombok.Data;

/**
 * Tool:IntelliJ IDEA
 * Description:
 * Date：2024-11-22-16:34
 *
 * @ Author:两袖青蛇
 */
@Data
@Builder
public class Response<T> {
    String code;
    String isOk;
    String message;
    String msg;
    T data;
}
