package com.example.demo_mysql.dao;

import com.example.demo_mysql.entity.Exam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Exammapping {
//    @Select("select * from exam where ksmc = #{ksmc}")
//    Exam findExamByKsmc(@Param("ksmc") String ksmc);

    @Insert("insert into exam values(#{ksmc},#{ks_sj},#{xq},#{nj},#{xk},#{ls_ws},#{ls_tb},#{id})")
    void addExam(Exam exam);
}
