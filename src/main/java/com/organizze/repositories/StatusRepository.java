package com.organizze.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organizze.model.status.Status;


public interface StatusRepository extends JpaRepository<Status, Long>{
    
}
