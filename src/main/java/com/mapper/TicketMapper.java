package com.mapper;

import com.bean.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketMapper {
    void insertTicket(LoginTicket ticket);

    LoginTicket seletByTicket(String ticket);

    void deleteByTicket(String ticket);
}
