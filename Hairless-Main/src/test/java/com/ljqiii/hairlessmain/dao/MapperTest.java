package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlessmain.dataobject.ProblemAcceptance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    @Autowired
    FavoriteMapper favoriteMapper;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void acceptance() {

        List<ProblemAcceptance> acceptance = submitMapper.acceptance(Arrays.asList(
                Problem.builder().id(1).build(),
                Problem.builder().id(2).build(),
                Problem.builder().id(3).build()
        ));
        Assert.assertNotNull(acceptance);

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
        List<Map<Long, Long>> maps = discussMapper.batchSelectCountDiscuss(problems);
        Assert.assertNotNull(maps);
    }

    @Test
    public void testBatchInsertFavorite() {
        Problem build = Problem.builder().id(1).build();
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(23);
        arrayList.add(3);
        int aaa = favoriteMapper.batchInsertFavoriteProblem("aaa", build, arrayList);
        Assert.assertNotEquals(0, aaa);
    }

    @Test
    public void testBatchDeleteFavorite() {
        Problem build = Problem.builder().id(1).build();
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(23);
        arrayList.add(3);
        int aaa = favoriteMapper.batchDeleteFavoriteProblem("aaa", build, arrayList);
        Assert.assertNotEquals(0, aaa);
    }


    @Test
    public void testSubmitOk() {
        ArrayList<Problem> problems = new ArrayList<>();
        problems.add(Problem.builder().id(1).build());
        problems.add(Problem.builder().id(2).build());
        List<Integer> integers = submitMapper.selectSumbitSuccessProblemId(problems, "aaa");
        Assert.assertNotNull(integers);

    }

    @Test
    public void selectLeaderBoard() {
        List<CorrectLeaderboard> correctLeaderboards = submitMapper.selectCorrectLeaderboard();
        Assert.assertNotNull(correctLeaderboards);
    }

    @Test
    public void selectProblemById() {
    }

    @Test
    public void deleteProblemById() {
    }
}