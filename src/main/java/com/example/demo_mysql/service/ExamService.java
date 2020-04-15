package com.example.demo_mysql.service;

import com.example.demo_mysql.dao.Exammapping;
import com.example.demo_mysql.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ExamService {

    @Autowired(required = false)
    private Exammapping exammapping;

    public void save_exam(Exam exam){
        exammapping.addExam(exam);
    }

}