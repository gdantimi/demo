package com.gdantimi.demo.repository;

import com.gdantimi.demo.model.entity.Sector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends CrudRepository<Sector, Long> {

    List<Sector> findAllByParentSectorIdIsNull();

    List<Sector> findAllById(List<Long> sectorsIds);
}
