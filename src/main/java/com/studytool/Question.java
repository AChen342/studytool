package com.studytool;

import java.io.Serializable;

public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int questionNum;
	protected String text;
	protected double points;

	public Question(int questionNum, String text, double points) {
		if(questionNum < 1) 
			throw new IllegalArgumentException("Invalid question number.");

		if(points < 0) 
			throw new IllegalArgumentException("Invalid points.");

		if(text == null || text.isEmpty() || text.isBlank())
			throw new IllegalArgumentException("Invalid question entered.");

		this.questionNum = questionNum;
		this.text = text;
		this.points = points;
	}

	public int getQuestionNumber() {
		return this.questionNum;
	}

	public String getText() {
		return this.text;
	}

	public double getPoints() {
		return this.points;
	}
}
