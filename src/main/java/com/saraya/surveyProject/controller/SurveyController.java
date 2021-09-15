package com.saraya.surveyProject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.saraya.surveyProject.model.Question;
import com.saraya.surveyProject.model.Survey;
import com.saraya.surveyProject.service.SurveyService;

@RestController
@RequestMapping(value = "/surveys")
public class SurveyController {
	@Autowired
	private SurveyService ss;
	

    @GetMapping("/")
    public List<Survey> getSurveys(){
        return ss.retrieveAllSurvey();
    }

	@GetMapping("/{surveyId}/questions")
	public List<Question> questionBySurvey(@PathVariable 
			String surveyId){
		return ss.retrieveQuestions(surveyId);
	}

    @GetMapping("/{surveyId}/questions/{questionId}")
    public Question getSingleQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        return ss.retrieveASingleQuestion(surveyId, questionId);
    }
	
	@PostMapping("/{surveyId}/questions")
    public ResponseEntity<Void> addQuestion(@PathVariable String surveyId, @RequestBody Question newQuestion) {
            Question question = ss.addQuestion(surveyId, newQuestion);
        if (question == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<Void> updateQuestion(@PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question newQuestion) {
        Question question = ss.updateQuestion(surveyId, questionId, newQuestion);

        if (question != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(question.getId()).toUri();

            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    public Question deleteQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        return ss.deleteQuestion(surveyId, questionId);
    }
}

