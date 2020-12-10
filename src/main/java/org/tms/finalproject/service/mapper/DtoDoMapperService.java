package org.tms.finalproject.service.mapper;

public interface DtoDoMapperService<T, E> {
    public T buildDo(E dto);
}
