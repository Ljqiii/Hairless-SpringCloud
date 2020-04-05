package com.ljqiii.hairlessmain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlessmain.dao.DiscussMapper;
import com.ljqiii.hairlessmain.dao.ProblemMapper;
import com.ljqiii.hairlessmain.dao.SubmitMapper;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    DiscussMapper discussMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Override
    public PageData<List<ProblemListVO>> listProblem(String username, String category, int pageNum, int pageCount) {
        PageData<List<ProblemListVO>> pageData = new PageData<>();

        Page<Problem> problems = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> problemMapper.selectProblem(category));

        if (problems.getResult().isEmpty()) {
            return pageData;
        }

        List<Map<Long, Long>> maps = discussMapper.selectCountDiscuss(problems.getResult());

        //获得评论数
        HashMap<Long, Long> problemDiscussCountMap = new HashMap();
        maps.stream().forEach(p -> problemDiscussCountMap.put(p.get("problemid"), p.get("count")));

        List<Integer> sumbitSuccessProblemIds = null;
        List<Integer> sumbitProblemIdIds = null;


        //查看提交
        if (!StringUtils.isEmpty(username)) {
            sumbitSuccessProblemIds = submitMapper.selectSumbitSuccessProblemId(problems.getResult(), username);
            sumbitProblemIdIds = submitMapper.selectSumbitProblemId(problems.getResult(), username);
        }

        List<Integer> finalSumbitProblemIdIds = sumbitProblemIdIds;
        List<Integer> finalSumbitSuccessProblemIds = sumbitSuccessProblemIds;
        List<ProblemListVO> problemVoList = problems.stream().map(problem -> {
            ProblemListVO problemListVO = new ProblemListVO();
            BeanUtils.copyProperties(problem, problemListVO);

            //提交结果，先全设置为错误
            if (finalSumbitProblemIdIds != null && finalSumbitProblemIdIds.contains(problem.getId())) {
                problemListVO.setIsResolve(false);
            }
            //把成功的设置为正确
            if (finalSumbitSuccessProblemIds != null && finalSumbitSuccessProblemIds.contains(problem.getId())) {
                problemListVO.setIsResolve(true);
            }

            //评论次数
            problemListVO.setDiscusscount(
                    problemDiscussCountMap.get(
                            Long.valueOf(problem.getId())) == null ? 0L : problemDiscussCountMap.get(Long.valueOf(problem.getId())));

            return problemListVO;
        }).collect(Collectors.toList());

        pageData.setContent(problemVoList);
        pageData.setPageInfo(PageInfo.builder()
                .pageSize(problems.getPageSize())
                .pageNum(problems.getPageNum())
                .total(problems.getTotal())
                .build());

        return pageData;
    }
}
