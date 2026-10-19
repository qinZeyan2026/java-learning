package com.scoreanalyzer;

import com.scoreanalyzer.io.CsvFileReader;
import com.scoreanalyzer.processor.StudentParser;
import com.scoreanalyzer.service.ScoreStatService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvFileReader reader = new CsvFileReader();
        StudentParser parser = new StudentParser();
        ScoreStatService statService = new ScoreStatService();

        try {
            // IO层：读取csv全部行
            List<String> lines = reader.readAllLines("scores.csv");
            List<Student> studentList = new ArrayList<>();

            //  Processor层：循环解析每行，封装Student，跳过表头i=0
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                Student stu = parser.parseStudent(line);

                if(stu != null){
                    studentList.add(stu);
                }
            }

            // Service层：业务计算
            List<Student> sortedStuList = statService.sortByAverage(studentList);
            double mathAvg = statService.calcMathAvg(studentList);
            double chineseAvg = statService.calcChinsesAvg(studentList);
            double englishAvg = statService.calcEnglishAvg(studentList);

            // 5. 打印
            System.out.println("====学生成绩排名（按平均分降序）====");
            for(Student stu : sortedStuList){
                System.out.printf("姓名：%s | 数学：%d 语文：%d 英语：%d | 平均分：%.2f%n",
                        stu.name(), stu.math(), stu.chinese(), stu.english(), stu.average());
            }
            System.out.println("====班级统计====");
            System.out.printf("数学班级平均分：%.2f%n", mathAvg);
            System.out.printf("语文班级平均分：%.2f%n", chineseAvg);
            System.out.printf("英语班级平均分：%.2f%n", englishAvg);

        } catch (IOException e) {
            System.out.println("❌ 文件读取失败！检查文件名称和位置");
            e.printStackTrace();
        }
    }
}
