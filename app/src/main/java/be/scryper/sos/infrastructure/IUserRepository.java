package be.scryper.sos.infrastructure;

import be.scryper.sos.dto.DtoAuthenticateRequest;
import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserRepository {
    // Get requests
    @GET("users/byId/{id}")
    Call<DtoUser> getById(@Path("id") int id, @Header("Authorization")String token);

    // Post requests
    @POST("users/authenticate")
    Call<DtoAuthenticateResult> authenticate(@Body DtoAuthenticateRequest authentication);
}
