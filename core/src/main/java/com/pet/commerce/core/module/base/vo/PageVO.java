package com.pet.commerce.core.module.base.vo;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Will Wu
 * @since 2022-02-17
 */
@Getter
@Setter
public class PageVO<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 3614737725595228546L;

    private Integer page;

    private Integer size;

    private Boolean first;

    private Boolean last;

    private Integer numberOfElements;

    private Integer totalPages;

    private Long totalElements;

    private List<T> content;


    /**
     * convert page to page vo
     *
     * @param page    spring page object
     * @param content page content
     * @param <T>     data type
     * @return page data
     */
    public static <T extends Serializable> PageVO<T> convert(Page<?> page, List<T> content) {
        PageVO<T> vo = new PageVO<>();
        vo.setPage(page.getNumber());
        vo.setTotalPages(page.getTotalPages());
        vo.setSize(page.getSize());
        vo.setNumberOfElements(page.getNumberOfElements());
        vo.setFirst(page.isFirst());
        vo.setLast(page.isLast());
        vo.setTotalPages(page.getTotalPages());
        vo.setTotalElements(page.getTotalElements());
        vo.setContent(content);
        return vo;
    }

    /**
     * convert page to page vo, mapper转换函数
     */
    public static <R, T extends Serializable> PageVO<T> convert(Page<R> page, Function<? super R, ? extends T> mapper) {
        PageVO<T> vo = new PageVO<>();
        vo.setPage(page.getNumber());
        vo.setTotalPages(page.getTotalPages());
        vo.setSize(page.getSize());
        vo.setNumberOfElements(page.getNumberOfElements());
        vo.setFirst(page.isFirst());
        vo.setLast(page.isLast());
        vo.setTotalPages(page.getTotalPages());
        vo.setTotalElements(page.getTotalElements());
        List<T> collect = page.getContent().stream().map(mapper).collect(Collectors.toList());
        vo.setContent(collect);
        return vo;
    }

    /**
     * JPA 查询page转换
     *
     * @param query    jpaQuery查询对象
     * @param pageable 分页
     * @param mapper   结果集转换函数
     * @param <R>      结果参数类型
     * @param <T>      返回值类型
     * @return 页面内容
     */
    public static <R, T extends Serializable> PageVO<T> convert(JPAQuery<R> query, Pageable pageable, Function<? super R, ? extends T> mapper) {
        query.limit(pageable.getPageSize());
        query.offset(pageable.getOffset());

        List<R> fetch = query.fetch();
        long total = query.fetchCount();
        List<T> content = fetch.stream().map(mapper).collect(Collectors.toList());
        PageImpl<T> pageResult = new PageImpl<>(content, pageable, total);
        return PageVO.convert(pageResult, content);
    }

}
