package com.organizze.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizze.infra.TokenService;
import com.organizze.model.status.Status;
import com.organizze.model.status.StatusRequestDTO;
import com.organizze.model.status.StatusResponseDTO;
import com.organizze.repositories.StatusRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping()
    public  ResponseEntity<List<Status>> getAllStatus() {

        List<Status> statusList = statusRepository.findAll();
        

        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }
    

    @PostMapping
    public ResponseEntity createStatus(@RequestBody @Valid StatusRequestDTO data) {
        Status novoStatus = new Status(data.nome());
        this.statusRepository.save(novoStatus);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<StatusResponseDTO> getStatusById(@PathVariable Long statusId) {
        Optional<Status> status = statusRepository.findById(statusId);
        return status.map(value -> new ResponseEntity<>(new StatusResponseDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity deleteStatus(@PathVariable Long statusId) {

        if (!statusRepository.existsById(statusId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        statusRepository.deleteById(statusId);
        return ResponseEntity.ok().build();
    }

}