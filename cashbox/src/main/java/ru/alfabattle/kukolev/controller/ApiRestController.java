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

    private static final int EARTH_RADIUS = 6371_000;

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
    public BranchDistanceDto getDistances(@RequestParam("lat") BigDecimal lat, @RequestParam("lon") BigDecimal lon) {

        if (lat == null || lon == null) throw new IllegalArgumentException();

        var branches = repository.findAll();
        var branch = Collections.min(branches, Comparator.comparing(b -> calcDistance(lat, lon, b)));

        var result = mapperFacade.map(branch, BranchDistanceDto.class);
        result.setDistance(calcDistance(lat, lon, branch));
        System.out.println(result);
        return result;
    }

    private int calcDistance(BigDecimal lat, BigDecimal lon, Branch branch) {
        double lonRad = lon.doubleValue() * (PI / 180);
        double latRad = lat.doubleValue() * (PI / 180);

        double lonBrRad = branch.getLon().doubleValue() * (PI / 180);
        double latBrRad = branch.getLat().doubleValue() * (PI / 180);

        double sin1 = pow((0.5 * (latRad - latBrRad)), 2);
        double sin2 = pow((0.5 * (lonRad - lonBrRad)), 2);

        double dist = 2 * EARTH_RADIUS * asin(sqrt(sin1 + cos(latRad) * cos(latBrRad) * sin2));
        return (int) dist;
    }
}
