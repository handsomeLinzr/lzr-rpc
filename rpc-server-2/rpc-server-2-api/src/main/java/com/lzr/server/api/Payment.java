package com.lzr.server.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class Payment implements Serializable {

    private static final long serialVersionUID = 6252750407126411291L;
    private String name;
    private String type;

}
