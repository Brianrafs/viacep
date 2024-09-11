import com.example.viacep.services.EnderecoServiceIF
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://viacep.com.br/"

    val enderecoServiceIF: EnderecoServiceIF by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EnderecoServiceIF::class.java)
    }
}
