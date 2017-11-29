package com.bwei.pojo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public class ClassPathXmlApplicationContext {
    private String xmlPath;

    public ClassPathXmlApplicationContext(String xmlPath) {
        // 传入配置文件名
        this.xmlPath = xmlPath;
    }

    public static void main(String[] args) {
        // 模仿Spring IOC写法
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // obj强转成User对象
        Student user = (Student) context.getBean("student1");
        user.introduce();
    }

    public Object getBean(String beanID) {
        // 将要返回的Object对象
        Object obj = null;
        // dom4j解析器
        SAXReader saxReader = new SAXReader();
        Document read = null;
        try {
            // 读取根路径下的配置文件
            read = saxReader.read(this.getClass().getClassLoader()
                    .getResourceAsStream(xmlPath));
        } catch (DocumentException e) {
            System.out.println("未找到路径下的配置文件");
        }

        if (read == null) {
            return null;
        }

        // 得到config.xml的根节点
        Element root = read.getRootElement();
        // 得到根节点的子节点
        List<Element> elements = root.elements();

        // 如果没有子节点，结束方法
        if (elements.size() <= 0) {
            System.out.println("没有子节点");
            return null;
        }

        // 找到指定id属性的bean节点
        Element element = null;
        // 循环遍历子节点
        for (int i = 0; i < elements.size(); i++) {
            element = elements.get(i);
            // 如果子节点中没有bean节点，结束方法
            if (!element.getName().equals("bean")) {
                System.out.println("未找到bean节点");
                return null;
            }
            // 得到bean节点的id的value
            String id = element.attributeValue("id");
            // 如果有和传入的请求一样的id属性的bean，跳出循环
            if (!id.equals(beanID)) {
                if (i < elements.size() - 1) {
                    continue;
                } else {
                    System.out.println("未找到对应id属性的bean节点");
                    return null;
                }
            }
            break;
        }
        // 得到对应id属性的bean节点中的class属性
        String beanClass = element.attributeValue("class");
        Class<?> forName = null;
        try {
            // 通过反射得到并创建指定class的对象
            forName = Class.forName(beanClass);
            obj = forName.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // 如果指定class文件没有
            System.out.println("未找到指定class");
            return null;
        }

        // 得到指定的bean节点的属性
        List<Element> elements2 = element.elements();
        // 如果没有子节点，结束方法
        if (elements2.size() <= 0) {
            return null;
        }
        // 遍历bean节点属性
        for (Element element2 : elements2) {
            String name = element2.attributeValue("name");
            String value = element2.attributeValue("value");
            Field declaredField;
            try {
                // 通过属性name和反射得到User的set方法
                declaredField = forName.getDeclaredField(name);
                // 配置反射得到操作private变量的权限
                declaredField.setAccessible(true);
                // 将value值set到obj中
                declaredField.set(obj, value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 返回obj
        return obj;
    }
}

