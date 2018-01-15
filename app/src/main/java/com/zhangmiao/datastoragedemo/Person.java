package com.zhangmiao.datastoragedemo;

/**
 * Created by zhangmiao on 2016/12/16.
 */
public class Person {

    public int _id;
    public String name;
    public int age;
    public String info;

    public Person() {

    }

    public Person(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }

    public Person(int _id, String name, int age, String info) {
        this._id = _id;
        this.name = name;
        this.age = age;
        this.info = info;
    }
}
