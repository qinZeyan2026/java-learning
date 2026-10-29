package com.qinzy;

import com.qinzy.dao.StudentDao;
import com.qinzy.model.Student;
import com.qinzy.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner  SCANNER=new Scanner(System.in);

    private static final StudentService SERVICE=new StudentService(new StudentDao("students.csv"));

    public static void main(String[] args) {
        while(true){
            printMenu();
            String choice=SCANNER.nextLine().trim();
            switch(choice){
                case"1":{
                    handleAdd();
                    break;
                }
                case"2":{
                    handleDelete();
                    break;
                }
                case"3":{
                    handleUpdata();
                    break;
                }
                case"4":{
                    handleFind();
                    break;
                }
                case"5":{
                    handleList();
                    break;
                }
                case"0":{
                    System.out.println("再见！");
                    return;
                }
                default:{
                    System.out.println("非法输入，请输入0-5");
                }
            }
        }
    }

    private static void handleList() {
        List<Student> all = SERVICE.findAll();

        if (all.isEmpty()) {
            System.out.println("（暂无学生数据）");
            return;
        }

        System.out.println("共 " + all.size() + " 名学生：");
        for (Student s : all) {
            System.out.println("  " + s);   // 自动调 toString()
        }
    }

    private static void handleFind() {
        System.out.print("请输入学号：");
        String id = SCANNER.nextLine().trim();

        Student s = SERVICE.findById(id);
        if (s == null) {
            System.out.println("✘ 未找到学号 [" + id + "] 的学生");
        } else {
            System.out.println(s);   // 自动调 toString()
        }
    }

    private static void handleUpdata() {
        System.out.print("请输入要修改的学号：");
        String id = SCANNER.nextLine().trim();

        Student exist = SERVICE.findById(id);
        if (exist == null) {
            System.out.println("✘ 未找到学号 [" + id + "] 的学生");
            return;
        }

        System.out.println("当前信息：" + exist);

        System.out.print("新姓名（回车保持不变）：");
        String name = SCANNER.nextLine().trim();

        System.out.print("新年龄（回车保持不变）：");
        String ageStr = SCANNER.nextLine().trim();

        System.out.print("新性别（回车保持不变）：");
        String gender = SCANNER.nextLine().trim();

        String finalName = name.isEmpty() ? exist.getName() : name;
        int finalAge = ageStr.isEmpty() ? exist.getAge() : Integer.parseInt(ageStr);
        String finalGender = gender.isEmpty() ? exist.getGender() : gender;

        Student newData = new Student(id, finalName, finalAge, finalGender);

        if (SERVICE.update(newData)) {
            System.out.println("✔ 修改成功");
        } else {
            System.out.println("✘ 修改失败");
        }
    }

    private static void handleDelete() {
        System.out.print("请输入要删除的学号：");
        String id = SCANNER.nextLine().trim();

        if (SERVICE.delete(id)) {
            System.out.println("✔ 删除成功");
        } else {
            System.out.println("✘ 未找到学号 [" + id + "] 的学生");
        }
    }

    private static void handleAdd() {
        System.out.println("学号：");
        String id=SCANNER.nextLine().trim();

        System.out.println("姓名：");
        String name =SCANNER.nextLine().trim();

        System.out.println("年龄：");
        int age=Integer.parseInt(SCANNER.nextLine().trim());

        System.out.println("性别：");
        String gender=SCANNER.nextLine().trim();

        Student s = new Student(id, name, age, gender);

        if (SERVICE.add(s)) {
            System.out.println("✔ 添加成功");
        } else {
            System.out.println("✘ 学号 [" + id + "] 已存在，添加失败");
        }
    }

    private static void printMenu() {
        System.out.println("======学生管理系统======");
        System.out.println("1.添加学生");
        System.out.println("2.删除学生");
        System.out.println("3.修改学生");
        System.out.println("4.按学号查询");
        System.out.println("5.查询全部");
        System.out.println("0.退出");
        System.out.println("请选择：");
    }
}
