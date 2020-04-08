package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.ProblemVO;

import java.util.List;

public interface ProblemService {

    PageData<List<ProblemListVO>> listProblem(String username, String category, int pageNum, int pageCount);

    ProblemVO getProblem(String username, int problemid);

    boolean isVipProblem(int problemid);

}
