package com.qinzy.dao;

import com.qinzy.model.Student;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private final Path dataFile;

    public StudentDao(String filePath) {
        dataFile = Paths.get(filePath);
    }

    public List<Student> loadAll(){
        List<Student> studentList=new ArrayList<>();

        if(!Files.exists(dataFile)){
            return studentList;
        }
        try{
            List<String> lines=Files.readAllLines(dataFile, StandardCharsets.UTF_8);

            for (String line : lines) {
                if(line.isBlank()){
                    continue;
                }
                Student stu=Student.fromLine(line);
                if(stu!=null){
                    studentList.add(stu);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }

    //写全部：List<Student> → 每行一个字符串 → 写入文件（覆盖）
    public void saveAll(List<Student> students){
        List<String> lineList = new ArrayList<>();
        for (Student s : students) {
            lineList.add(s.toLine());
        }
        try {
            Files.write(dataFile, lineList, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
