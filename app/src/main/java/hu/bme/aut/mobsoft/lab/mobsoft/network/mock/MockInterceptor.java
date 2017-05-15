package hu.bme.aut.mobsoft.lab.mobsoft.network.mock;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.network.NetworkConfig;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.MemoryRepository;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class MockInterceptor implements Interceptor {

    private MemoryRepository memoryRepository;

    private Pattern getQuestionByIdPattern;
    private Pattern getAnswersByQuestionIdPattern;

    @Inject
    Gson gson;

    MockInterceptor() {
        memoryRepository = new MemoryRepository();
        injector.inject(this);
        getQuestionByIdPattern =
                Pattern.compile(NetworkConfig.ENDPOINT_PREFIX + "question/(\\d+)");
        getAnswersByQuestionIdPattern =
                Pattern.compile(NetworkConfig.ENDPOINT_PREFIX + "answer/forQuestionId/(\\d+)");
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request request = chain.request();
        final Uri uri = Uri.parse(request.url().toString());

        Log.d("Test Http Client", "URL call: " + uri.toString());

        if (uri.getPath().startsWith(NetworkConfig.ENDPOINT_PREFIX + "question")) {
            return processQuestionRequest(request);
        }
        if (uri.getPath().startsWith(NetworkConfig.ENDPOINT_PREFIX + "answer")) {
            return processAnswerRequest(request);
        }

        return MockHelper.makeResponse(request, 400, "Unknown");
    }

    private Response processQuestionRequest(Request request) {
        final Uri uri = Uri.parse(request.url().toString());
        final String uriPath = uri.getPath();

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "question")
                && request.method().equals("POST")){
            return MockHelper.makeResponse(request, 201, "42");
        }

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "question")
                && request.method().equals("GET")){
            final List<Question> questions = memoryRepository.getQuestions(null, null);
            final String responseString = gson.toJson(questions);

            return MockHelper.makeResponse(request, 200, responseString);
        }

        Matcher matcher = getQuestionByIdPattern.matcher(uriPath);
        if(matcher.matches() && request.method().equals("GET")) {
            final Question question = new Question();
            question.setId(Long.valueOf(matcher.group(1)));
            final String responseString = gson.toJson(question);

            return MockHelper.makeResponse(request, 200, responseString);
        }

        return MockHelper.makeResponse(request, 400, "Unknown operation");
    }

    private Response processAnswerRequest(Request request) {
        final Uri uri = Uri.parse(request.url().toString());
        final String uriPath = uri.getPath();

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "answer")
                && request.method().equals("POST")){
            return MockHelper.makeResponse(request, 201, "1337");
        }

        if(uriPath.equals(NetworkConfig.ENDPOINT_PREFIX + "answers/rate")
                && request.method().equals("POST")){
            return MockHelper.makeResponse(request, 200, "");
        }

        Matcher matcher = getAnswersByQuestionIdPattern.matcher(uriPath);
        if(matcher.matches() && request.method().equals("GET")){
            final long id = Long.valueOf(matcher.group(1));
            final List<Answer> answers = memoryRepository.getAnswersForId(id);
            final String responseString = gson.toJson(answers);

            return MockHelper.makeResponse(request, 200, responseString);
        }

        return MockHelper.makeResponse(request, 400, "Unknown operation");
    }


}
