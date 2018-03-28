package DAO;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Session {

    private UUID id;
    private String type;
    private Long duration;
    private Long pauseTime;

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Session(){
    }
    
    public Session(String type) {
        this.type = type;
        this.id = UUID.randomUUID();
    }
    
    public Session(UUID id, String type, Long duration){
        this.id = id;
        this.type = type;
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPauseTime(){
        return pauseTime;
    }
    
    public void StartPause() {
        pauseTime = new Date(LocalDate.now().toEpochDay()).getTime();
    }
    
    public void EndPause() {
        duration -= pauseTime;
    }
}
