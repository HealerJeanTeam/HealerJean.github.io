package com.hlj.swagger.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description = "我是User描述")
public class User {

    @ApiModelProperty(value = "用户的姓名，比如'李四'")
    private String name;
    @ApiModelProperty(value = "id",required = true)
    private String id;
    @ApiModelProperty(value = "用户的年龄，比如：20")
    private Integer age;

    @ApiModelProperty(value = "用户的子类，测试用",required = true)
    private Base base;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }



    public User(String name, String id, Integer age, Base base) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.base = base;
    }

}
