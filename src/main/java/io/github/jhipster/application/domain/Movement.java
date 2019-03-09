package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "movement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abreviation")
    private String abreviation;

    @Column(name = "description")
    private String description;

    @Column(name = "web_link")
    private String webLink;

    @ManyToOne
    @JsonIgnoreProperties("movements")
    private MovementCategory name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Movement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public Movement abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getDescription() {
        return description;
    }

    public Movement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebLink() {
        return webLink;
    }

    public Movement webLink(String webLink) {
        this.webLink = webLink;
        return this;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public MovementCategory getName() {
        return name;
    }

    public Movement name(MovementCategory movementCategory) {
        this.name = movementCategory;
        return this;
    }

    public void setName(MovementCategory movementCategory) {
        this.name = movementCategory;
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
        Movement movement = (Movement) o;
        if (movement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Movement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            ", description='" + getDescription() + "'" +
            ", webLink='" + getWebLink() + "'" +
            "}";
    }
}
