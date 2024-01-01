package com.studytool.model;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class SystemManager implements Manager, Serializable {
	private static final Exam EMPTY_EXAM = new Exam();
	private static final Student EMPTY_STUDENT = new Student();
	private static final long serialVersionUID = 1L;
	private HashSet<Exam> exams;
	private HashSet<Student> students;
	private String[] letterGrades;
	private double[] cutoffs;

	public SystemManager() {
		this.exams = new HashSet<Exam>();
		this.students = new HashSet<Student>();
		letterGrades = new String[] { "A", "B", "C", "D", "F" };
		cutoffs = new double[] { 90, 80, 70, 60, 0 };
	}

	@Override
	public boolean addStudent(String name) {
		if (name.isEmpty() || name.isBlank() || students.contains(new Student(name))) {
			return false;
		}

		students.add(new Student(name));
		return true;
	}

	@Override
	public boolean addExam(int examId, String title) {
		if (title.isEmpty() || title.isBlank() || exams.contains(new Exam(examId, title))) {
			return false;
		}

		exams.add(new Exam(examId, title));
		return true;
	}

	@Override
	public HashSet<Exam> getExams() {
		return new HashSet<Exam>(exams);
	}

	@Override
	public HashSet<Student> getStudents() {
		return new HashSet<Student>(students);
	}

	private Exam findExam(int examId) {
		Exam exam;
		Iterator<Exam> it = exams.iterator();

		while (it.hasNext()) {
			exam = it.next();

			if (exam.getId() == examId) {
				return exam;
			}
		}

		return EMPTY_EXAM;
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		Exam exam = findExam(examId);

		if (!text.isEmpty() && !text.isBlank() && !exam.equals(EMPTY_EXAM)) {
			exam.addQuestion(new TrueFalseQuestion(questionNumber, text, points, answer));
		}
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		Exam exam = findExam(examId);

		if (!text.isEmpty() && !text.isBlank() && !exam.equals(EMPTY_EXAM) && answer.length > 0) {
			exam.addQuestion(new MultipleChoiceQuestion(questionNumber, text, points, answer));
		}
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		Exam exam = findExam(examId);

		if (!text.isEmpty() && !text.isBlank() && !exam.equals(EMPTY_EXAM) && answer.length > 0) {
			exam.addQuestion(new FillInTheBlankQuestion(questionNumber, text, points, answer));
		}
	}

	@Override
	public String getKey(int examId) {
		String key = "";
		Exam exam = findExam(examId);

		if (!exam.equals(EMPTY_EXAM)) {
			for (Question question : exam.getQuestionBank()) {
				key += "Question Text: " + question.getText() + "\n";
				key += "Points: " + question.getPoints() + "\n";
				key += "Correct Answer: ";

				if (question instanceof TrueFalseQuestion) {
					key += ((TrueFalseQuestion) (question)).getAnswer() ? "True\n" : "False\n";

				} else {
					TreeSet<String> sortedAnswers = new TreeSet<String>();

					if (question instanceof MultipleChoiceQuestion) {
						MultipleChoiceQuestion mcQuestion = ((MultipleChoiceQuestion) question);
						sortedAnswers.addAll(Arrays.asList(mcQuestion.getAnswer()));

					} else if (question instanceof FillInTheBlankQuestion) {
						FillInTheBlankQuestion fitbQuestion = ((FillInTheBlankQuestion) question);
						sortedAnswers.addAll(Arrays.asList(fitbQuestion.getAnswer()));
					}

					key += sortedAnswers.toString();
				}
			}

			return key;
		}

		return "Exam not found";
	}

	private Student findStudent(String studentName) {
		Student student;
		Iterator<Student> it = students.iterator();

		if (students.contains(new Student(studentName))) {
			while (it.hasNext()) {
				student = it.next();

				if (student.getName().equals(studentName)) {
					return student;
				}
			}
		}

		return EMPTY_STUDENT;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		Student student = findStudent(studentName);
		Exam exam = findExam(examId);

		if (!student.getName().isEmpty() && !exam.equals(EMPTY_EXAM)) {
			student.answerTFQuestion(exam, questionNumber, answer);
		}
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Student student = findStudent(studentName);
		Exam exam = findExam(examId);

		if (!student.getName().isEmpty() && !exam.equals(EMPTY_EXAM)) {
			student.answerMCQuestion(exam, questionNumber, answer);
		}
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Exam exam = findExam(examId);
		Student student = findStudent(studentName);

		if (!student.getName().isEmpty() && !exam.equals(EMPTY_EXAM)) {
			student.answerFitBQuestion(exam, questionNumber, answer);
		}
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		Student student = findStudent(studentName);
		Exam exam = findExam(examId);

		return (!student.getName().isEmpty() && !exam.equals(EMPTY_EXAM)) ? (student.getExamScore(exam)) : 0;
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		Student student = findStudent(studentName);
		Exam exam = findExam(examId);

		return (!student.getName().isEmpty() && !exam.equals(EMPTY_EXAM)) ? student.getGradingReportOf(exam).toString()
				: "student doesn't exist";
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double totalScore = 0, maxPoints;
		int totalExams = 0;
		Student student = findStudent(studentName);
		Iterator<Exam> it = exams.iterator();
		Exam exam;

		if (!studentName.isEmpty() && !studentName.isBlank() && !student.getName().isEmpty()) {
			while (it.hasNext()) {
				maxPoints = 0;
				exam = it.next();

				for (Question question : exam.getQuestionBank()) {
					maxPoints += question.getPoints();
				}

				totalScore += student.getExamScore(exam) / maxPoints;
				totalExams++;
			}
		}

		return Math.round((totalScore / totalExams) * 100);
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		double numericGrade = getCourseNumericGrade(studentName);

		for (int idx = 0; idx < cutoffs.length; idx++) {
			if (numericGrade >= cutoffs[idx]) {
				return letterGrades[idx];
			}
		}

		return "A";

	}

	@Override
	public String getCourseGrades() {
		String displayCourseGrades = "";

		TreeSet<Student> sortedStudentList = new TreeSet<Student>();
		sortedStudentList.addAll(students);

		for (Student student : sortedStudentList) {
			displayCourseGrades += student.getName();
			displayCourseGrades += " " + getCourseNumericGrade(student.getName());
			displayCourseGrades += " " + getCourseLetterGrade(student.getName());
			displayCourseGrades += "\n";
		}

		return displayCourseGrades;

	}

	@Override
	public double getMaxScore(int examId) {
		double maxScore = 0, studentScore = 0;
		Exam exam = findExam(examId);
		Iterator<Student> it = students.iterator();

		while (it.hasNext()) {
			studentScore = it.next().getExamScore(exam);
			if (studentScore > maxScore) {
				maxScore = studentScore;
			}
		}

		return maxScore;

	}

	@Override
	public double getMinScore(int examId) {
		double minScore = getMaxScore(examId), studentScore = 0;
		Exam exam = findExam(examId);
		Iterator<Student> it = students.iterator();

		while (it.hasNext()) {
			studentScore = it.next().getExamScore(exam);
			if (studentScore < minScore) {
				minScore = studentScore;
			}
		}

		return minScore;
	}

	@Override
	public double getAverageScore(int examId) {
		double avgScore = 0;
		Exam exam = findExam(examId);
		Iterator<Student> it = students.iterator();

		while (it.hasNext()) {
			avgScore += it.next().getExamScore(exam);
		}

		return avgScore / students.size();
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		File saveFile = new File(fileName);

		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(saveFile));
			output.writeObject(manager);
			output.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public Manager restoreManager(String fileName) {
		SystemManager sysMan = new SystemManager();
		File loadFile = new File(fileName);

		if (loadFile.exists()) {
			try {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(loadFile));
				sysMan = (SystemManager) input.readObject();
				input.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		return sysMan;
	}

}
