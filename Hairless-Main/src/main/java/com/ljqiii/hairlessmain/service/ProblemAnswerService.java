package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.domain.ProblemAnswer;

import java.util.List;

public interface ProblemAnswerService {

    boolean newProblemAnswer(ProblemAnswer problemAnswer);

    List<ProblemAnswer> getProblemAnswers(int problemId);
}
