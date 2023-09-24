package com.pet.commerce.core.module.base.ro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

import static com.pet.commerce.core.constants.Constants.DEFAULT_PAGE_SIZE;
import static com.pet.commerce.core.constants.Constants.MAX_PAGE_SIZE;
import static org.springframework.data.domain.Sort.NullHandling.NULLS_LAST;

/**
 * @author Will Wu
 * @since 2022-02-17
 */
@Getter
@Setter
public class PageRO implements Serializable {
    private static final long serialVersionUID = -2820854995116599643L;

    protected Integer page;

    protected Integer size;

    protected String sort;

    @JsonIgnore
    public Pageable getPageable() {
        if (page == null || page < 0) {
            this.page = 0;
        }
        if (size == null || size < 1) {
            this.size = DEFAULT_PAGE_SIZE;
        } else if (size > MAX_PAGE_SIZE) {
            this.size = MAX_PAGE_SIZE;
        }
        return org.springframework.data.domain.PageRequest.of(page, size, handleSortString(sort));
    }

    private Sort handleSortString(String sortStr) {
        Sort sort = null;
        if (StringUtils.isNotBlank(sortStr)) {
            String[] sorts = sortStr.split(",");
            if (sorts.length == 2) {
                sort = Sort.by(new Sort.Order(Sort.Direction.fromString(sorts[1].trim()), sorts[0].trim(), NULLS_LAST));
            } else if (sorts.length == 1) {
                sort = Sort.by(new Sort.Order(Sort.Direction.DESC, sorts[0].trim()));
            }
        }
        if (sort == null) {
            sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        }
        return sort;
    }

}
