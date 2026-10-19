package com.scoreanalyzer.service;

import com.scoreanalyzer.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreStatService {
    public List<Student> sortByAverage(List<Student> studentList) {
        return studentList.stream()
                //按学生平均分降序
                .sorted(Comparator.comparingDouble(Student::average).reversed())
                .collect(Collectors.toList());

    }

    //计算班级数学平均分
    public double calcMathAvg(List<Student> studentList) {
        double sum = 0;
        for (Student student : studentList) {
            sum += student.math();
        }
        return sum / studentList.size();
    }

    //计算班级语文平均分
    public double calcChinsesAvg(List<Student> studentList) {
        double sum = 0;
        for (Student student : studentList) {
            sum += student.chinese();
        }
        return sum / studentList.size();
    }

    //计算班级英语平均分
    public double calcEnglishAvg(List<Student> studentList) {
        double sum = 0;
        for (Student student : studentList) {
            sum += student.english();
        }
        return sum / studentList.size();
    }
}
