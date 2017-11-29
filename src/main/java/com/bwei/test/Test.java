package com.bwei.test;

import com.bwei.pojo.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/10/25.
 */
public class Test {
    public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new  ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(context.getBean("student2"));
    }
}
