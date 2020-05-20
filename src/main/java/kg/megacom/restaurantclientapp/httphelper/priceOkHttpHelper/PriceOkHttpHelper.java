package kg.megacom.restaurantclientapp.httphelper.priceOkHttpHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class PriceOkHttpHelper {
    public  static  PriceOkHttpHelper getInstance(){
        return new PriceOkHttpHelper();
    }
    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient okHttpClient = new OkHttpClient();

    public Double getPrice(Long id) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse("http://localhost:8080/price/getPriceByDishId").newBuilder();
        builder.addQueryParameter("id", String.valueOf(id));
        String url = builder.build().toString();
        Request request = new Request.Builder().addHeader("Content-Type","application/json")
                .url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        Double d = null;
        if (response.isSuccessful()){
            d = Double.valueOf(response.body().string());
            return d;
        }
        else {
            return d;
        }
    }
}
