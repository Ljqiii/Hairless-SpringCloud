package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category")
    List<Category> selectCateGory();

    @Insert("insert into category(id,problemid,categoryid)values(#{id},#{problemid},#{categoryid})")
    int insertCategory(Category category);

    @Select("select name from category where symbol=#{symbol}")
    String selectCategoryNameBySymbol(@Param("symbol") String symbol);

    int insertProblemCategory(@Param("problem") Problem problem, @Param("categories")  List<Category> categories);
}
