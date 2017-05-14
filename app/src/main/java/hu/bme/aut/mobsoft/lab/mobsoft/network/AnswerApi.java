package hu.bme.aut.mobsoft.lab.mobsoft.network;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AnswerApi {

  /**
   * Update answer
   *
   * @param body Answer object to update
   * @return Call<Void>
   */

  @PUT("answer")
  Call<Void> updateAnswer(
          @Body Answer body
  );


  /**
   * Create answer
   *
   * @param body Answer object to create
   * @return Call<Long>
   */

  @POST("answer")
  Call<Long> createAnswer(
          @Body Answer body
  );


  /**
   * Get all answers for question ID
   *
   * @param questionId ID of the question to get answers for
   * @return Call<List<Answer>>
   */

  @GET("answer/forQuestionId/{questionId}")
  Call<List<Answer>> getAnswersForId(
          @Path("questionId") Long questionId
  );


  /**
   * Rate an answer
   *
   * @param body Rate description object
   * @return Call<Void>
   */

  @POST("answer/rate")
  Call<Void> rateAnswer(
          @Body Rating body
  );


  /**
   * Get answer by ID
   *
   * @param answerId ID of the answer to return
   * @return Call<Answer>
   */

  @GET("answer/{answerId}")
  Call<Answer> getAnswerById(
          @Path("answerId") Long answerId
  );


}

