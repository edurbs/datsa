package com.github.edurbs.datsa.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public class PageWrapper<T> extends PageImpl<T> {

    private Pageable pageable;

    public PageWrapper(Page<T> page, Pageable pageable){
        super(page.getContent(), pageable, page.getNumberOfElements());
        this.pageable = pageable;
    }

    @Override
    public @NonNull Pageable getPageable() {
        return this.pageable;
    }


}
