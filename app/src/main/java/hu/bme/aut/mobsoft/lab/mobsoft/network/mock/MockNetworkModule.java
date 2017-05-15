package hu.bme.aut.mobsoft.lab.mobsoft.network.mock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.network.AnswerApi;
import hu.bme.aut.mobsoft.lab.mobsoft.network.NetworkModule;
import hu.bme.aut.mobsoft.lab.mobsoft.network.QuestionApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class MockNetworkModule {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private NetworkModule networkModule = new NetworkModule();

    @Provides
    @Singleton
    public OkHttpClient.Builder provideOkHttpClientBuilder() {
        return networkModule.provideOkHttpClientBuilder();
    }
    @Provides
    @Singleton
	public OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder) {

		builder.interceptors().add(new MockInterceptor());
		return builder.build();
	}

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return networkModule.provideRetrofit(client, gson);
    }

    @Provides
    @Singleton
    public AnswerApi provideAnswerApi(Retrofit retrofit) {
        return networkModule.provideAnswerApi(retrofit);
    }

    @Provides
    @Singleton
    public QuestionApi provideQuestionApi(Retrofit retrofit) {
        return networkModule.provideQuestionApi(retrofit);
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    }
}
