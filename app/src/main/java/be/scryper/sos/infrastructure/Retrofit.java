package be.scryper.sos.infrastructure;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static final String BASE_URL = "https://localhost:5001/api/";
    private static retrofit2.Retrofit instance;

    public  static retrofit2.Retrofit getInstance() {
        if(instance == null) {
            instance = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
