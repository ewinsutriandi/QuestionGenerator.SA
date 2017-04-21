package com.alza.quiz.model;

import java.util.Comparator;
import java.util.Locale;

/**
 * Created by galuh on 24/12/16.
 */

public abstract class Quiz implements Comparable<Quiz>, Comparator<Quiz>{
    QuizLevel quizLevel;
    Locale loc;
    int subCategoryOrder = 99;
    String question;
    String correctAnswer;
    String lessonClassifier;
    String lessonCategory;
    String lessonSubcategory;
    int localGeneratorOrder;
    int lessonGrade;
    public boolean isCorrect(Object answer){
        return false;
    }

    public QuizLevel getQuizLevel() {
        return quizLevel;
    }

    public String getQuestion() {
        return question;
    }
    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public String getLessonClassifier() {
        return lessonClassifier;
    }

    public void setLessonClassifier(String lessonClassifier) {
        this.lessonClassifier = lessonClassifier;
    }

    public String getLessonCategory() {
        return lessonCategory;
    }

    public void setLessonCategory(String lessonCategory) {
        this.lessonCategory = lessonCategory;
    }
    public void setDifficultyLevel(QuizLevel quizLevel) {
		this.quizLevel = quizLevel;
	}

    public String getLessonSubcategory() {
        return lessonSubcategory;
    }

    public void setLessonSubcategory(String lessonSubcategory) {
        this.lessonSubcategory = lessonSubcategory;
    }

	public int getSubCategoryOrder() {
		return subCategoryOrder;
	}

	public void setSubCategoryOrder(int subCategoryOrder) {
		this.subCategoryOrder = subCategoryOrder;
	}

	public int getLocalGeneratorOrder() {
		return localGeneratorOrder;
	}

	public void setLocalGeneratorOrder(int localGeneratorOrder) {
		this.localGeneratorOrder = localGeneratorOrder;
	}

	public int getLessonGrade() {
		return lessonGrade;
	}

	public void setLessonGrade(int lessonGrade) {
		this.lessonGrade = lessonGrade;
	}

	public void setQuizLevel(QuizLevel quizLevel) {
		this.quizLevel = quizLevel;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public int compareTo(Quiz o){
		if (this.lessonGrade!=o.lessonGrade){
			return this.lessonGrade - o.lessonGrade;
		} else if (this.quizLevel!=o.quizLevel) {
			return this.quizLevel.compareTo(o.quizLevel);
		} else if (this.subCategoryOrder!=o.subCategoryOrder){
			return this.subCategoryOrder - o.subCategoryOrder;
		} else if (this.localGeneratorOrder != o.localGeneratorOrder){
			return this.localGeneratorOrder - o.localGeneratorOrder;
		}
		return 0;
	}
	
	public int compare(Quiz o1, Quiz o2){
		return o1.compareTo(o2);
	}
	
	
}
