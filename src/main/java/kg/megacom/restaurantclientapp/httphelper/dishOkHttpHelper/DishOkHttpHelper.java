package kg.megacom.restaurantclientapp.httphelper.dishOkHttpHelper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import kg.megacom.restaurantclientapp.models.Dish;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DishOkHttpHelper {
    public static DishOkHttpHelper getInstance(){
        return new DishOkHttpHelper();
    }
    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient okHttpClient = new OkHttpClient();

    public List<Dish> getDishesByMenuId(Long id) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse("http://localhost:8080/dish/getByMenuId").newBuilder();
        builder.addQueryParameter("id", String.valueOf(id));
        String url = builder.build().toString();
        Request request = new Request.Builder().addHeader("Content-Type","application/json")
                .url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        List<Dish> dishList = new ArrayList<>();
        if ((response.isSuccessful())){
            dishList = objectMapper.readValue(response.body().string(), new TypeReference<List<Dish>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            return dishList;
        }else{
            return dishList;
        }
    }
    public Dish getDishById(Long id) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse("http://localhost:8080/getDishById").newBuilder();
        builder.addQueryParameter("id", String.valueOf(id));
        String url = builder.build().toString();
        Request request = new Request.Builder().addHeader("Content-Type","application/json")
                .url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        Dish dish = new Dish();
        if (response.isSuccessful()){
            dish = objectMapper.readValue(response.body().string(), Dish.class);
            return dish;
        }else {
            return dish;
        }
    }
}
