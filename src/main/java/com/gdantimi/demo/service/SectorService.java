package com.gdantimi.demo.service;

import com.gdantimi.demo.model.Sector;
import com.gdantimi.demo.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
