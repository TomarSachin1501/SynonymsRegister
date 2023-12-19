package com.sachin.synonyms_register.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sachin.synonyms_register.dto.WordDto;
import com.sachin.synonyms_register.entity.Meaning;
import com.sachin.synonyms_register.entity.Word;
import com.sachin.synonyms_register.exception.WordNotFoundException;
import com.sachin.synonyms_register.repository.MeaningRepository;
import com.sachin.synonyms_register.repository.WordRepository;
import com.sachin.synonyms_register.service.WordDtoService;

@Service
public class WordDtoServiceImplementaion implements WordDtoService {

	private static final String noMeaningfound = "Word '%s' does not exist!";
	
	@Autowired
	private WordRepository wordRepo;
	
	@Autowired
	private MeaningRepository meaningRepo;
	
	
	/**
     * Retrieving word from db.
     * @param value of the word object wanted
     * @return WordDTO object
     */
	
	@Override
	public WordDto reteriveWord(String value) {
		// TODO Auto-generated method stub
		Word word = wordRepo.findByWord(value).orElseThrow(
					()-> new WordNotFoundException(String.format(noMeaningfound, value)));
		return new WordDto(word.getWord());
	}

	/**
     * Retrieving word from db.
     * @param value of the word object wanted
     * @return WordDTO object
     */
	
	@Override
	public List<WordDto> reteriveSynonyms(String value) {
		// TODO Auto-generated method stub
		List<WordDto> list = wordRepo.findSynonyms(value);
		return list;
	}
	
	/**
     * Creating new word in the db.
     * @param wordDTO word object
     * @return saved word
     */

	@Override
	public WordDto createWord(WordDto wordDto) {
		// TODO Auto-generated method stub
		wordRepo.findByWord(wordDto.getValue()).orElseGet(()-> saveWord(wordDto));
		return wordDto;
	}
	
	/**
     * Updating existing word with new value.
     * @param oldValue old string value of the word
     * @param wordDTO new word object
     * @return updated word
     */

	@Override
	public WordDto updateWord(String oldValue, WordDto wordDto) {
		// TODO Auto-generated method stub
		Word word = wordRepo.findByWord(oldValue).orElseThrow(()-> new WordNotFoundException(String.format(noMeaningfound, oldValue)));
		word.setWord(wordDto.getValue());
		wordRepo.saveAndFlush(word);
		return wordDto;
	}
	
	/**
     * Assigning a synonym to the existing word.
     * @param existingWordValue word string value
     * @param synonymDTO synonym object (if not exist in db, will be created)
     * @return assigned synonym
     */

	@Override
	public WordDto assignSynonyms(String existingWord, WordDto wordDto) {
		// TODO Auto-generated method stub
		Word word = wordRepo.findByWord(existingWord).orElseThrow(()-> new WordNotFoundException(String.format(noMeaningfound, existingWord)));
		Word synonym = wordRepo.findByWord(wordDto.getValue()).orElseGet(()-> saveWord(wordDto));
		Meaning meaning = Optional.ofNullable(word.getMeaning())
							.orElseGet(()->{
								if(synonym.getMeaning() == null) {
									return new Meaning();
								}else {
									return synonym.getMeaning();
								}
							});
//		handling scenario when word and synonym both have meaning, removing synonym's meaning from db
		if(word.getMeaning() != null) {
			removedMeaning(synonym.getMeaning());
		}
		word.setMeaning(meaning);
		synonym.setMeaning(meaning);
		meaningRepo.save(meaning);
		wordRepo.save(synonym);
		wordRepo.saveAndFlush(word);
		
		return wordDto;
	}
	
	/**
     * Removing relation between word and synonym.
     * @param existingWordValue word string value
     * @param synonymDTO unattached synonym
     */
	
	@Override
	public void deassignSynonym(String existWord, WordDto wordDto) {
		// TODO Auto-generated method stub
		Word word = wordRepo.findByWord(existWord).orElseThrow(
						()-> new WordNotFoundException(String.format(noMeaningfound, existWord)));
		
		Word synonym = wordRepo.findByWord(wordDto.getValue()).orElseThrow(
						()-> new WordNotFoundException(String.format(noMeaningfound, wordDto.getValue())));
		
		if(word.getMeaning()==synonym.getMeaning()) {
			synonym.setMeaning(null);
		}else {
			throw new UnsupportedOperationException(String.format(noMeaningfound, existWord));
		}
		

	}

	/**
     * Deleting a word from db.
     * @param value word string to be deleted
     */
	
	@Override
	public void deleteWord(String value) {
		// TODO Auto-generated method stub
		Word word = wordRepo.findByWord(value).orElseThrow(
					()-> new WordNotFoundException(String.format(noMeaningfound, value)));
		wordRepo.delete(word);
	}
	
	private void removedMeaning(Meaning meaning) {
		// TODO Auto-generated method stub
		if(wordRepo.countWordByMeaning(meaning)==1) {
			meaningRepo.delete(meaning);
		}
	}

	
	private Word saveWord(WordDto wordDto) {
		// TODO Auto-generated method stub
		Word word = new Word();
		word.setWord(wordDto.getValue());
		wordRepo.save(word);
		return word;
	}

}
