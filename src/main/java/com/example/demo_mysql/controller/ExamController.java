package com.example.demo_mysql.controller;
import com.example.demo_mysql.entity.Exam;
import com.example.demo_mysql.entity.Result;
import com.example.demo_mysql.service.ExamService;
import com.example.demo_mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * (Exam)表控制层
 *
 * @author makejava
 * @since 2020-04-14 11:41:37
 */
@Controller
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping("/insert")
    @ResponseBody
    public void insert(@RequestBody Exam exam){
         examService.save_exam(exam);
    }

        @GetMapping("/getTasks")
        @ResponseBody
        public String listTasks(){
            return "任务列表";
        }

}