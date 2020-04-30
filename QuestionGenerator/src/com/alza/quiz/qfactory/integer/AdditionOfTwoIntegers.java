package com.alza.quiz.qfactory.integer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.qfactory.IQuestionFactory;

public class AdditionOfTwoIntegers implements IQuestionFactory{
	Locale loc;
	ResourceBundle bundle;
	public AdditionOfTwoIntegers(Locale loc){
		this.loc = loc;
		initStringFromLocale();
	}
	public AdditionOfTwoIntegers(){
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}
	private void initStringFromLocale(){
		bundle = ResourceBundle.getBundle("lang.langbundle", loc);
	}
	int numOfQuestion = 3;
	int[][] bounds = {
			{3,10},{10,25},{25,50},{50,75},{75,99}
	};
	@Override
	public Quiz generateQuiz() {
		List<Quiz> quizList = generateQuizList();
		int rnd = new Random().nextInt(quizList.size()); 
		return quizList.get(rnd);
	}

	@Override
	public Quiz generateQuiz(QuizLevel quizLevel) {
		return generateQuiz();
	}

	@Override
	public List<Quiz> generateQuizList() {
		List<Quiz> lq = new ArrayList<Quiz>();
		for (int i= 0;i<numOfQuestion;i++){
			int idx;
			if (i<bounds.length){
				idx = i;
			} else {
				idx = i % bounds.length; 
			}
			//System.out.println("index "+idx);
			int a=0,b=0;
			do {
				a = ThreadLocalRandom.current().nextInt(bounds[idx][0], 
						bounds[idx][1]);
				b = ThreadLocalRandom.current().nextInt(bounds[idx][0], 
						bounds[idx][1]);
			} while (a==b);
			MultipleChoiceQuiz  q = new MultipleChoiceQuiz();
			int rslt = a+b;			
			q.setQuestion(a+" + "+b);
			q.addChoice(rslt);
			
			if (rslt > 10) {
				if (idx % 2 == 0) {
					q.addChoice(rslt+10);
				}
				else {
					q.addChoice(rslt-10);
				}
			} else {
				q.addChoice(a*b);
			}
			q.addChoice(rslt,a+10,b-a);
			q.setCorrectAnswer(String.valueOf(rslt));
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonSubcategory(bundle.getString("integer.addtwonum"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonGrade(4);
			q.setSubCategoryOrder(2);
			q.setLocalGeneratorOrder(idx);
			q.setLessonCategory(bundle.getString("integer"));
			q.setLocale(loc);
			lq.add(q);
		}
		
		return lq;
	}

	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
		return generateQuizList();
	}

}
