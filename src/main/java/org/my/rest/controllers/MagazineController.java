package org.my.rest.controllers;

import org.my.rest.models.Magazine;
import org.my.rest.serveces.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MagazineController {

    @Autowired
    private MagazineService magazineService;

    @GetMapping(value = "/magazines", produces = {"application/json", "application/xml"})
    public List<Magazine> getAll(@RequestParam(required = false) String title, @RequestParam(required = false) String genre) {

        if (title != null && genre == null) {
            return magazineService.getAll().stream()
                    .filter(magazine -> magazine.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        } else if (title == null && genre != null) {
            return magazineService.getAll().stream()
                    .filter(magazine -> magazine.getGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());
        } else if (title != null && genre != null) {
            return magazineService.getAll().stream()
                    .filter(magazine -> magazine.getTitle().equalsIgnoreCase(title))
                    .filter(magazine -> magazine.getGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());
        } else {
            return magazineService.getAll();
        }
    }

    @GetMapping(value = "/magazines/{id}", produces = {"application/json", "application/xml"})
    public Magazine getOne(@PathVariable Long id) {
        return magazineService.getById(id);
    }

    @PostMapping(value = "/magazines",
                 consumes = {"application/json", "application/xml"},
                 produces = {"application/json", "application/xml"})
    public Magazine create(@RequestBody Magazine magazine) {
        return magazineService.addNew(magazine);
    }


    @DeleteMapping("/magazines/{id}")
    public void deletOne(@PathVariable Long id) {
        magazineService.deleteOne(id);
    }

    @PutMapping(value = "/magazines/{id}",
                consumes = {"application/json", "application/xml"},
                produces = {"application/json", "application/xml"})
    public Magazine modifyOne(@PathVariable Long id, @RequestBody Magazine magazine) {
        Magazine magazine1 = magazineService.getById(id);
        magazine1.setTitle(magazine.getTitle());
        magazine1.setGenre(magazine.getGenre());
        return magazineService.overrideMagazine(magazine1);



    }

}
