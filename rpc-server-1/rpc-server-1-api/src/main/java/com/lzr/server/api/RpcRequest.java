package com.lzr.server.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用于作为远程调用的请求对象
 * @author Administrator
 */
@Data
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 6977434446977572092L;

    private String className;
    private String methodName;
    private Object[] params;

    public RpcRequest(String className, String methodName, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.params = params;
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
