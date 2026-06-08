package com.api.simulation.dto.gameMngt;

import com.api.simulation.common.SortField;
import com.api.simulation.dto.BaseFilterDto;

public class GameRuleFilterDto extends BaseFilterDto {

    @SortField(property = "game_id", nullsLast = true)
    private String sortByGameId;

    @SortField(property = "rule_key", nullsLast = true)
    private String sortByRuleKey;
}
