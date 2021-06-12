package com.lzr.rpc.api.po;

import lombok.Data;

import java.util.Arrays;

/**
 * 用于封装rpc调用的信息
 */
@Data
public class RpcRequest {

    // 接口名
    private String className;
    // 方法名
    private String methodName;
    // 参数列表
    private Object[] params;

    // 返回类型
    private Class<?> targetClass;

    public RpcRequest(String className, String methodName, Object[] params, Class<?> targetClass) {
        this.className = className;
        this.methodName = methodName;
        this.params = params;
        this.targetClass = targetClass;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
