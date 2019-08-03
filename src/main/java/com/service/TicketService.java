package com.service;

import com.bean.LoginTicket;
import org.springframework.stereotype.Service;


public interface TicketService {
    void addTicket(LoginTicket ticket);
}
