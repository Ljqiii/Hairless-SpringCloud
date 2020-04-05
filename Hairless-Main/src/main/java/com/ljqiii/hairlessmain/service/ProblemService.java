package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;

import java.util.List;

public interface ProblemService {

    PageData<List<ProblemListVO>> listProblem(String username, String category, int pageNum, int pageCount);

}
