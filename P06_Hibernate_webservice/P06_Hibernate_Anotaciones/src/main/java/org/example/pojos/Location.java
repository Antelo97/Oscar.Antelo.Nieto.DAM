package org.example.pojos;

import com.google.gson.annotations.SerializedName;
import org.example.pojos.Character;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Locations")
public class Location implements Serializable {

    private int id_location; // Clave primaria

    @SerializedName("id")
    private int idApi_location;
    @SerializedName("name")
    private String name_location;
    @SerializedName("type")
    private String type_location;
    @SerializedName("dimension")
    private String dimension_location;
    @SerializedName("url")
    private String url_location;
    @SerializedName("created")
    private String created_location;

    private Set<Character> characters_location;

    public Location() {

    }

    public Location(int idApi_location, String name_location, String type_location, String dimension_location, String url_location, String created_location) {
        this.idApi_location = idApi_location;
        this.name_location = name_location;
        this.type_location = type_location;
        this.dimension_location = dimension_location;
        this.url_location = url_location;
        this.created_location = created_location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId_location() {
        return id_location;
    }

    public void setId_location(int id_location) {
        this.id_location = id_location;
    }

    @Column(name = "idApi_location")
    public int getIdApi_location() {
        return idApi_location;
    }

    public void setIdApi_location(int idApi_location) {
        this.idApi_location = idApi_location;
    }

    @Column(name = "name_location")
    public String getName_location() {
        return name_location;
    }

    public void setName_location(String name_location) {
        this.name_location = name_location;
    }

    @Column(name = "type_location")
    public String getType_location() {
        return type_location;
    }

    public void setType_location(String type_location) {
        this.type_location = type_location;
    }

    @Column(name = "dimension_location")
    public String getDimension_location() {
        return dimension_location;
    }

    public void setDimension_location(String dimension_location) {
        this.dimension_location = dimension_location;
    }

    @Column(name = "url_location")
    public String getUrl_location() {
        return url_location;
    }

    public void setUrl_location(String url_location) {
        this.url_location = url_location;
    }

    @Column(name = "created_location")
    public String getCreated_location() {
        return created_location;
    }

    public void setCreated_location(String created_location) {
        this.created_location = created_location;
    }

    @OneToMany(mappedBy = "location_character")
    public Set<Character> getCharacters_location() {
        return characters_location;
    }

    public void setCharacters_location(Set<Character> characters_location) {
        this.characters_location = characters_location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id_location=" + id_location +
                ", idApi_location=" + idApi_location +
                ", name_location='" + name_location + '\'' +
                ", type_location='" + type_location + '\'' +
                ", dimension_location='" + dimension_location + '\'' +
                ", url_location='" + url_location + '\'' +
                ", created_location='" + created_location + '\'' +
                ", characters_location=" + characters_location +
                '}';
    }
}
