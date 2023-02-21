package org.example.pojos;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Episodes")
public class Episode implements Serializable {

    private int id_episode; // Clave primaria

    @SerializedName("id")
    private int idApi_episode;
    @SerializedName("name")
    private String name_episode;
    @SerializedName("air_date")
    private String air_date_episode;
    @SerializedName("episode")
    private String episode_episode;
    @SerializedName("url")
    private String url_episode;
    @SerializedName("created")
    private String created_episode;

    private Set<CharToEp> characters_episode;

    public Episode() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId_episode() {
        return id_episode;
    }

    public void setId_episode(int id_episode) {
        this.id_episode = id_episode;
    }

    @Column(name = "idApi_epidose")
    public int getIdApi_episode() {
        return idApi_episode;
    }

    public void setIdApi_episode(int idApi_episode) {
        this.idApi_episode = idApi_episode;
    }

    @Column(name = "name_epidose")
    public String getName_episode() {
        return name_episode;
    }

    public void setName_episode(String name_episode) {
        this.name_episode = name_episode;
    }

    @Column(name = "air_date_episode")
    public String getAir_date_episode() {
        return air_date_episode;
    }

    public void setAir_date_episode(String air_date_episode) {
        this.air_date_episode = air_date_episode;
    }

    @Column(name = "episode_episode")
    public String getEpisode_episode() {
        return episode_episode;
    }

    public void setEpisode_episode(String episode_episode) {
        this.episode_episode = episode_episode;
    }

    @Column(name = "url_episode")
    public String getUrl_episode() {
        return url_episode;
    }

    public void setUrl_episode(String url_episode) {
        this.url_episode = url_episode;
    }

    @Column(name = "created_epidose")
    public String getCreated_episode() {
        return created_episode;
    }

    public void setCreated_episode(String created_episode) {
        this.created_episode = created_episode;
    }

    @OneToMany(mappedBy = "episode")
    public Set<CharToEp> getCharacters_episode() {
        return characters_episode;
    }

    public void setCharacters_episode(Set<CharToEp> characters_episode) {
        this.characters_episode = characters_episode;
    }
}
