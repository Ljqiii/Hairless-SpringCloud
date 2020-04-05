package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.vo.CategoryVO;
import com.ljqiii.hairlesscommon.vo.PageData;

import java.util.List;

public interface CategoryService {
    PageData<List<CategoryVO>> queryCategory(int pageNum, int pageCount, boolean withProblemCount);

    String getCategoryName(String symbol);
}
