package com.cyl.it.practice.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-28
 */
@Data
public class UserDemo implements Serializable {

    private static final long serialVersionUID = 7964023833120136752L;
    private int id;
    private String name;
    private int age;

    public UserDemo() {
    }

    public UserDemo(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
