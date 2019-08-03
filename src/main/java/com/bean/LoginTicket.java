package com.bean;


import java.util.Date;

public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private String ticket;
    private int status;
    public LoginTicket() {
    }

    public LoginTicket(int id, int userId, Date expired, String ticket, int status) {
        this.id = id;
        this.userId = userId;
        this.expired = expired;
        this.ticket = ticket;
        this.status = status;
    }


    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", expired=" + expired +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
