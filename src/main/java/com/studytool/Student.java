package com.studytool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.studytool.model.FillInTheBlankQuestion;
import com.studytool.model.MultipleChoiceQuestion;

public class Student implements Serializable, Comparable<Student> {
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Exam> takenExams;

	public Student(String name) {
		this.name = name;
		this.takenExams = new ArrayList<Exam>();
	}

	public String getName() {
		return this.name;
	}

	public void answerTFQuestion(Exam exam, int questionNumber, boolean answer) {
		Exam studentExamCopy;
		TrueFalseQuestion tfQuestion;

		if (!takenExams.contains(exam)) {
			studentExamCopy = new Exam(exam);
			takenExams.add(studentExamCopy);
		}

		studentExamCopy = takenExams.get(takenExams.indexOf(exam));
		tfQuestion = (TrueFalseQuestion) studentExamCopy.getQuestionBank().get(questionNumber);
		tfQuestion.setStudentAnswer(answer);
	}

	public void answerMCQuestion(Exam exam, int questionNumber, String[] answer) {
		Exam studentExamCopy;
		MultipleChoiceQuestion mcQuestion;

		if (!takenExams.contains(exam)) {
			studentExamCopy = new Exam(exam);
			takenExams.add(studentExamCopy);
		}

		studentExamCopy = takenExams.get(takenExams.indexOf(exam));
		mcQuestion = ((MultipleChoiceQuestion) studentExamCopy.getQuestionBank().get(questionNumber));
		mcQuestion.setStudentAnswer(answer);
	}

	public void answerFitBQuestion(Exam exam, int questionNumber, String[] answer) {
		Exam studentExamCopy;
		FillInTheBlankQuestion fitbQuestion;

		if (!takenExams.contains(exam)) {
			studentExamCopy = new Exam(exam);
			takenExams.add(studentExamCopy);
		}

		studentExamCopy = takenExams.get(takenExams.indexOf(exam));
		fitbQuestion = ((FillInTheBlankQuestion) studentExamCopy.getQuestionBank().get(questionNumber));
		fitbQuestion.setStudentAnswer(answer);

	}

	private double pointsEarnedOnTFQuestion(TrueFalseQuestion question) {
		return (question.getStudentAnswer() == question.getAnswer()) ? question.getPoints() : 0;
	}

	private double pointsEarnedOnMCQuestion(MultipleChoiceQuestion question) {
		return Arrays.equals(question.getStudentAnswer(), question.getAnswer()) ? question.getPoints() : 0;
	}

	private double pointsEarnedOnFITBQuestion(FillInTheBlankQuestion question) {
		int numOfAnswers = question.getAnswer().length;
		double pointsPerCorrect = question.getPoints() / numOfAnswers;
		double pointsEarned = 0;
		String[] studentAnswers = question.getStudentAnswer();
		String[] answers = question.getAnswer();

		if (Arrays.equals(studentAnswers, answers)) {
			return question.getPoints();
		} else {
			for (String answer : answers) {
				pointsEarned += Arrays.asList(studentAnswers).indexOf(answer) > -1 ? pointsPerCorrect : 0;
			}
		}

		return pointsEarned;
	}

	private double calculatePointsEarned(Question question) {
		double pointsEarned = 0;

		if (question instanceof TrueFalseQuestion) {
			pointsEarned = pointsEarnedOnTFQuestion((TrueFalseQuestion) question);

		} else if (question instanceof MultipleChoiceQuestion) {
			pointsEarned = pointsEarnedOnMCQuestion((MultipleChoiceQuestion) question);

		} else if (question instanceof FillInTheBlankQuestion) {
			pointsEarned = pointsEarnedOnFITBQuestion((FillInTheBlankQuestion) question);
		}

		return pointsEarned;

	}

	public double getExamScore(Exam exam) {
		double totalPointsEarned = 0;
		Exam studentExamCopy = takenExams.get(takenExams.indexOf(exam));
		ArrayList<Question> questions = new ArrayList<Question>(studentExamCopy.getQuestionBank());

		for (Question question : questions) {
			totalPointsEarned += calculatePointsEarned(question);
		}

		return totalPointsEarned;
	}

	public StringBuffer getGradingReportOf(Exam exam) {
		StringBuffer gradingReport = new StringBuffer();
		double totalExamPoints = 0, pointsEarnedOnQuestion = 0, pointsEarnedOnExam = 0;
		Exam findExam = takenExams.get(takenExams.indexOf(exam));
		ArrayList<Question> questions = findExam.getQuestionBank();

		for(Question question : questions){
			totalExamPoints += question.getPoints();
			pointsEarnedOnQuestion = calculatePointsEarned(question);
			pointsEarnedOnExam += pointsEarnedOnQuestion;

			gradingReport.append("Question #" + question.getQuestionNumber() + " ");
			gradingReport.append(pointsEarnedOnQuestion + " points out of ");
			gradingReport.append(question.getPoints() + "\n");

		}

		gradingReport.append("Final Score: " + pointsEarnedOnExam + " out of ");
		gradingReport.append(totalExamPoints);

		return gradingReport;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Student)) {
			return false;
		} else {
			return this.name.equals(((Student) obj).name) ? true : false;
		}
	}

	@Override
	public int hashCode() {
		return Math.abs(this.name.hashCode());
	}

	@Override
	public int compareTo(Student studentInstance) {
		return this.name.compareTo(studentInstance.name);
	}
}
