package com.ljqiii.dao;

import com.ljqiii.hairlesscommon.domain.Point;
import com.ljqiii.hairlesscommon.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface PointMapper {

    @Select("select count(point_event.point) from point, point_event where point.userid = #{id} and point.eventid = point_event.id")
    int countPointByUser(User user);

    @Insert("insert into point(userid,eventid) values (#{userid} ,#{eventid})")
    int insertPoint(Point point);

    @Delete("delete from point where id=#{id}")
    int deletePoint(Point point);

}
