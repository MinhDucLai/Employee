package com.company.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CommonListDTO<T> {
    @NonNull
    private List<T> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int count;
}
