package my_project.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my_project.dto.ConcertDTO;
import my_project.dto.MusicianDTO;
import my_project.model.Concert;
import my_project.model.Musician;
import my_project.repository.ConcertRepository;
import my_project.repository.MusicianRepository; 

@RestController
@RequestMapping("/api/concerts")

public class ConcertController {
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private MusicianRepository musicianRepository;


    @GetMapping
    @Transactional
    public List<ConcertDTO> getAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        List<ConcertDTO> concertDTOs = new ArrayList<>();

        for (Concert concert : concerts) {
            ConcertDTO concertDTO = convertToConcertDTO(concert);
            concertDTOs.add(concertDTO);
        }

        return concertDTOs;
    }
    private ConcertDTO convertToConcertDTO(Concert concert) {
        ConcertDTO concertDTO = new ConcertDTO();
        concertDTO.setId(concert.getId());
        concertDTO.setName(concert.getName());
        concertDTO.setLocation(concert.getLocation());
        concertDTO.setTicketPriceS(concert.getTicketPriceS());
        concertDTO.setTicketPriceV(concert.getTicketPriceV());
        concertDTO.setDate(concert.getDate());
        // Заполните остальные поля в ConcertDTO

        return concertDTO;
    }
/* 
    @GetMapping
    public List<ConcertDTO> getAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        List<ConcertDTO> concertDTOs = new ArrayList<>();

        for (Concert concert : concerts) {
            ConcertDTO concertDTO = new ConcertDTO();
            concertDTO.setId(concert.getId());
            concertDTO.setName(concert.getName());
            concertDTO.setLocation(concert.getLocation());
            concertDTO.setTicketPriceS(concert.getTicketPriceS());
            concertDTO.setTicketPriceV(concert.getTicketPriceV());
            concertDTO.setDate(concert.getDate());

            // Преобразование списка музыкантов в список MusicianDTO
            Set<MusicianDTO> musicianDTOs = concert.getMusicians().stream()
                    .map(musician -> {
                        MusicianDTO musicianDTO = new MusicianDTO();
                        musicianDTO.setId(musician.getId());
                        musicianDTO.setFirstName(musician.getFirstName());
                        musicianDTO.setLastName(musician.getLastName());
                        musicianDTO.setBio(musician.getBio());
                        musicianDTO.setMusicStyle(musician.getMusicStyle());
                        return musicianDTO;
                    })
                    .collect(Collectors.toSet());

            concertDTO.setMusicians(musicianDTOs);
            concertDTOs.add(concertDTO);
        }

        return concertDTOs;
    }
    */
    /* 
    @GetMapping
    public List<Concert> getAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        for (Concert concert : concerts) {
            Hibernate.initialize(concert.getMusicians());
        }
        return concerts;
    }
     
    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        return concertRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Concert createConcert(@RequestBody Concert concert) {
        return concertRepository.save(concert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concert> updateConcert(@PathVariable Long id, @RequestBody Concert concert) {
        return concertRepository.findById(id)
                .map(existingConcert -> {
                    existingConcert.setName(concert.getName());
                    existingConcert.setDate(concert.getDate());
                    existingConcert.setLocation(concert.getLocation());
                    existingConcert.setTicketPriceS(concert.getTicketPriceS());
                    existingConcert.setTicketPriceV(concert.getTicketPriceV());
                    existingConcert.setMusicians(concert.getMusicians());
                    return ResponseEntity.ok(concertRepository.save(existingConcert));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Concert> deleteConcert(@PathVariable Long id) {
        return concertRepository.findById(id)
                .map(existingConcert -> {
                    concertRepository.delete(existingConcert);
                    return ResponseEntity.ok(existingConcert);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{concertId}/musicians/{musicianId}")
    public ResponseEntity<Concert> addMusicianToConcert(@PathVariable Long concertId,
                                                        @PathVariable Long musicianId) {
        Optional<Concert> optionalConcert = concertRepository.findById(concertId);
        Optional<Musician> optionalMusician = musicianRepository.findById(musicianId);
        if (optionalConcert.isPresent() && optionalMusician.isPresent()) {
            Concert concert = optionalConcert.get();
            Musician musician = optionalMusician.get();
            concert.getMusicians().add(musician);
            return ResponseEntity.ok(concertRepository.save(concert));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{concertId}/musicians/{musicianId}")
    public ResponseEntity<Concert> removeMusicianFromConcert(@PathVariable Long concertId,
                                                             @PathVariable Long musicianId) {
        Optional<Concert> optionalConcert = concertRepository.findById(concertId);
        Optional<Musician> optionalMusician = musicianRepository.findById(musicianId);
        if (optionalConcert.isPresent() && optionalMusician.isPresent()) {
            Concert concert = optionalConcert.get();
            Musician musician = optionalMusician.get();
            concert.getMusicians().remove(musician);
            return ResponseEntity.ok(concertRepository.save(concert));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    */
}
