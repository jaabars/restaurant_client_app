package kg.megacom.restaurantclientapp.httphelper.menuOkHttpHelper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import kg.megacom.restaurantclientapp.models.Menu;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuOkHttpHelper {

    public static MenuOkHttpHelper getInstance (){
        return new MenuOkHttpHelper();
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient okHttpClient = new OkHttpClient();

    public List<Menu> getMenus() throws IOException {
        Request request = new Request.Builder().addHeader("Content-Type","application/json")
                .url("http://localhost:8080/menu/get").build();
        Response response = okHttpClient.newCall(request).execute();
        List<Menu> menuList = new ArrayList<>();
        if(response.isSuccessful()){
            menuList = objectMapper.readValue(response.body().string(), new TypeReference<List<Menu>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            return menuList;
        }else {
            return menuList;
        }
    }
}
