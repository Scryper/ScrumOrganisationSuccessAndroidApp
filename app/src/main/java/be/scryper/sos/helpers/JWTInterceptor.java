package be.scryper.sos.helpers;

import android.content.Context;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;


public class JWTInterceptor implements okhttp3.Interceptor {
    private SessionManager sessionManager;

    public JWTInterceptor(Context context) {
        sessionManager = new SessionManager(context);
    }


    @Override
    public Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
        Request request= chain.request();
        Request.Builder requestBuilder = chain.request().newBuilder();
        if(sessionManager.fetchAuthToken()!=null){
            requestBuilder.addHeader("Authorization", "Bearer "+sessionManager.fetchAuthToken());

        }
        return chain.proceed(requestBuilder.build());
    }
}