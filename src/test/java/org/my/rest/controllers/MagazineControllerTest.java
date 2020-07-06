package org.my.rest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.my.rest.models.Book;
import org.my.rest.models.Magazine;
import org.my.rest.serveces.MagazineService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MagazineControllerTest {

    @Mock
    private MagazineService magazineService;

    @InjectMocks
    private MagazineController magazineController;

    private Map<Long, Magazine> magazineMap;

    @BeforeEach
    void setUp() {

        this.magazineMap= new HashMap<>();
        magazineMap.put(1L, new Magazine(1L, "VOG", "sex"));
        magazineMap.put(2L, new Magazine(2L, "Forbes", "business"));
        magazineMap.put(3L, new Magazine(3L, "Fortune", "business"));
        magazineMap.put(4L, new Magazine(4L, "Cosmopolitan", "sex"));
    }

    @Test
    void getAll() {
        when(magazineService.getAll()).thenReturn(new ArrayList<>(magazineMap.values()));
        List<Magazine> result = magazineController.getAll(null, null);
        verify(magazineService).getAll();
        assertEquals(4, result.size());
    }

    @Test
    void getAllCaseTitleNotNull() {
        when(magazineService.getAll()).thenReturn(new ArrayList<>(magazineMap.values()));
        List<Magazine> result = magazineController.getAll("VOG", null);

        verify(magazineService).getAll();
        assertEquals(1, result.size());
        assertEquals("VOG", result.get(0).getTitle());
    }

    @Test
    void getAllCaseGenreNotNull() {
        when(magazineService.getAll()).thenReturn(new ArrayList<>(magazineMap.values()));
        List<Magazine> result = magazineController.getAll(null, "sex");
        verify(magazineService).getAll();
        assertEquals(2, result.size());
        assertEquals("sex", result.get(0).getGenre());
    }

    @Test
    void getAllCaseInvalidGenre() {
        when(magazineService.getAll()).thenReturn(new ArrayList<>(magazineMap.values()));
        List<Magazine> result = magazineController.getAll(null, "camedy");
        verify(magazineService).getAll();
        assertEquals(0, result.size());
    }

    @Test
    void getAllCaseInvalidTitle() {
        when(magazineService.getAll()).thenReturn(new ArrayList<>(magazineMap.values()));
        List<Magazine> result = magazineController.getAll("LMK", null);
        verify(magazineService).getAll();
        assertEquals(0, result.size());
    }

    @Test
    void getAllCaseBothNotNull() {
        when(magazineService.getAll()).thenReturn(new ArrayList<>(magazineMap.values()));
        List<Magazine> result = magazineController.getAll("VOG", "sex");
        verify(magazineService).getAll();
        assertEquals(1, result.size());
        assertEquals("sex", result.get(0).getGenre());
        assertEquals("VOG", result.get(0).getTitle());
    }


    @Test
    void getOne() {
        Long testId = 2L;
        when(magazineService.getById(testId)).thenReturn(magazineMap.get(testId));
        Magazine result = magazineController.getOne(testId);

        verify(magazineService).getById(testId);
        assertEquals(new Long(testId), result.getId());
    }

    @Test
    void create() {

        Magazine testMagazine = new Magazine(null, "NNN", "sex");
        when(magazineService.addNew(testMagazine)).thenReturn(testMagazine);

        Magazine result = magazineController.create(testMagazine);

        verify(magazineService).addNew(testMagazine);
        assertNotNull(result);
    }

    @Test
    void deletOne() {
        Long testId = 1L;
        magazineController.deletOne(testId);
        verify(magazineService).deleteOne(testId);
    }

    @Test
    void modifyOne() {
        Magazine modifiedMagazine = new Magazine(10L, "VOG", "sex");
        Magazine legacyMagazine = new Magazine(10L, "Vasya", "busseness");
        when(magazineService.getById(modifiedMagazine.getId())).thenReturn(legacyMagazine);

        Magazine result = magazineController.modifyOne(modifiedMagazine.getId(), modifiedMagazine);
        verify(magazineService).getById(modifiedMagazine.getId());
        verify(magazineService).addNew(modifiedMagazine);
        assertEquals(modifiedMagazine, result);
    }

}