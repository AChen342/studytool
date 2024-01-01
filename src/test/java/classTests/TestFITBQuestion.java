package classTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import org.junit.Test;

import com.studytool.model.FillInTheBlankQuestion;

/* Note: FITB stands for Fill in the Blank */
public class TestFITBQuestion {
    static final String FITBQUESTIONS_FILE = "FITBQuestionBank.txt";
    static final String SAMPLE_QUESTION = "This is a sample question";
    static final String[] SAMPLE_ANSWERS = {"These", "are", "sample", "answers"};
    static final String[] SAMPLE_STUDENT_ANSWERS = {"This", "is", "the", "student", "answer"};
    static final double POINTS = 1;

    @Test public void testFITBQuestionConstructor(){
        int questionNum = 0;
        String question = "";
        String[] answers;

        try{
            BufferedReader readLines = new BufferedReader(new FileReader(FITBQUESTIONS_FILE));

            while((question = readLines.readLine()) != null){
                answers = readLines.readLine().split(",");
                questionNum++;

                try{
                    new FillInTheBlankQuestion(questionNum, question, POINTS, answers);
                }catch(Exception e){
                    fail(e.getMessage());
                }
            }

            readLines.close();

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Test public void testPassingInInvalidQuestionNumber(){
        try{
            new FillInTheBlankQuestion(0, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to create question #0");
        }catch(Exception e){}
        
        try{
            new FillInTheBlankQuestion(-1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to create question number with negative value.");
        }catch(Exception e){}
    }

    @Test public void testPassingInInvalidPointsValue(){
        try{
            new FillInTheBlankQuestion(1, SAMPLE_QUESTION, -1, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to create a question worth negative points.");
        }catch(Exception e){}
    }

    @Test public void testPassingInInvalidQuestionText(){
        try{
            new FillInTheBlankQuestion(1, null, POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass in null question text.");
        }catch(Exception e){}

        try{
            new FillInTheBlankQuestion(1, "", POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass in empty string for question text.");
        }catch(Exception e){}

        try{
            new FillInTheBlankQuestion(1, " ", POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass in blank string for question text.");
        }catch(Exception e){}
    }

    @Test public void testPassingInInvalidQuestionAnswers(){
        try{
            new FillInTheBlankQuestion(1, SAMPLE_QUESTION, POINTS, null);
            fail("Exception expected for attempting to pass in null array for answers.");
        }catch(Exception e){}

        try{
            new FillInTheBlankQuestion(1, SAMPLE_QUESTION, POINTS, new String[]{});
            fail("Exception expected for attempting to pass in empty array for answers.");
        }catch(Exception e){}
    }

    @Test public void testSettingStudentFITBAnswer(){
        String[] result;
        FillInTheBlankQuestion fitbQuestion = new FillInTheBlankQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        fitbQuestion.setStudentAnswer(SAMPLE_STUDENT_ANSWERS);
        result = fitbQuestion.getStudentAnswer();

        assertTrue(Arrays.equals(result, SAMPLE_STUDENT_ANSWERS));
    }

    @Test public void testStudentFITBAnswerImmutableAfterSetting(){
        String[] expectedResult = new String[]{"This", "is", "the", "student's", "answer"};
        String[] result;
        FillInTheBlankQuestion fitbQuestion = new FillInTheBlankQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        fitbQuestion.setStudentAnswer(expectedResult);
        result = fitbQuestion.getStudentAnswer();

        assertTrue(Arrays.equals(expectedResult, result));
    
        expectedResult = new String[]{"Attempting", "to", "change", "student",
        "answer"};
        result = fitbQuestion.getStudentAnswer();

        assertFalse(Arrays.equals(expectedResult, result));
    }

    @Test public void testStudentFITBAnswerImmutableAfterGetting(){
        String[] expectedResult = new String[]{"This", "is", "the", "student's", "answer"};
        String[] result;
        FillInTheBlankQuestion fitbQuestion = new FillInTheBlankQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        fitbQuestion.setStudentAnswer(expectedResult);
        result = fitbQuestion.getStudentAnswer();

        assertTrue(Arrays.equals(expectedResult, result));

        result[2] = "isn't";

        assertFalse(Arrays.equals(result, fitbQuestion.getStudentAnswer()));
    }

    @Test public void testQuestionAnswersAreImmutable(){
        FillInTheBlankQuestion fitbQuestion = new FillInTheBlankQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        String[] answers = fitbQuestion.getAnswer();

        assertTrue(Arrays.equals(answers, SAMPLE_ANSWERS));

        answers[2] = "new";
        assertFalse(Arrays.equals(answers, fitbQuestion.getAnswer()));
    }
}
