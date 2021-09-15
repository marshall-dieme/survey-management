package com.saraya.surveyProject.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.saraya.surveyProject.model.Question;
import com.saraya.surveyProject.model.Survey;

@Service
public class SurveyService {
	
	private static List<Survey> surveys = new ArrayList<>();
	
	static {
		Question question1 = new Question("Question1",
				"Largest country in the world", "Russia",
				Arrays.asList("India","USA","Russia","China"));
		
		Question question2 = new Question("Question2",
				"Most populus Country", "China",
				Arrays.asList("India","USA","Russia","China"));
		
		Question question3 = new Question("Question3",
				"Highest GOP in the world", "USA",
				Arrays.asList("India","USA","Russia","China"));
		
		Question question4 = new Question("Question4",
				"Second largest english speaking", "India",
				Arrays.asList("India","USA","Russia","China"));
		
		
		List<Question> questions = new ArrayList<>(Arrays
				.asList(question1, question2, question3, question4));
		
		Survey survey = new Survey("survey1", "The World survey",
				"Survey about the world", questions);
		
		surveys.add(survey);
	}
	
	public List<Survey> retrieveAllSurvey(){
		return surveys;
	}
	
	public Survey retrieveSurveyById(String surveyId) {
		for(Survey survey : surveys) {
			if(survey.getId().equalsIgnoreCase(surveyId)) {
				return survey;
			}
		}
		return null;
	}
	
	public List<Question> retrieveQuestions(String surveyId){
		return retrieveSurveyById(surveyId).getQuestions();
	}
	
	public Question retrieveASingleQuestion(String surveyId, String questionId) {
		for(Question question : retrieveQuestions(surveyId)) {
			if(question.getId().equalsIgnoreCase(questionId)) {
				return question;
			}
		}
		return null;
	}
	
	private SecureRandom random = new SecureRandom();

	public Question addQuestion(String surveyId, Question question) {
		Survey survey = retrieveSurveyById(surveyId);

		if (survey == null) {
		return null;
		}

		String randomId = new BigInteger(130, random).toString(32);
		question.setId(randomId);

		survey.getQuestions().add(question);

		return question;
	}

	public Question updateQuestion(String surveyId, String questionId, Question newQuestion){
		Question question = retrieveASingleQuestion(surveyId, questionId);
		Survey survey = retrieveSurveyById(surveyId);
		if (question != null) {
			question.setCorrectAnswer(newQuestion.getCorrectAnswer());
			question.setDescription(newQuestion.getDescription());
			question.setOptions(newQuestion.getOptions());
			survey.getQuestions().set(survey.getQuestions().indexOf(question), question);
			return question;
		}
		return null;
	}

    public Question deleteQuestion(String surveyId, String questionId) {
        Question question = retrieveASingleQuestion(surveyId, questionId);
		Survey survey = retrieveSurveyById(surveyId);

		if (survey.getQuestions().remove(question)) {
			return question;
		}

		return null;
    }
}

	

