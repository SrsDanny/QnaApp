package hu.bme.aut.mobsoft.lab.mobsoft.network;

import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface QuestionApi {

  /**
   * Get all the questions
   *
   * @return Call<List<Question>>
   */

  @GET("question")
  Call<List<Question>> getQuestions();



  /**
   * Create a new question
   *
   * @param body Question object to create
   * @return Call<Long>
   */

  @POST("question")
  Call<Long> createQuestion(
          @Body Question body
  );


  /**
   * Get a question by ID
   *
   * @param questionId ID of the question to return
   * @return Call<Question>
   */

  @GET("question/{questionId}")
  Call<Question> getQuestionById(
          @Path("questionId") Long questionId
  );


}
