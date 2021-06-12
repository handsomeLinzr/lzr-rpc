package com.lzr.rpc.api.po;

import lombok.Data;

@Data
public class User {

    private String name;
    private Integer age;
    private String address;

    public User(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
