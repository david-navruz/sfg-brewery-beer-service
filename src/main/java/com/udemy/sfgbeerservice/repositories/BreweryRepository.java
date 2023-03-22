package com.udemy.sfgbeerservice.repositories;

import com.udemy.sfgbeerservice.domain.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BreweryRepository extends JpaRepository<Brewery, UUID> {

}
