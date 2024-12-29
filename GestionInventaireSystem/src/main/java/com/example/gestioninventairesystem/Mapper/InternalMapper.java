package com.example.gestioninventairesystem.Mapper;

import java.util.List;

public interface InternalMapper<D, M> {

    D toDto(M model);

    M toModel(D dto);

    List<D> toDtos(List<M> models);

    List<M> toModels(List<D> dtos);

}
