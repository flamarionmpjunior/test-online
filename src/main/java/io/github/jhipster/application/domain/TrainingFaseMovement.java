package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A TrainingFaseMovement.
 */
@Entity
@Table(name = "training_fase_movement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrainingFaseMovement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private Instant name;

    @ManyToOne
    @JsonIgnoreProperties("trainingFaseMovements")
    private TrainingFase name;

    @ManyToOne
    @JsonIgnoreProperties("trainingFaseMovements")
    private Movement name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getName() {
        return name;
    }

    public TrainingFaseMovement name(Instant name) {
        this.name = name;
        return this;
    }

    public void setName(Instant name) {
        this.name = name;
    }

    public TrainingFase getName() {
        return name;
    }

    public TrainingFaseMovement name(TrainingFase trainingFase) {
        this.name = trainingFase;
        return this;
    }

    public void setName(TrainingFase trainingFase) {
        this.name = trainingFase;
    }

    public Movement getName() {
        return name;
    }

    public TrainingFaseMovement name(Movement movement) {
        this.name = movement;
        return this;
    }

    public void setName(Movement movement) {
        this.name = movement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainingFaseMovement trainingFaseMovement = (TrainingFaseMovement) o;
        if (trainingFaseMovement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainingFaseMovement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainingFaseMovement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
