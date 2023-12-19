package com.sachin.synonyms_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.synonyms_register.dto.WordDto;
import com.sachin.synonyms_register.serviceImplement.WordDtoServiceImplementaion;

@RestController
@RequestMapping("/v3/word")
public class WordController {
	
	@Autowired
	private WordDtoServiceImplementaion wordService;
	
	@PostMapping("/")
	public WordDto createWord(@RequestBody WordDto wordDto)
	{
		return wordService.createWord(wordDto);
	}
	
	@GetMapping("/{value}")
	public ResponseEntity<WordDto> fetchWord(@PathVariable String value)
	{
		WordDto word = wordService.reteriveWord(value);
		return new ResponseEntity<>(word,HttpStatus.OK);
	}
	
	@GetMapping("/{value}/synonyms")
	public ResponseEntity<List<WordDto>> fetchAllSynonyms(@PathVariable String value)
	{
		List<WordDto> synonymlist = wordService.reteriveSynonyms(value);
		return new ResponseEntity<>(synonymlist,HttpStatus.OK);
	}
	
	@PutMapping("/{value}/assign")
	public ResponseEntity<WordDto> assignWord(@PathVariable String value, @RequestBody WordDto wordDto)
	{
		WordDto word = wordService.assignSynonyms(value, wordDto);
		return new ResponseEntity<>(word,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{value}/update")
	public ResponseEntity<WordDto> update(@PathVariable String value, @RequestBody WordDto wordDto)
	{
		WordDto word = wordService.updateWord(value, wordDto);
		return new ResponseEntity<>(word,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{value}")
	public ResponseEntity<Void> delete(@PathVariable String value)
	{
		wordService.deleteWord(value);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{value}/deassign")
	public ResponseEntity<Void> deassignWord(@PathVariable String value, WordDto wordDto)
	{
		wordService.deassignSynonym(value, wordDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

	
}
