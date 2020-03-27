package com.ljqiii.hairlessnotification.dao;

import com.ljqiii.hairlesscommon.domain.Notification;
import com.ljqiii.hairlesscommon.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface NotificationMapper {

    @Select("select * from notification where userid=#{id} and ispush=0")
    List<Notification> selectUnPushedNotification(User user);

    @Select("select * from notification where userid=#{id} and isread=0")
    List<Notification> selectUnReadNotification(User user);

    @Select("select  count(*) from notification where userid=#{userid} and isread=0")
    int selectUnReadNotificationCount(int userid);

}
