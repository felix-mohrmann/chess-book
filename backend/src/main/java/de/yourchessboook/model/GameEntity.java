package de.yourchessboook.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "games")
@Getter
@Setter
public class GameEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String opening;

    @Column(length = 8000)
    private String moves;

    @Column
    private String winner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(opening, that.opening) && Objects.equals(moves, that.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opening, moves);
    }
}
