package com.studytool.model;

public class TrueFalseQuestion extends Question {
	private static final long serialVersionUID = 1L;
	private boolean answer;
	private Boolean studentAnswer;

	public TrueFalseQuestion(int questionNumber, String text, double points, boolean answer) {
		super(questionNumber, text, points);
		this.answer = answer;
		this.studentAnswer = null;
	}

	public void setStudentAnswer(boolean studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public boolean getAnswer() {
		return this.answer;
	}

	public Boolean getStudentAnswer() {
		return this.studentAnswer;
	}

}
