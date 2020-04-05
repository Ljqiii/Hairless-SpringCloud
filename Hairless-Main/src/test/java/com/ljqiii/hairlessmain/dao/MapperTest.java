package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    DiscussMapper discussMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void selectProblem() {
        List<Problem> problems = problemMapper.selectProblem(null);
        Assert.assertNull(problems);
    }

    @Test
    public void selectCateGory() {
        List<Category> categories = categoryMapper.selectCateGory();
        Assert.assertNotNull(categories);
    }

    @Test
    public void selectProblemWirhCategory() {
        List<Problem> problems = problemMapper.selectProblem("thread");
        Assert.assertNull(problems);
    }

    @Test
    public void testDiscuss() {
        ArrayList<Problem> problems = new ArrayList<>();
        problems.add(Problem.builder().id(1).build());
        problems.add(Problem.builder().id(2).build());
        List<Map<Long, Long>> maps = discussMapper.selectCountDiscuss(problems);
        Assert.assertNotNull(maps);
    }

    @Test
    public void testSubmitOk() {
        ArrayList<Problem> problems = new ArrayList<>();
        problems.add(Problem.builder().id(1).build());
        problems.add(Problem.builder().id(2).build());
        List<Integer> integers = submitMapper.selectSumbitSuccessProblemId(problems,"aaa");
        Assert.assertNotNull(integers);

    }

    @Test
    public void selectProblemById() {
    }

    @Test
    public void deleteProblemById() {
    }
}