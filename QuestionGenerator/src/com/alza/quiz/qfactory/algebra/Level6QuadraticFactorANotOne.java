package com.alza.quiz.qfactory.algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import com.alza.quiz.model.ISingleQuizPrimaryAttributeGenerator;
import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.qfactory.IQuestionFactory;
import com.alza.quiz.util.CommonFunctionAndValues;

public class Level6QuadraticFactorANotOne implements IQuestionFactory {

	private int numOfQuestion = 5;
	private Map<Integer, ProblemSkeleton> qMap = new HashMap<Integer, Level6QuadraticFactorANotOne.ProblemSkeleton>();
	private Locale loc;
	private ResourceBundle bundle;
	private ResourceBundle bundleAlgebra;

	public Level6QuadraticFactorANotOne(Locale loc) {
		this.loc = loc;
		initStringFromLocale();
	}

	public Level6QuadraticFactorANotOne() {
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}

	private void initStringFromLocale() {
		bundle = ResourceBundle.getBundle("lang.langbundle", loc);
		bundleAlgebra = ResourceBundle.getBundle("lang.langbundle-algebra", loc);

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
		List<Quiz> lq = new ArrayList<Quiz>();
		for (int i = 0; i < this.numOfQuestion; i++) {
			ProblemSkeleton p = generateUniqueProblem(i);
			Quiz q = p.generateSingleQuiz();
			setQuizSecondaryAttributes(q);
			lq.add(q);
		}
		return lq;
	}

	private ProblemSkeleton generateUniqueProblem(int idx) {
		ProblemSkeleton p;
		do {
			p = new ProblemSkeleton(idx);
		} while (qMap.containsKey(p.hash()));
		qMap.put(p.hash(), p);
		return p;
	}

	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
		return generateQuizList();
	}

	private void setQuizSecondaryAttributes(Quiz q) {
		q.setDifficultyLevel(QuizLevel.MUDAH);
		q.setLessonSubcategory(bundleAlgebra.getString("algebra.level5.quadratic"));
		q.setLessonClassifier(bundle.getString("mathelementary"));
		q.setLessonGrade(5);
		q.setSubCategoryOrder(6);
		q.setLessonCategory(bundle.getString("algebra"));
		q.setLocale(loc);
	}

	protected class ProblemSkeleton implements ISingleQuizPrimaryAttributeGenerator {
		int a,b,c;			
		int num1;
		int num2;
		String var;		
		final String[] VARSYM = { "x", "y", "z" };
		ProblemSkeleton(int idx) {			
			int modA; 
			var = VARSYM[ThreadLocalRandom.current().nextInt(0, VARSYM.length)];
			do {
				// default positive pair
				num1 = ThreadLocalRandom.current().nextInt(2, 9);
				num2 = ThreadLocalRandom.current().nextInt(2, 9);
				int mod = idx % 3;
				if (mod==1) {
					// positive and negative pair
					num1 = num1 * CommonFunctionAndValues.getRandom(new int[] {1,-1});
				} else if (mod==2) {
					// negative pair
					num1 = num1 * -1;
					num2 = num2 * -1;					
				}
				a = ThreadLocalRandom.current().nextInt(2, 6);
				modA = num1 % a;
			} while (num1 == num2 || num1 + num2 == 0 ||modA==0);			
			b = num1 + a*num2;
			c = num1 * num2;
		}

		int hash() {
			String s = a + " " +b+" "+c;
			return (CommonFunctionAndValues.hashSimple(s));
		}

		private String replaceAllSymbols(String s) {
			s = s.replace("avar", String.valueOf(this.a));
			s = s.replace("bvar", String.valueOf(this.b));
			s = s.replace("cvar", String.valueOf(this.c));			
			s = s.replace("VAR", String.valueOf(var));
			return s;
		}

		private String[] wrongChoices() {
			String firstFactor,secondFactor;
			if (num1>0) {
				firstFactor = "("+this.a+var+"+"+num1+")";
			} else {
				firstFactor = "("+this.a+var+num1+")";
			}
			if (num2>0) {
				secondFactor = "("+var+"+"+num2+")";
			} else {
				secondFactor = "("+var+num2+")";
			}
			
			String firstFactorWrong,secondFactorWrong;
			String firstFactorWrongCoeff,secondFactorWrongCoeff;
			if (num1>0) {
				firstFactorWrong = "("+this.a+var+"-"+num1+")";
				firstFactorWrongCoeff = "("+var+"-"+num1+")";
			} else {
				firstFactorWrong = "("+this.a+var+"+"+(-num1)+")";
				firstFactorWrongCoeff = "("+var+"+"+(-num1)+")";
			}
			if (num2>0) {
				secondFactorWrong = "("+var+"-"+num2+")";
				secondFactorWrongCoeff = "("+this.a+var+"-"+num2+")";
			} else {
				secondFactorWrong = "("+var+"+"+(-num2)+")";
				secondFactorWrongCoeff = "("+this.a+var+"+"+(-num2)+")";
			}
									
			String[] choices = { 
					firstFactorWrong+secondFactorWrong, 
					firstFactor+secondFactorWrong,
					firstFactorWrong+secondFactor,
					firstFactorWrongCoeff+secondFactorWrongCoeff,
					firstFactorWrong+secondFactorWrongCoeff,
					};
			return choices;
		}

		@Override
		public String generateQuestion() {
			String s = "avarVAR^2";
			if (b > 0) {
				s += "+bvarVAR";
			} else {
				s += "bvarVAR";
			}
			if (c > 0) {
				s += "+cvar";
			} else {
				s += "cvar";
			}			
			s = replaceAllSymbols(s);
			return s;
		}

		@Override
		public String generateQuestionMathjax() {
			String s = generateQuestion();
			s = replaceAllSymbols(s);
			s = CommonFunctionAndValues.enclosedWithMathJaxExp(s);
			
			s = "Factor "+ s;
			return s;
		}

		@Override
		public String generateQuestionWolfram() {
			return generateQuestion();
		}

		@Override
		public String[] generateChoices() {
			int choiceSize = 3;
			List<String> choices = Arrays.asList(wrongChoices());
			String[] finalChoices = new String[choiceSize];
			Collections.shuffle(choices);
			for (int i = 0; i < choiceSize - 1; i++) {
				finalChoices[i] = choices.get(i);
			}
			finalChoices[choiceSize - 1] = generateAnswer();
			return finalChoices;
		}

		@Override
		public String generateAnswer() {
			String firstFactor,secondFactor;
			if (num1>0) {
				firstFactor = "("+this.a+var+"+"+num1+")";
			} else {
				firstFactor = "("+this.a+var+num1+")";
			}
			if (num2>0) {
				secondFactor = "("+var+"+"+num2+")";
			} else {
				secondFactor = "("+var+num2+")";
			}
			return firstFactor+secondFactor;
		}

		@Override
		public Quiz generateSingleQuiz() {
			MultipleChoiceQuiz q = new MultipleChoiceQuiz();
			q.setQuestion(generateQuestionMathjax());
			q.setProblemString(generateQuestionWolfram());
			q.setCorrectAnswer(generateAnswer());
			q.setChoices(generateChoices());
			return q;
		}

	}

}