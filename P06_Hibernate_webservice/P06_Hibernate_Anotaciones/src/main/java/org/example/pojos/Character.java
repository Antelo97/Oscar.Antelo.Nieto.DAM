package org.example.pojos;

import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Characters")
public class Character implements Serializable {

    private int id_character; // Clave primaria
    private int id_origin; // Clave foránea (Tabla Locations)
    private int id_location; // Clave foránea (Tabla Locations)

    @SerializedName("id")
    private int idApi_character;
    @SerializedName("name")
    private String name_character;
    @SerializedName("status")
    private String status_character;
    @SerializedName("species")
    private String species_character;
    @SerializedName("type")
    private String type_character;
    @SerializedName("gender")
    private String gender_character;
    @SerializedName("url")
    private String url_character;
    @SerializedName("created")
    private String created_character;

    private Location origin_character;
    private Location location_character;
    private Set<CharToEp> episodes_character;

    public Character() {

    }

    public Character(int idApi_character, String name_character, String status_character, String species_character, String type_character, String gender_character, String url_character, String created_character) {
        this.idApi_character = idApi_character;
        this.name_character = name_character;
        this.status_character = status_character;
        this.species_character = species_character;
        this.type_character = type_character;
        this.gender_character = gender_character;
        this.url_character = url_character;
        this.created_character = created_character;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId_character() {
        return id_character;
    }

    public void setId_character(int id_character) {
        this.id_character = id_character;
    }

    @Column(nullable = true)
    public int getId_origin() {
        return id_origin;
    }

    public void setId_origin(Integer id_origin) {
        this.id_origin = id_origin;
    }

    @Column(nullable = true)
    public int getId_location() {
        return id_location;
    }

    public void setId_location(Integer id_location) {
        this.id_location = id_location;
    }

    @Column(name = "idApi_character")
    public int getIdApi_character() {
        return idApi_character;
    }

    public void setIdApi_character(int idApi_character) {
        this.idApi_character = idApi_character;
    }

    @Column(name = "name_character")
    public String getName_character() {
        return name_character;
    }

    public void setName_character(String name_character) {
        this.name_character = name_character;
    }

    @Column(name = "status_character")
    public String getStatus_character() {
        return status_character;
    }

    public void setStatus_character(String status_character) {
        this.status_character = status_character;
    }

    @Column(name = "species_character")
    public String getSpecies_character() {
        return species_character;
    }

    public void setSpecies_character(String species_character) {
        this.species_character = species_character;
    }

    @Column(name = "type_character")
    public String getType_character() {
        return type_character;
    }

    public void setType_character(String type_character) {
        this.type_character = type_character;
    }

    @Column(name = "gender_character")
    public String getGender_character() {
        return gender_character;
    }

    public void setGender_character(String gender_character) {
        this.gender_character = gender_character;
    }

    @Column(name = "url_character")
    public String getUrl_character() {
        return url_character;
    }

    public void setUrl_character(String url_character) {
        this.url_character = url_character;
    }

    @Column(name = "created_character")
    public String getCreated_character() {
        return created_character;
    }

    public void setCreated_character(String created_character) {
        this.created_character = created_character;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_origin", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public Location getOrigin_character() {
        return origin_character;
    }

    public void setOrigin_character(Location origin_character) {
        this.origin_character = origin_character;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_location", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public Location getLocation_character() {
        return location_character;
    }

    public void setLocation_character(Location location_character) {
        this.location_character = location_character;
    }

    @OneToMany(mappedBy = "character")
    public Set<CharToEp> getEpisodes_character() {
        return episodes_character;
    }

    public void setEpisodes_character(Set<CharToEp> episodes_character) {
        this.episodes_character = episodes_character;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id_character=" + id_character +
                ", id_location=" + id_location +
                ", id_origin=" + id_origin +
                ", idApi_character=" + idApi_character +
                ", name_character='" + name_character + '\'' +
                ", status_character='" + status_character + '\'' +
                ", species_character='" + species_character + '\'' +
                ", type_character='" + type_character + '\'' +
                ", gender_character='" + gender_character + '\'' +
                ", url_character='" + url_character + '\'' +
                ", created_character='" + created_character + '\'' +
                ", origin_character=" + origin_character +
                ", location_character=" + location_character +
                ", episodes_character=" + episodes_character +
                '}';
    }
}
