package se.alten.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDto {

    @JsonProperty(value="check_in")
    private String checkIn;

    @JsonProperty(value="check_out")
    private String checkOut;

    @JsonProperty(value="person_id")
    private Long personId;

    @JsonProperty(value="person_fullname", required=false)
    private String personFullName;

    @JsonProperty(value="result", required=false)
    private ResultDto result;

    @JsonProperty(value="person_ids")
    private List<Long> personIds;

    //Jackson needs have an empty constructor
    public VisitDto() { }

    public VisitDto(Long personId, String personFullName, String checkIn, String checkOut) {
        this.personId = personId;
        this.personFullName = personFullName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public Long getPersonId() {
        return personId;
    }

    public List<Long> getPersonIds() {
        return personIds;
    }

    public ResultDto getResult() {
        return result;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setResult(ResultDto result) {
        this.result = result;
    }

    public void setPersonId(Long id) {
        personId = id;
    }
}
