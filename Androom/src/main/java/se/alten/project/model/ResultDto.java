package se.alten.project.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto {

    private final HttpStatus status;
    private final String message;
    private final String error;
    private final Object data;

    private ResultDto(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.data = builder.data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }

    public static class Builder {
        private HttpStatus status;
        private String message;
        private String error;
        private Object data;

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ResultDto build() {
            return new ResultDto(this);
        }
    }

}
