package com.ljqiii.hairlessmain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.ProblemVO;
import com.ljqiii.hairlessmain.dao.*;
import com.ljqiii.hairlessmain.dataobject.ProblemAcceptance;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    DiscussMapper discussMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    CategoryMapper categoryMapper;

    public PageData<List<ProblemListVO>> setProblemVOData(String username, Page<Problem> problems) {
        PageData<List<ProblemListVO>> pageData = new PageData<>();

        if (problems.getResult().isEmpty()) {
            pageData.setPageInfo(PageInfo.builder()
                    .pageSize(problems.getPageSize())
                    .pageNum(1)
                    .total(0)
                    .build());
            return pageData;
        }

        HashMap<Integer, String> acceptance = acceptance(problems.getResult());

        List<Map<Long, Long>> maps = discussMapper.batchSelectCountDiscuss(problems.getResult());

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

            //设置正确率
            String acceptanceStr = acceptance.get(problem.getId());
            problemListVO.setAcceptance(acceptanceStr == null ? "-" : acceptanceStr);
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

    @Override
    public PageData<List<ProblemListVO>> listProblem(String owner, String username, String category, int pageNum, int pageCount) {

        Page<Problem> problems = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> problemMapper.selectProblem(category, owner));

        return setProblemVOData(username, problems);
    }

    @Override
    public ProblemVO getProblem(String username, int problemid) {
        Problem problem = problemMapper.selectProblemById(problemid);
        if (problem == null) {
            return null;
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);

        //评论数量
        problemVO.setDiscusscount(discussMapper.selectCountDiscuss(problemid));

        //当前用户是否喜欢
        if (username != null) {
            problemVO.setLike(favoriteMapper.selectCountUserNameProblem(problemid, username) > 0 ? true : false);//当前用户是否喜欢
        }

        //收藏数
        problemVO.setFavoriteCount(favoriteMapper.selectCountFavoriteProblem(problemid));//喜欢数量

        //正确率
        String acceptance = acceptance(Arrays.asList(problem)).get(problem.getId());
        problemVO.setAcceptance(acceptance == null ? "-" : acceptance);

        //提交总次数
        problemVO.setSumbitedCount(submitMapper.selectSumProblemSubmitedCount(problem));

        //初始化代码
        ProblemCode problemCode = null;
        try {
            problemCode = JSONObject.parseObject(problem.getInitCode(), ProblemCode.class);
        } catch (Exception e) {
        }
        if (problemCode != null) {

            problemVO.setInitProblemCode(problemCode.getProblemCodeFileItems());
        }

        return problemVO;
    }

    @Override
    public boolean isVipProblem(int problemid) {
        return problemMapper.selectIfOnlyVipByProblemId(problemid);
    }

    @Transactional
    @Override
    public Integer newProblem(Problem problem, List<Category> categories) {
        int i = problemMapper.insertProblem(problem);
        //验证categoryid是否存在

        if (categories.size() != 0) {
            categoryMapper.insertProblemCategory(problem, categories);
        }

        if (i == 1) {
            return problem.getId();
        }
        return null;
    }

    //计算正确率
    public HashMap<Integer, String> acceptance(List<Problem> problems) {
        HashMap<Integer, String> result = new HashMap<>();
        List<ProblemAcceptance> acceptance = submitMapper.acceptance(problems);
        acceptance.stream().forEach(p -> {
            Double acceptance100 = p.getAcceptance() * 100;
            String acceptanceStr = acceptance100.intValue() + " %";
            result.put(p.getProblemid(), acceptanceStr);
        });
        return result;
    }
}
