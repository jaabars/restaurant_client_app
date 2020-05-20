package kg.megacom.restaurantclientapp.httphelper.customerOkHttpHelper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import kg.megacom.restaurantclientapp.models.Customer;

import java.io.IOException;


public class CustomerOkHttpHelper {
    public static  CustomerOkHttpHelper getInstance(){
        return new CustomerOkHttpHelper();
    }
    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient okHttpClient = new OkHttpClient();
    public Response saveCustomer(Customer customer) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),objectMapper.writeValueAsString(customer));
        Request request = new Request.Builder().addHeader("Content-Type","application/json")
                .url("http://localhost:8080/customer/save").post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()){
            customer = objectMapper.readValue(response.body().string(),Customer.class);
            System.out.println(customer.getName()+" "+ customer.getPhone());
        }
        return response;
    }
    public int getCustomerByPhoneAndPassword(String phone, String password) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse("http://localhost:8080/customer/getByPhone").newBuilder();
        builder.addQueryParameter("phone",phone);
        builder.addQueryParameter("password",password);
        String url = builder.build().toString();
        //System.out.println(url);
        Request request = new Request.Builder().addHeader("Content-Type","application/json")
                .url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        int n = 0;
        if (response.isSuccessful()){
            n = Integer.parseInt(response.body().string());
            return n;
        }else {
            return n;
        }
}
}
