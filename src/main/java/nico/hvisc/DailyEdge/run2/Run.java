package nico.hvisc.DailyEdge.run2;

import jakarta.persistence.*;

import java.sql.Timestamp;
@Entity
@Table(name = "Run")
public class Run {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String title;
    private Integer kilometers;
    private Timestamp created_at;
    public Run() {}

    public Run(int id, String title, int kilometers, Timestamp created_at) {
        this.id = id;
        this.title = title;
        this.kilometers = kilometers;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", kilometers=" + kilometers +
                ", created_at=" + created_at +
                '}';
    }
}
