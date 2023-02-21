package org.example.pojos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CharToEp")
public class CharToEp implements Serializable {

    private int id_charToEp; // CLave primaria
    private int id_character; // Clave foránea (Tabla Characters)
    private int id_episode; // Clave foránea (Tabla Episodes)

    private Character character;
    private Episode episode;

    public CharToEp() {

    }

    public CharToEp(int id_character, int id_episode) {
        this.id_character = id_character;
        this.id_episode = id_episode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId_charToEp() {
        return id_charToEp;
    }

    public void setId_charToEp(int id_charToEp) {
        this.id_charToEp = id_charToEp;
    }

    public int getId_character() {
        return id_character;
    }

    public void setId_character(int id_character) {
        this.id_character = id_character;
    }

    public int getId_episode() {
        return id_episode;
    }

    public void setId_episode(int id_episode) {
        this.id_episode = id_episode;
    }

    @ManyToOne()
    @JoinColumn(name = "id_character", insertable = false, updatable = false)
    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @ManyToOne()
    @JoinColumn(name = "id_episode", insertable = false, updatable = false)
    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public String toString() {
        return "CharToEp{" +
                "id_charToEp=" + id_charToEp +
                ", id_character=" + id_character +
                ", id_episode=" + id_episode +
                ", character=" + character +
                ", episode=" + episode +
                '}';
    }
}
