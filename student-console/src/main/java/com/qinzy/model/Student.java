package com.qinzy.model;

public class Student {
    private String id;
    private String name;
    private int age;
    private String gender;

    public Student(String id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Student() {

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "学号=" + id + ", 姓名=" + name + ", 年龄=" + age + ", 性别=" + gender;
    }

    public String toLine() {
        return id + "," + name + "," + age + "," + gender;
    }

    public static Student fromLine(String line) {
        if(line==null||line.isBlank()){
            return null;
        }
        String[] split = line.split(",");
        if(split.length!=4){
            return null;
        }
        String id=split[0].trim();
        String name=split[1].trim();
        int age;
        try {
            age=Integer.parseInt(split[2].trim());
        }catch (NumberFormatException e){
            return null;
        }
        String gender=split[3].trim();

        return new Student(id,name,age,gender);
    }
}
