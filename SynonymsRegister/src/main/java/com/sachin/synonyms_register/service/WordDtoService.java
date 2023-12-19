package com.sachin.synonyms_register.service;

import java.util.List;

import com.sachin.synonyms_register.dto.WordDto;
import com.sachin.synonyms_register.entity.Word;

public interface WordDtoService {
	
	WordDto reteriveWord(String value);
	List<WordDto> reteriveSynonyms(String value);
	WordDto createWord(WordDto wordDto);
	WordDto updateWord(String oldValue, WordDto wordDto);
	WordDto assignSynonyms(String existingWord, WordDto wordDto);
	void deassignSynonym(String existWord, WordDto wordDto);
	void deleteWord(String value);

}
