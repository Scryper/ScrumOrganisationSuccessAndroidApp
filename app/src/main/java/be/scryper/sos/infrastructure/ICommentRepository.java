package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoComment;
import be.scryper.sos.dto.DtoCreateComment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ICommentRepository {
    @GET("comments/byUserStory/{idUserStory}")
    Call<List<DtoComment>> getByIdUserStory(@Path("idUserStory") int id, @Header("Authorization")String token);

    @POST("comments")
    Call<DtoComment> create(@Body DtoCreateComment comment, @Header("Authorization")String token);

    @PUT("comments/{id}")
    Call<Void> updateContent(@Path("id") int id, @Body DtoCreateComment user, @Header("Authorization")String token);

    @DELETE("comments/{id}")
    Call<Void> delete(@Path("id") int id, @Header("Authorization")String token);
}
