package classTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Test;
import com.studytool.model.TrueFalseQuestion;

/*Note: TF stands for true false */
public class TestTFQuestion {
    final static String TFQUESTION_FILE = "TFQuestionBank.txt";
    final static double POINTS = 1;
    final static String SAMPLE_QUESTION = "Answer true for this sample question.";
    final static boolean SAMPLE_ANSWER = true;

    @Test public void testTFQuestionConstructor(){
        String question;
        int questionNumber = 0;
        boolean answer;

        try{
            BufferedReader readLines = new BufferedReader(new FileReader(TFQUESTION_FILE));

            while(((question = readLines.readLine()) != null)){
                answer = Boolean.parseBoolean(readLines.readLine());

                try{
                    new TrueFalseQuestion(++questionNumber, question, POINTS, answer);

                }catch(Exception e){
                    fail(e.getMessage());
                }
            }

            readLines.close();
            

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test public void testPassingInvalidTFQuestionNumber(){
        try{
            new TrueFalseQuestion(-1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWER);
            fail("Exception expected for attempting to create negative question number.");
        }catch(Exception e){}

        try{
            new TrueFalseQuestion(0, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWER);
            fail("Exception expected for attempting to create question #0");
        }catch(Exception e){}
    }

    @Test public void testPassingInvalidTFQuestionText(){
        try{
            new TrueFalseQuestion(1, null, POINTS, SAMPLE_ANSWER);
            fail("Exception expected for attempting to pass null question text.");
        }catch(Exception e){}

        try{
            new TrueFalseQuestion(1, "", POINTS, SAMPLE_ANSWER);
            fail("Exception expected for attempting to pass empty question text.");
        }catch(Exception e){}

        try{
            new TrueFalseQuestion(1, " ", POINTS, SAMPLE_ANSWER);
            fail("Exception expected for attempting to pass blank question text.");
        }catch(Exception e){}
    }

    @Test public void testSettingStudentTFAnswer(){
        boolean studentAnswer = false, retrieveStudentAnswer;

        TrueFalseQuestion tfQuestion = new TrueFalseQuestion(1, SAMPLE_QUESTION, POINTS, SAMPLE_ANSWER);

        tfQuestion.setStudentAnswer(studentAnswer);
        retrieveStudentAnswer = tfQuestion.getStudentAnswer();

        assertTrue(retrieveStudentAnswer == studentAnswer);
    }
}
