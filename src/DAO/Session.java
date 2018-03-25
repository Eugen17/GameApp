package DAO;

import java.time.LocalDate;
import java.util.Date;

public class Session {

    private Integer id;
    private String type;
    private long duration;
    private long pauseTime;

    public Session() {
    }

    public Session(String type, Date time) {
        this.type = type;
    }

    public Session(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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

    public long getPauseTime(){
        return pauseTime;
    }
    
    public void StartPause() {
        pauseTime = new Date(LocalDate.now().toEpochDay()).getTime();
    }
    
    public void EndPause() {
        duration -= pauseTime;
    }
}
