package controllers;

import java.util.*;
import models.*;
import play.*;
import play.mvc.*;
import play.data.validation.*;

@With(Secure.class)
public class Secured extends Controller {
	public static User currentUser(){
		return User.get(Security.connected());
	}
    public static void newQuestion(@Required String content) {
    	if (!validation.hasErrors()) {
    		Question question = new Question(currentUser(), content);
    		Application.question(question.id());
        } else {
        	Application.index();
        }
    }
    
    public static void newAnswer(int questionId, @Required String content) {
    	if (!validation.hasErrors() && Question.get(questionId) != null) {
    		Question.get(questionId).answer(currentUser(), content);
    		Application.question(questionId);
        } else {
        	Application.index();
        }
    }
    
    public static void newCommentQuestion(int questionId, @Required String content) {
    	if (!validation.hasErrors() && Question.get(questionId) != null) {
    		Question.get(questionId).comment(currentUser(), content);
    		Application.commentQuestion(questionId);
    	}
    }
    
    public static void voteQuestionUp(int id) {
    	if (Question.get(id) != null) {
    		Question.get(id).voteUp(currentUser());
    		Application.question(id);
        } else {
        	Application.index();
        }
    }
    
    public static void voteQuestionDown(int id) {
    	if (Question.get(id) != null) {
    		Question.get(id).voteDown(currentUser());
    		Application.question(id);
        } else {
        	Application.index();
        }
    }

    public static void voteAnswerUp(int question, int id) {
    	if (Question.get(question) != null && Question.get(question).getAnswer(id) != null) {
    		Question.get(question).getAnswer(id).voteUp(currentUser());
    		Application.question(question);
        } else {
        	Application.index();
        }
    }
    
    public static void voteAnswerDown(int question, int id) {
    	if (Question.get(question) != null && Question.get(question).getAnswer(id) != null) {
    		Question.get(question).getAnswer(id).voteDown(currentUser());
    		Application.question(question);
        } else {
        	Application.index();
        }
    }
    
  public static void deleteComment(Comment comment, int questionId) {
	  Question question = Question.get(questionId);
	  question.unregister(comment);
	  Application.index();
  }
}
