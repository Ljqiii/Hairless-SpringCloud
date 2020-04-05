package com.ljqiii.hairlessmain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.CategoryVO;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlessmain.dao.CategoryMapper;
import com.ljqiii.hairlessmain.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public PageData<List<CategoryVO>> queryCategory(int pageNum, int pageCount, boolean withProblemCount) {
        Page<Category> problems = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> categoryMapper.selectCateGory());

        PageData<List<CategoryVO>> pageData = new PageData<>();
        List<Category> categories = problems.getResult();
        List<CategoryVO> categoryVOs = categories.stream().map(c -> {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(c, categoryVO);
            return categoryVO;
        }).collect(Collectors.toList());

        pageData.setContent(categoryVOs);
        pageData.setPageInfo(PageInfo.builder()
                .pageNum(problems.getPageNum())
                .pageSize(problems.getPageSize())
                .total(problems.getTotal())
                .build());

        if (withProblemCount) {

        }

        return pageData;
    }

    @Override
    public String getCategoryName(String symbol) {
        if (symbol.equals("all")) {
            return "全部";
        }
        return categoryMapper.selectCategoryNameBySymbol(symbol);
    }
}
