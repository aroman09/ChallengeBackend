package com.nttdata.app.account.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Client {
    @JsonProperty("clientId")
    @NotBlank(message = "Debe enviar el id del cliente")
    private Long clientId;
    private String identification;
    private String name;

}
