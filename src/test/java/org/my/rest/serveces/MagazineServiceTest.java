package org.my.rest.serveces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.my.rest.models.Book;
import org.my.rest.models.Magazine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MagazineServiceTest {
    private MagazineService magazineService;

    @BeforeEach
    void setUp() {
        magazineService = new MagazineService();
    }

    @Test
    void getAll() {
        List<Magazine> result = magazineService.getAll();
        assertEquals(4, result.size());
    }


    @Test
    void getById() {
        Magazine result = magazineService.getById(2L);
        assertNotNull(result);
        assertEquals(2L, result.getId());
    }

    @Test
    void deleteOne() {
        Magazine result = magazineService.deleteOne(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(3, magazineService.getAll().size());
    }


    @Test
    void addNew() {
        Magazine testMagazine = new Magazine(null, "Discavery", "business");
        Magazine result = magazineService.addNew(testMagazine);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals(5, magazineService.getAll().size());
    }
}