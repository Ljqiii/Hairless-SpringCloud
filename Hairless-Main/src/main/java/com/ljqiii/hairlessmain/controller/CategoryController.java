package com.ljqiii.hairlessmain.controller;


import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.CategoryVO;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlessmain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/categorylist")
    public HairlessResponse<PageData<List<CategoryVO>>> getCategory(
            @RequestParam(value = "withproblemcount", required = false, defaultValue = "false") boolean withProblemCount,
            @RequestParam(value = "pagenum", required = false, defaultValue = "0") int pageNum,
            @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount,
            Principal principal) {

        HairlessResponse<PageData<List<CategoryVO>>> response = new HairlessResponse<>();
        PageData<List<CategoryVO>> categoryVOS = categoryService.queryCategory(pageNum, pageCount, withProblemCount);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(categoryVOS);
        return response;
    }

    @GetMapping("/getcategoryname")
    public HairlessResponse<Object> getCategoryName(@RequestParam("symbol") String symbol) {
        String categoryName = categoryService.getCategoryName(symbol);
        HairlessResponse<Object> response = HairlessResponse.builder().data(categoryName).build();
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

}
