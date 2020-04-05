package com.ljqiii.hairlessmain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlessmain.dao.ProblemMapper;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public PageData<List<ProblemListVO>> listProblem(String username, String category, int pageNum, int pageCount) {
        PageData<List<ProblemListVO>> pageData = new PageData<>();

        Page<Problem> problems = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> problemMapper.selectProblem(category));

        List<ProblemListVO> problemVoList = problems.stream().map(problem -> {
            ProblemListVO problemListVO = new ProblemListVO();
            BeanUtils.copyProperties(problem, problemListVO);
            return problemListVO;
        }).collect(Collectors.toList());

        pageData.setContent(problemVoList);
        pageData.setPageInfo(PageInfo.builder()
                .pageSize(problems.getPageSize())
                .pageNum(problems.getPageNum())
                .total(problems.getTotal())
                .build());

        if (StringUtils.isEmpty(username)) {

        }

        return pageData;
    }
}
