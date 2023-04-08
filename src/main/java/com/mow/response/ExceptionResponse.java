package com.mow.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    private String response;
    private int status;
    private String error;
    private String endpoint;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date timestamp;
    public String stringify() {
        return String.format(
                "{\"response\":\"%s\",\"status\":\"%s\",\"error\":\"%s\",\"endpoint\":\"%s\",\"timestamp\":\"%s\"}",
                this.getResponse(),
                this.getStatus(),
                this.getError(),
                this.getEndpoint(),
                this.getTimestamp()
        );
    }

}
