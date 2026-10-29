package com.qinzy.service;

import com.qinzy.dao.StudentDao;
import com.qinzy.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final StudentDao dao;
    private final List<Student> students;

    public StudentService(StudentDao dao) {
        this.dao = dao;
        this.students = dao.loadAll();
    }

    public boolean add(Student s){
        if(findById(s.getId())!=null){
            return false;
        }

        students.add(s);

        persist();

        return true;
    }

    public boolean delete(String id){
        boolean removed = students.removeIf(s -> s.getId().equals(id));

        if(removed){
            persist();
        }

        return removed;
    }

    public boolean update(Student newData){
        Student old=findById(newData.getId());

        if(old==null){
            return false;
        }

        old.setName(newData.getName());
        old.setAge(newData.getAge());
        old.setGender(newData.getGender());

        persist();

        return true;
    }

    public Student findById(String id){
        for (Student s : students) {
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }

    public List<Student> findAll(){
        return students;
    }

    private void persist(){
        dao.saveAll(students);
    }
}
