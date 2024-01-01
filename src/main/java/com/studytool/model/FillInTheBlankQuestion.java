package com.studytool.model;

public class FillInTheBlankQuestion extends Question {
	private static final long serialVersionUID = 1L;
	private String[] answer;
	private String[] studentAnswer;

	public FillInTheBlankQuestion(int questionNumber, String text,
			double points, String[] answer) {
		super(questionNumber, text, points);

		if(answer == null || answer.length < 1)
			throw new IllegalArgumentException("Answer is missing");
		
		this.answer = answer;
		this.studentAnswer = new String[]{};
	}

	public void setStudentAnswer(String[] studentAnswer) {
		this.studentAnswer = new String[studentAnswer.length];

		for(int idx = 0; idx < studentAnswer.length; idx++){
			this.studentAnswer[idx] = studentAnswer[idx];
		}
	}

	public String[] getStudentAnswer() {
		String[] copy = new String[this.studentAnswer.length];

		for (int idx = 0; idx < this.studentAnswer.length; idx++) {
			copy[idx] = this.studentAnswer[idx];
		}

		return copy;
	}

	public String[] getAnswer() {
		String[] copy = new String[this.answer.length];

		for (int idx = 0; idx < this.answer.length; idx++) {
			copy[idx] = this.answer[idx];
		}

		return copy;
	}

}
