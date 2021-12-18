package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoAuthenticateRequest;
import be.scryper.sos.dto.DtoAuthenticateResult;
import be.scryper.sos.dto.DtoUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserRepository {
    // Get requests
    @GET("users")
    Call<List<DtoUser>> getAll();

    @GET("users/byId/{id}")
    Call<DtoUser> getById(@Path("id") int id);

    // Post requests
    @POST("users")
    Call<DtoUser> addUser(@Body DtoUser user);

    @POST("users/authenticate")
    Call<DtoAuthenticateResult> authenticate(@Body DtoAuthenticateRequest authentication);

    // Put requests
    @PUT("users/roleUpdate/{idForRoleUpdate}")
    Call<Void> update(@Path("idForRoleUpdate") int id, @Body DtoUser user);

    // Delete requests
    @DELETE("users/{id}")
    Call<Void> delete(@Path("id") int id);
}
