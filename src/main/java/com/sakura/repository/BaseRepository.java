package com.sakura.repository;

import com.sakura.Entities.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends Serializable>
                extends PagingAndSortingRepository<E, ID> {

        @Override
        List<E> findAll();

        @Override
        Optional<E> findById(ID id);

}
