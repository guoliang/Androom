package se.alten.project.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Visit {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @JsonProperty("check_in")
    private Date checkIn;

    @JsonProperty("check_out")
    private Date checkOut;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="personId")
    private Person person;

    protected Visit() {}

    public Visit(Date checkIn, Person person) {
        this.checkIn = checkIn;
        this.person = person;
    }

    public Long getId() {
        return id == null ? 0 : id;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
       this.person = person;
    }

}
