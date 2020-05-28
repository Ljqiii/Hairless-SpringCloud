package com.ljqiii.hairlessmain.service.impl;

import com.ljqiii.hairlesscommon.domain.ProblemAnswer;
import com.ljqiii.hairlessmain.dao.ProblemAnswerMapper;
import com.ljqiii.hairlessmain.service.ProblemAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProblemAnswerServiceImpl implements ProblemAnswerService {

    @Autowired
    ProblemAnswerMapper problemAnswerMapper;

    @Override
    public boolean newProblemAnswer(ProblemAnswer problemAnswer) {
        problemAnswerMapper.insertProblemAnswer(problemAnswer);
        return false;
    }

    @Override
    public List<ProblemAnswer> getProblemAnswers(int problemId) {
        return problemAnswerMapper.selectProblemAnswerByProblemid(problemId);
    }
}
