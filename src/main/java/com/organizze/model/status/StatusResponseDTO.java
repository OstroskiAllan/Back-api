package com.organizze.model.status;

public record StatusResponseDTO(
    Long id,
     String nome
) {
    public StatusResponseDTO(Status status){
        this(status.getId(),
        status.getNome());
    }
} 