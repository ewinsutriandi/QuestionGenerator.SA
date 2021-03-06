package com.alza.quiz.qfactory.geom.plane.triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import com.alza.common.math.MathUtils;
import com.alza.quiz.model.GeomQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.model.geom.plane.Geom;
import com.alza.quiz.model.geom.plane.Triangle;
import com.alza.quiz.qfactory.IQuestionFactory;

public class TrianglePhytagoreanFindRightEdge implements IQuestionFactory {
	private int numq = 2;
	Locale loc;
	ResourceBundle bundle;

	public TrianglePhytagoreanFindRightEdge() {
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}

	public TrianglePhytagoreanFindRightEdge(Locale loc) {
		super();
		this.loc = loc;
		initStringFromLocale();
	}

	private void initStringFromLocale() {
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
		List<Quiz> quizList = new ArrayList<Quiz>();
		for (int i = 0; i < numq; i++) {
			Triangle shp;
			int phyt[] = MathUtils.generateRandomPythagoreanTriples(3, 5);
			shp = new Triangle(phyt[0],phyt[1],0);
			shp.hideTextsAndMeasurements();
			shp.setShowLeftEdgeLength(true);
			shp.setShowBaselineLength(true);
			GeomQuiz q = new GeomQuiz();
			q.setGeomShape(shp.getPaths());
			q.setCorrectAnswer(Geom.formatMeasurement(shp.getRightEdge()));
			String question = bundle.getString("geom.shape2d.question.triangle.rightedge.phytagorean");
			q.setQuestion(question);
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonSubcategory(bundle.getString("geom.shape2d.triangle"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonGrade(4);
			q.setSubCategoryOrder(5);
			q.setLocale(loc);
			q.setLessonCategory(bundle.getString("geom.shape2d"));
			quizList.add(q);
		}
		return quizList;
	}

	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		numq = numOfQuestion;
		return generateQuizList();
	}

}
