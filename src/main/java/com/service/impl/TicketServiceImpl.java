package com.service.impl;

import com.bean.LoginTicket;
import com.mapper.TicketMapper;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.Ticket;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Override
    public void addTicket(LoginTicket ticket) {
        ticketMapper.insertTicket(ticket);
    }
}
