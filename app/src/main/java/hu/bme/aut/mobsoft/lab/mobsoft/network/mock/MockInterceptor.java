package hu.bme.aut.mobsoft.lab.mobsoft.network.mock;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.network.NetworkConfig;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class MockInterceptor implements Interceptor {

    private Repository repository;
    private Gson gson;

    private Pattern getQuestionByIdPattern =
            Pattern.compile(NetworkConfig.ENDPOINT_PREFIX + "question/(\\d+)");
    private Pattern getAnswersByQuestionIdPattern =
            Pattern.compile(NetworkConfig.ENDPOINT_PREFIX + "answer/forQuestionId/(\\d+)");

    MockInterceptor(Gson gson, Repository repository) {
        this.gson = gson;
        this.repository = repository;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request request = chain.request();
        final Uri uri = Uri.parse(request.url().toString());
        final String uriPath = uri.getPath();
        final Buffer buffer = new Buffer();
        final RequestBody body = request.body();
        final String bodyStr;
        if(body != null) {
            body.writeTo(buffer);
            bodyStr = buffer.readUtf8();
        } else {
            bodyStr = null;
        }


        Log.d("Test Http Client", "URL call: " + uri.toString());

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "question")
                && request.method().equals("POST")){
            final Question question = gson.fromJson(bodyStr, Question.class);
            final long questionId = repository.saveQuestion(question);
            return MockResponseHelper.makeResponse(request, 201, String.valueOf(questionId));
        }

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "question")
                && request.method().equals("GET")){
            final List<Question> questions = repository.getQuestions(null, null);
            final String responseString = gson.toJson(questions);
            return MockResponseHelper.makeResponse(request, 200, responseString);
        }

        Matcher matcher = getQuestionByIdPattern.matcher(uriPath);
        if(matcher.matches() && request.method().equals("GET")) {
            final Question question = repository.getQuestion(Long.valueOf(matcher.group(1)));
            final String responseString = gson.toJson(question);
            return MockResponseHelper.makeResponse(request, 200, responseString);
        }

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "answer")
                && request.method().equals("POST")){
            final Answer answer = gson.fromJson(bodyStr, Answer.class);

            try {
                final long answerId = repository.saveAnswer(answer);
                return MockResponseHelper.makeResponse(request, 201, String.valueOf(answerId));
            } catch (IllegalArgumentException e) {
                return MockResponseHelper.makeResponse(request, 404, "");
            }
        }

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "answer/rate")
                && request.method().equals("POST")){
            return MockResponseHelper.makeResponse(request, 200, "");
        }

        matcher = getAnswersByQuestionIdPattern.matcher(uriPath);
        if(matcher.matches() && request.method().equals("GET")){
            final long id = Long.valueOf(matcher.group(1));
            final List<Answer> answers = repository.getAnswersForId(id);
            final String responseString = gson.toJson(answers);

            return MockResponseHelper.makeResponse(request, 200, responseString);
        }

        return MockResponseHelper.makeResponse(request, 400, "Unknown operation");
    }


}
