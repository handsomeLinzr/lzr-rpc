package com.lzr.server.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -5642032139653268532L;

    private String className;
    private String methodName;
    private Object[] params;
    private String version;

    public RpcRequest(String className, String methodName, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.params = params;
    }
}
