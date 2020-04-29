package com.ljqiii.hairlessmain.service;

import com.github.pagehelper.Page;
import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.ProblemVO;

import java.util.List;

public interface ProblemService {

    PageData<List<ProblemListVO>> setProblemVOData(String username, Page<Problem> problems);

    PageData<List<ProblemListVO>> listProblem( String owner,String username, String category, int pageNum, int pageCount);

    ProblemVO getProblem(String username, int problemid);

    boolean isVipProblem(int problemid);

    Integer newProblem(Problem problem,List<Category> categories );

}
