package com.sachin.synonyms_register.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sachin.synonyms_register.dto.WordDto;
import com.sachin.synonyms_register.entity.Meaning;
import com.sachin.synonyms_register.entity.Word;



public interface WordRepository extends JpaRepository<Word, Long>{
	
	Optional<Word> findByWord(String word);
		
	@Query(value = "select new com.sachin.synonyms_register.dto.WordDto(w.word) from Word w " +
            "where w.meaning = (select w1.meaning from Word w1 where w1.word = :value and w1.meaning is not null) and w.word <> :value")
    List<WordDto> findSynonyms(String value);
	
	Long countWordByMeaning(Meaning meaning);

}
