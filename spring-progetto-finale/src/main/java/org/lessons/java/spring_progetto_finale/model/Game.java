package org.lessons.java.spring_progetto_finale.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The game name cannot be blank nor empty")
    private String name;

    @NotBlank(message = "The game description cannot be blank nor empty")
    @Size(max = 500, message = "The game description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "The game must have a developer")
    private String developer;

    @NotBlank(message = "The game must have a publisher")
    private String publisher;

    @Lob
    // @NotEmpty(message = "You must insert a cover image")
    private byte[] image;

    @NotNull(message = "The game must have a price")
    @Positive(message = "The price cannot be a negative number nor zero")
    private BigDecimal price;

    @ManyToMany()
    @JoinTable(name = "game_genre", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @NotEmpty(message = "You must select at least one genre")
    private List<Genre> genres;

    @ManyToMany()
    @JoinTable(name = "console_game", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "console_id"))
    @NotEmpty(message = "You must select at least one console")
    private List<Console> availableConsoles;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Genre> getGenres() {
        return this.genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Console> getAvailableConsoles() {
        return this.availableConsoles;
    }

    public void setAvailableConsoles(List<Console> availableConsoles) {
        this.availableConsoles = availableConsoles;
    }

}
