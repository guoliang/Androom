package se.alten.project.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(
        uniqueConstraints=
            @UniqueConstraint(columnNames={"firstName", "lastName"}))
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonIgnore
    @OneToMany(
            mappedBy="person",
            cascade=CascadeType.ALL)
    private List<Visit> visits;

    protected Person() { }

    public Person(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(String personFullName) {
        String[] nameElements = personFullName.split(" ");
        this.firstName = nameElements[0];
        this.lastName = nameElements[1];
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return String.format("Person [id=%d, firstName=%s, lastName=%s]",
                id, getFirstName(), getLastName());
    }
}
