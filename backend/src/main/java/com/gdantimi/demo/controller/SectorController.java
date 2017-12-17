package com.gdantimi.demo.controller;

import com.gdantimi.demo.model.entity.Sector;
import com.gdantimi.demo.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @PostMapping
    public ResponseEntity<Sector> save(@RequestBody Sector sector){
        sector = sectorService.save(sector);
        return new ResponseEntity<>(sector, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sector> find(@PathVariable Long id){
        Sector sector = sectorService.find(id);
        if(sector == null){
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(sector, OK);
    }

    @GetMapping
    public ResponseEntity<List<Sector>> findAll(){
        List<Sector> sector = sectorService.findAll();
        return new ResponseEntity<>(sector, OK);
    }

    @GetMapping("/findAllByIds")
    public ResponseEntity<List<Sector>> findAllById(@RequestParam List<Long> sectorIds){
        List<Sector> sector = sectorService.findAllById(sectorIds);
        return new ResponseEntity<>(sector, OK);
    }
}
