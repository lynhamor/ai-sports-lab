package com.api.simulation.dto.gameMngt;

import com.api.simulation.common.SortField;
import com.api.simulation.dto.BaseFilterDto;

public class GameFilterDto extends BaseFilterDto {

    @SortField(property = "game_name", nullsLast = true)
    private String sortOfGameName;
}
