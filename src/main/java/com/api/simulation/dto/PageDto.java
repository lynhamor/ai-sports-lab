package com.api.simulation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto<T> {

    private record Pagination( int page, int totalPage, int count, Long totalResult, int size){}

    private final Pagination pagination;
    private final Object content;
    private Object metadata;

    public PageDto(Page<? extends T> page) {
        this.pagination = new Pagination(
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getContent().size(),
                page.getTotalElements(),
                page.getSize()
        );
        this.content = page.getContent();
    }
}
