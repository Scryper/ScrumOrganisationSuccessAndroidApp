package be.scryper.sos.infrastructure;

import java.util.List;

import be.scryper.sos.dto.DtoDeveloperProject;
import be.scryper.sos.dto.DtoProject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDeveloperProjectRepository {

    @GET("developerProject/byIdDeveloper/{idDeveloper}")
    Call<List<DtoDeveloperProject>> getByIdDeveloper(@Path("idDeveloper") int id);
}
