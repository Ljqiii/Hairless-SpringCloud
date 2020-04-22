package com.ljqiii.hairlessmain.service.impl;

import com.ljqiii.hairlesscommon.domain.ProblemTemplate;
import com.ljqiii.hairlessmain.dao.ProblemTemplateMapper;
import com.ljqiii.hairlessmain.service.ProblemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemTemplateServiceImpl implements ProblemTemplateService {

    @Autowired
    ProblemTemplateMapper problemTemplateMapper;

    public ProblemTemplate getTemplate(String lang) {
        return problemTemplateMapper.selectProblemTemplate(lang);
    }

}
