package com.bwei.pojo;

/**
 * Created by Administrator on 2017/10/25.
 */
public class Student{
    private String id;
    private String name;
    private String sex;

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public void introduce() {
        System.out.println("ID:" + getId() + "---name:" + getName()+"---sex:"+getSex());
    }
}
