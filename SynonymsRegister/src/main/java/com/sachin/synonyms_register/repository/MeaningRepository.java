package com.sachin.synonyms_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sachin.synonyms_register.entity.Meaning;

@Repository
public interface MeaningRepository extends JpaRepository<Meaning, Long>{

}
