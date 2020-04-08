package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Category;
import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Select("select  * from favoritefolder where  username=#{username}")
    List<FavoriteFolder> selectFavoriteFolder(@Param("username") String username);

    @Insert("insert into favoritefolder(username,name,ispublic)values (#{userName} ,#{name} ,#{isPublic} )")
    int insertFavoriteFolder(FavoriteFolder favoriteFolder);

    @Delete("delete from favoritefolder where id=#{id} ")
    int deleteFavoriteFolder(FavoriteFolder favoriteFolder);

    @Select("select * from favoritefolder where username=#{username} and id=#{id} limit 1")
    FavoriteFolder selectFavoriteFolderByUserNameAnd(@Param("username") String username, @Param("id") int id);

    @Insert("insert into favorite(username,problemid,favoritefolderid)values(#{username} ,#{problem.id} ,#{folder.id})")
    int insertFavoriteProblem(@Param("username") String username, @Param("folder") FavoriteFolder favoriteFolder, @Param("problem") Problem problem);

    @Delete("delete from favorite where problemid=#{problemid} and username=#{username} ")
    int deleteFavoriteProblem(@Param("username") String username, @Param("problemid") Problem problemid);

    @Select("select favorite.favoritefolderid from favorite where username=#{username} and problemid=#{problem.id} ")
    List<Integer> selectFavoriteFoldersByUserNameAndProblem(@Param("username") String username, @Param("problem") Problem problem);

    int batchInsertFavoriteProblem(@Param("username") String username, @Param("problem") Problem problem, @Param("favoriteFolderIds") ArrayList<Integer> favoriteFolderIds);

    int batchDeleteFavoriteProblem(@Param("username") String username, @Param("problem") Problem problem, @Param("favoriteFolderIds") ArrayList<Integer> favoriteFolderIds);

    @Select("select favoritefolderid from favorite where username=#{username} and problemid=#{problemid}")
    List<Integer> selectProblemFavoriteFolderList(@Param("username") String username, @Param("problemid") Integer problemid);

    @Select("select count(*) from favorite where problemid=#{problemid}")
    int selectCountFavoriteProblem(@Param("problemid") int problemid);

    @Select("select count(*) from favorite where problemid=#{problemid} and username=#{username}")
    int selectCountUserNameProblem(@Param("problemid") int problemid, @Param("username") String username);

}
