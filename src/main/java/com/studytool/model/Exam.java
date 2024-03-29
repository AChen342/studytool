package com.studytool.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Exam implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private ArrayList<Question> questionBank;

	public Exam(){
		this.id = -1;
		this.title = "";
	}

	public Exam(int id, String title) {
		if(id < 0)
			throw new IllegalArgumentException("Exam id cannot be negative.");
		
		if(title == null || title.isEmpty() || title.isBlank())
			throw new IllegalArgumentException("Invalid exam id passed.");

		this.id = id;
		this.title = title;
		this.questionBank = new ArrayList<Question>();
	}

	public Exam(Exam examInstance) {
		if(examInstance == null)
			throw new IllegalArgumentException("Exam instance is null.");

		this.id = examInstance.id;
		this.title = examInstance.title;
		this.questionBank = new ArrayList<Question>(examInstance.getQuestionBank());
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	private int findQuestionIndex(int questionNumber) {
		for (int idx = 0; idx < questionBank.size(); idx++) {
			if (questionBank.get(idx).getQuestionNumber() == questionNumber) {
				return idx;
			}
		}

		return -1;
	}

	public void addQuestion(Question question) {
		if(question == null)
			throw new IllegalArgumentException("Question instance is null.");
			
		int idx = findQuestionIndex(question.getQuestionNumber());

		if (idx < 0) {
			questionBank.add(question);
		} else {
			questionBank.remove(idx);
			questionBank.add(question);
		}
	}

	public ArrayList<Question> getQuestionBank() {
		return new ArrayList<Question>(questionBank);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Exam)) {
			return false;
		} else {
			return this.id == ((Exam) obj).id;
		}
	}

	@Override
	public int hashCode() {
		return this.id;
	}
}
