package com.company.form;


import java.util.Date;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommonForm {

    @Min(1)
    private Integer page;

    @Min(1)
    private Integer size;

    private String sortField;

    @Pattern(regexp = "DESC|ASC|desc|asc")
    private String order;

    private String q;

    public String getQ() {
        if (StringUtils.isEmpty(q)) return null;
        return q.trim();
    }

    public Integer getPage() {
        if (page == null) return 1;
        return page;
    }

    public Integer getSize() {
        if (size == null) return 10;
        return size;
    }

    public Integer getLimit() {
        return getSize();
    }

    public Integer getOffset() {
        return (getPage() - 1) * getSize();
    }

    public String getSortField() {
        if (StringUtils.isEmpty(sortField)) return "id";
        return sortField;
    }

    public String getOrder() {
        if (StringUtils.isEmpty(order)) return "DESC";
        return order.toUpperCase();
    }
}

