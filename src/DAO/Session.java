package DAO;

import java.util.Date;

public class Session {
    private Integer id;
    private String type;
    private Date time;
    private Integer duration;
    public Session() {
    }
    public Session(String type, Date time, Integer duration) {
        this.type = type;
        this.time = time;
        this.duration = duration;
    }
    public Session(Integer id, String type, Date time, Integer duration) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.duration = duration;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
}
