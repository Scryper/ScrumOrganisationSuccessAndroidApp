package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoInputMeeting;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IMeetingRepository {
    //Get requests
    @GET("meetings/byUser/{idUser}")
    Call<List<DtoInputMeeting>> getByIdUser(@Path("idUser") int idUser, @Header("Authorization")String token );
}
