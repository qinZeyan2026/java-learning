package com.scoreanalyzer.processor;

import com.scoreanalyzer.Student;

public class StudentParser {
    //CSV行解析器：把一行csv文本，转为Student对象

    public Student parseStudent(String line) {
        try {
            String[] parts=line.split(",");

            if(parts.length!=4){
                System.out.println("⚠ 该行字段数量不对，跳过：" + line);
                return null;
            }


            String name=parts[0];
            int math=Integer.parseInt(parts[1]);
            int chines=Integer.parseInt(parts[2]);
            int english=Integer.parseInt(parts[3]);

            return new Student(name,math,chines,english);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ 分数不是有效数字，跳过该行：" + line);
            return null;
        }
    }
}
