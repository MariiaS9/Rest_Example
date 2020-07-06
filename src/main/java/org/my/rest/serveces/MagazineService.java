package org.my.rest.serveces;


import org.my.rest.models.Magazine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MagazineService {

    private final Map<Long, Magazine> magazineMap;

    public MagazineService() {
        this.magazineMap = new HashMap<>();
        magazineMap.put(1L, new Magazine(1L, "VOG", "sex"));
        magazineMap.put(2L, new Magazine(2L, "Forbes", "business"));
        magazineMap.put(3L, new Magazine(3L, "Fortune", "business"));
        magazineMap.put(4L, new Magazine(4L, "Cosmopolitan", "sex"));
    }

    public List<Magazine> getAll() {
        return new ArrayList<>(magazineMap.values());
    }

    public Magazine getById(Long id) {
        return magazineMap.get(id);
    }

    public Magazine deleteOne(Long id) {
        return magazineMap.remove(id);
    }

    public Magazine addNew(Magazine magazine) {
        long maxId = magazineMap.keySet()
                .stream()
                .mapToLong(v -> v)
                .max().getAsLong();
        maxId++;

        magazine.setId(maxId);
        magazineMap.put(magazine.getId(), magazine);
        return magazine;
    }

    public Magazine overrideMagazine(Magazine magazine){
        magazineMap.put(magazine.getId(), magazine);
        return magazineMap.get(magazine.getId());
    }
}
