package classTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import org.junit.Test;

import com.studytool.model.MultipleChoiceQuestion;

public class TestMCQuestion {
    static final String MCQUESTIONS_FILE = "MCQuestionBank.txt";
    static final String SAMPLE_QUESTION = "This is a sample question.";
    static final String[] SAMPLE_ANSWERS = new String[]{"These", "are", "sample", "answers"};
    static final double POINTS = 1;

    @Test public void testMultipleChoiceQuestionConstructor(){
        BufferedReader readLines;
        int questionNumber = 0;
        String question = "";
        String[] answers;
        
        try{
            readLines = new BufferedReader(new FileReader(MCQUESTIONS_FILE));
            
            while((question = readLines.readLine()) != null){
                question += "\n" + readLines.readLine();
                answers = readLines.readLine().split(",");
                questionNumber++;

                try{
                    new MultipleChoiceQuestion(questionNumber, question, POINTS,answers);
                }catch (Exception e){
                    fail(e.getMessage());
                }
            }

            readLines.close();

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Test public void testPassingInInvalidMCQuestionNumber(){
        try{
            new MultipleChoiceQuestion(0, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to create question #0.");

        }catch(Exception e){}

        try{
            new MultipleChoiceQuestion(-1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to create negative question number.");

        }catch(Exception e){}
    }

    @Test public void testPassingInInvalidMCQuestionText(){
        try{
            new MultipleChoiceQuestion(1, null, POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass in null as question text.");

        }catch(Exception e){}
        
        try{
            new MultipleChoiceQuestion(1, "", POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass empty question text.");

        }catch(Exception e){}

        try{
            new MultipleChoiceQuestion(1, " ", POINTS, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass blank question text.");

        }catch(Exception e){}
    }

    @Test public void testPassingInvalidMCPointsValue(){
        try{
            new MultipleChoiceQuestion(1, SAMPLE_QUESTION, -1, SAMPLE_ANSWERS);
            fail("Exception expected for attempting to pass negative points for question.");
        }catch(Exception e){}
    }

    @Test public void testPassingInvalidMCAnswers(){
        try{
            new MultipleChoiceQuestion(1, SAMPLE_QUESTION, POINTS, new String[]{});
            fail("Exception expected for attempting to pass empty answer array.");
        }catch(Exception e){}

        try{
            new MultipleChoiceQuestion(1, SAMPLE_QUESTION, POINTS, null);
            fail("Exception expected for attempting to pass null array.");
        }catch(Exception e){}
    }

    @Test public void testSettingStudentMCAnswer(){
        String[] expectedResult = new String[]{"This", "is", "the", "student's", "answer"};
        String[] result;
        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        mcQuestion.setStudentAnswer(expectedResult);
        result = mcQuestion.getStudentAnswer();

        assertTrue(Arrays.equals(expectedResult, result));
    }

    @Test public void testStudentMCAnswerImmutableAfterSetting(){
        String[] expectedResult = new String[]{"This", "is", "the", "student's", "answer"};
        String[] result;
        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        mcQuestion.setStudentAnswer(expectedResult);
        result = mcQuestion.getStudentAnswer();

        assertTrue(Arrays.equals(expectedResult, result));
    
        expectedResult = new String[]{"Attempting", "to", "change", "student",
        "answer"};
        result = mcQuestion.getStudentAnswer();

        assertFalse(Arrays.equals(expectedResult, result));
    }

    @Test public void testStudentAnswerImmutableAfterGetting(){
        String[] expectedResult = new String[]{"Student", "answer", "is", "final"};
        String[] result;
        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        mcQuestion.setStudentAnswer(expectedResult);
        result = mcQuestion.getStudentAnswer();

        assertTrue(Arrays.equals(expectedResult, result));

        result[2] = "isn't";
        
        assertFalse(Arrays.equals(result, mcQuestion.getStudentAnswer()));
    }

    @Test public void testQuestionAnswersAreImmutable(){
        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWERS);

        String[] answers = mcQuestion.getAnswer();

        assertTrue(Arrays.equals(answers, SAMPLE_ANSWERS));

        answers[2] = "new";
        assertFalse(Arrays.equals(answers, mcQuestion.getAnswer()));
    }
}
