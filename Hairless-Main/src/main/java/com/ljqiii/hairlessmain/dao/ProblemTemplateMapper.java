package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.ProblemTemplate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemTemplateMapper {

    @Select("select * from problemtemplate where lang=#{lang} limit 1")
    ProblemTemplate selectProblemTemplate(@Param("lang") String lang);

}
