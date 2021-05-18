package org.uresti.pozarreal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;
import org.uresti.pozarreal.model.Street;
import org.uresti.pozarreal.repository.HousesRepository;
import org.uresti.pozarreal.repository.RepresentativeRepository;
import org.uresti.pozarreal.repository.StreetRepository;

import java.util.*;

public class StreetServiceImplTests {

    @Test
    public void givenAnEmptyStreetList_whenGetStreets_thenEmptyListIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Mockito.when(streetRepository.findAll()).thenReturn(lista);

        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertTrue(streets.isEmpty());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreets_thenListWithTwoElementsIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Mockito.when(streetRepository.findAll()).thenReturn(lista);


        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertEquals(2, streets.size());
        Assertions.assertEquals("id1", streets.get(0).getId());
        Assertions.assertEquals("Street 1", streets.get(0).getName());
        Assertions.assertEquals("id2", streets.get(1).getId());
        Assertions.assertEquals("Street 2", streets.get(1).getName());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreetInfo1Element_thenInfoOfStreet1IsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = Mockito.mock(RepresentativeRepository.class);
        HousesRepository housesRepository = Mockito.mock(HousesRepository.class);

        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Representative representative = new Representative();

        representative.setId("id1");
        representative.setStreet("Street 1");
        representative.setName("Street 1");
        representative.setPhone("4441182149");
        representative.setAddress("Address 1");

        List<House> houses = new LinkedList<>();


        Mockito.when(streetRepository.findById("id1")).thenReturn(Optional.of(street1));
        Mockito.when(representativeRepository.findRepresentativeByStreet("id1")).thenReturn(representative);
        Mockito.when(housesRepository.findAllByStreet("id1")).thenReturn(houses);

        // When:
        StreetInfo streetInfo = streetService.getStreetInfo("id1");

        // Then:
        Assertions.assertEquals("id1", streetInfo.getId());
        Assertions.assertEquals("Street 1", streetInfo.getName());
        Assertions.assertEquals("id1", streetInfo.getRepresentative().getId());
        Assertions.assertEquals("Street 1", streetInfo.getRepresentative().getStreet());
        Assertions.assertEquals("Street 1", streetInfo.getRepresentative().getName());
        Assertions.assertEquals("4441182149", streetInfo.getRepresentative().getPhone());
        Assertions.assertEquals("Address 1", streetInfo.getRepresentative().getAddress());
        Assertions.assertTrue(houses.isEmpty());
    }

}
