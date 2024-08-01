package com.organizze.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/status")
    public ResponseEntity createStatus(@RequestBody @Valid StatusRequestDTO data) {
        Status novoStatus = new Status(data.nome());
        this.statusRepository.save(novoStatus);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<StatusResponseDTO> getStatusById(@PathVariable Long statusId) {
        Optional<Status> status = statusRepository.findById(statusId);
        return status.map(value -> new ResponseEntity<>(new StatusResponseDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}