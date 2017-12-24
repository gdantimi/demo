package com.gdantimi.demo.service;

import com.gdantimi.demo.model.entity.Sector;
import com.gdantimi.demo.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.collections.IteratorUtils.toList;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    public Sector save(Sector sector){
        return sectorRepository.save(sector);
    }

    public Sector find(Long id){
        return sectorRepository.findOne(id);
    }

    public List<Sector> findAll(){
        return sectorRepository.findAllByParentSectorIdIsNull();
    }

    public List<Sector> findAllById(List<Long> sectorsIds) {
        Iterable<Sector> sectors = sectorRepository.findAll(sectorsIds);
        return toList(sectors.iterator());
    }

    public boolean exists(Long sectorId) {
        return sectorRepository.exists(sectorId);
    }
}
