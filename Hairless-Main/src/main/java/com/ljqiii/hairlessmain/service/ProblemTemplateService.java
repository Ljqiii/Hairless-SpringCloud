package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.domain.ProblemTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProblemTemplateService {

    ProblemTemplate getTemplate(String lang);



}
