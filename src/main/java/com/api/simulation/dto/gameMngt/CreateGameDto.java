package com.api.simulation.dto.gameMngt;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Create Game Request")
public class CreateGameDto {

    @Schema(example = "NBA Game")
    @NotBlank
    private String gameName;

    @Schema(example = "simulation")
    @NotBlank
    private String gameCategory;
}
