package org.example.saessakroutine.domain.board.persistence.dto.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusResponse{
    private String status;
    private String message;
}
