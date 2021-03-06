package com.alza.quiz.qfactory.fraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import com.alza.common.math.Fraction;
import com.alza.common.math.MathUtils;
import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.qfactory.IQuestionFactory;
import com.alza.quiz.util.CommonFunctionAndValues;

public class FractionAddSubtract implements IQuestionFactory{
	private static int numq = 6;
	Locale loc;
	ResourceBundle bundle;
	public FractionAddSubtract(Locale loc){
		this.loc = loc;
		initStringFromLocale();
	}
	public FractionAddSubtract(){
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}
	private void initStringFromLocale(){
		bundle = ResourceBundle.getBundle("lang.langbundle", loc);
	}
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
		List<Quiz> quizList= new ArrayList<Quiz>();
		for (int i=0; i<numq; i++){
			MultipleChoiceQuiz q = null;
			if (i >= 4){
				q = generateTypeC(i);
				q.setDifficultyLevel(QuizLevel.SULIT);
				q.setLessonGrade(5);
			} else if (i >= 2){
				q = generateTypeB(i);
				q.setDifficultyLevel(QuizLevel.SEDANG);
				q.setLessonGrade(5);
			} else {
				q = generateTypeA(i);
				q.setDifficultyLevel(QuizLevel.MUDAH);
				q.setLessonGrade(4);
			}
			//q.setQuestion(CommonFunctionAndValues.enclosedWithMathJaxExp(q.getQuestion()));
			q.setLessonSubcategory(bundle.getString("fraction.addsubtract"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonCategory(bundle.getString("fraction"));
			q.setSubCategoryOrder(4);
			q.setLocale(loc);
			quizList.add(q);
		}
		return quizList;
	}

	private MultipleChoiceQuiz generateTypeA(int i) {
		MultipleChoiceQuiz q = new MultipleChoiceQuiz();
		int denom;
		int a1,a2;
		do {
			denom = CommonFunctionAndValues.getRandomInt(4, 10);
			a1 = CommonFunctionAndValues.getRandomInt(2, 6);
			a2 = CommonFunctionAndValues.getRandomInt(2, 7);
		} while (!(denom > a1 && denom > a2 && (a1+a2)<=denom && a1>a2));
		buildQuestion(i, q, denom, denom, a1, a2);
		return q;
	}
	private MultipleChoiceQuiz generateTypeB(int i) {
		MultipleChoiceQuiz q = new MultipleChoiceQuiz();
		int denom;
		int a1,a2;
		do {
			denom = CommonFunctionAndValues.getRandomInt(5, 12);
			a1 = CommonFunctionAndValues.getRandomInt(2, 10);
			a2 = CommonFunctionAndValues.getRandomInt(2, 10);
		} while (!(denom > a1 && denom > a2 && (a1+a2)<=denom && a1>a2));
		buildQuestion(i, q, denom, denom, a1, a2);
		return q;
	}
	private MultipleChoiceQuiz generateTypeC(int i) {
		MultipleChoiceQuiz q = new MultipleChoiceQuiz();
		int denomLeft,denomRight;
		int a1,a2,gcd;
		do {
			denomLeft = CommonFunctionAndValues.getRandomInt(4, 12);
			denomRight = CommonFunctionAndValues.getRandomInt(4, 12);
			a1 = CommonFunctionAndValues.getRandomInt(2, 10);
			a2 = CommonFunctionAndValues.getRandomInt(2, 10);
			gcd = MathUtils.findGCDDjikstra(denomLeft, denomRight);
		} while (denomLeft==denomRight||denomLeft<a1||denomRight<a2||gcd<2);
		buildQuestion(i, q, denomLeft, denomRight, a1, a2);
		return q;
	}
	private void buildQuestion(int i, MultipleChoiceQuiz q, int denomLeft,
			int denomRight, int a1, int a2) {
		Fraction f1 = new Fraction(a1, denomLeft);
		Fraction f2 = new Fraction(a2, denomRight);
		Fraction result;
		if (i % 2 == 0){
			result = f1.getResultWhenAddedWith(f2);
			q.setQuestion(f1.toMathJaxString()+" + "+f2.toMathJaxString());
			q.setProblemString(f1.toString() + "+" + f2.toString());
		} else{
			result = f1.getResultWhenSubtractWith(f2);
			q.setQuestion(f1.toMathJaxString()+" - "+f2.toMathJaxString());
			q.setProblemString(f1.toString() + "-" + f2.toString());
		}
		q.setCorrectAnswer(result.toString());
		q.setChoices(buildChoices(f1,f2,result));
	}
	private Set<String> buildChoices(Fraction f1,Fraction f2,Fraction result){
		Fraction[] choices = new Fraction[3];
		choices[0] = result;
		boolean isAddition=false;
		isAddition = (result.equals(f1.getResultWhenAddedWith(f2)));
		if (isAddition){
			choices[1] = result.inverse();			
			choices[2] = new Fraction(f1.a + f2.a, f1.b + f2.b);
		} else {
			choices[1] = result.inverse();
			if (f1.a != f2.a && f1.b != f2.b) {
				choices[2] = new Fraction(f1.a - f2.a, f1.b - f2.b);
			} else {
				choices[2] = new Fraction(f1.a - f2.b, f1.b - f2.a);
			}
			
		}
		Set<String> choicesInString = new HashSet<String>();
		for (Fraction f : choices) {
			choicesInString.add(f.toString());
		}
		return choicesInString;
	}

	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		numq = numOfQuestion;
		return generateQuizList();
	}

}
