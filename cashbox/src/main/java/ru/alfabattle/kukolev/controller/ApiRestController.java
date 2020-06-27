package ru.alfabattle.kukolev.controller;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alfabattle.kukolev.domain.Branch;
import ru.alfabattle.kukolev.dto.*;
import ru.alfabattle.kukolev.exception.EntityNotFoundException;
import ru.alfabattle.kukolev.repository.BranchRepository;

import java.math.BigDecimal;
import java.util.*;

import static java.lang.Math.*;

@RestController
public class ApiRestController {

    @Autowired
    private BranchRepository repository;
    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping("/branches/{id}")
    public BranchDto getById(@PathVariable Long id) {
        var branch = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapperFacade.map(branch, BranchDto.class);
    }

    @GetMapping("/branches")
    public BranchDistanceDto getById(@RequestParam("lat") BigDecimal lat, @RequestParam("lat") BigDecimal lon) {

        if (lat == null || lon == null) throw new IllegalArgumentException();

        var branches = repository.findAll();
        Branch branch = Collections.min(branches, Comparator.comparing(b -> calcDistance(lat, lon, b)));

        var result = mapperFacade.map(branch, BranchDistanceDto.class);
        result.setDistance(BigDecimal.valueOf(calcDistance(lat, lon, branch)));
        return result;
    }

    private double calcDistance(BigDecimal lat, BigDecimal lon, Branch branch) {
        return sqrt(pow(branch.getLat().doubleValue() - lat.doubleValue(), 2) +
                pow(branch.getLon().doubleValue() - lon.doubleValue(), 2));
    }
}
