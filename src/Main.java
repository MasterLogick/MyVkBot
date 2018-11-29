import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Random;

public class Main {
    static int APP_ID = 6746854;
    static String CLIENT_SECRET = "e0COCN0v7Ij0lnWZ2pmh";
    static String REDIRECT_URI = "https://oauth.vk.com/blank.html";
    static String ACCESS_TOKEN = "ef18f1566947e32440505335129430031b29f0f891bd6d254911d0e2877c329a3bd4481a675b0e80c9a1d";
    static int USER_ID = 279640317;
    private static java.lang.Object Object;

    public static void main(String[] args) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        CookieHandler.setDefault(new CookieManager());
       /* HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://oauth.vk.com/authorize?client_id="+APP_ID+"&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=134217727&response_type=token&v=5.87&state=0");
        httpPost.setHeader("User-Agent","Mozilla/5.0");
        httpPost.setHeader("Cookie", "");
        try {
            HttpResponse hr = httpClient.execute(httpPost);
            httpPost.abort();
            System.out.println(new BufferedReader(new InputStreamReader(hr.getEntity().getContent())).readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        UserAuthResponse authResponse = vk.oauth().userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code).execute();
        UserActor actor = new UserActor(USER_ID, ACCESS_TOKEN);
        try {
            String ids = vk.friends().get(actor).unsafeParam("fields", "uid,first_name,last_name").executeAsString();
            Gson gson = new Gson();
            Test r = gson.fromJson(ids, Test.class);
            /*for (Integer i:
                 ids.getItems()) {
                System.out.println(i);
            }*/
            for (Test.Response.Item i :
                    r.response.items) {
                System.out.println(i.id+" "+i.first_name+" "+i.last_name);
            }
            for (int i = 0; i <20 ; i++) {
                vk.messages().send(actor).userId(221167324).message(String.valueOf(new Random().nextInt())).execute();
                Thread.sleep(700);
            }

//            vk.messages().send(actor).userId(221167324).message("чекай я это пишу из бота").execute();
//            List<String> uids = ids.getItems().stream().map(Object::toString).collect(Collectors.toList());

            //vk.messages().send(actor).message("bbb").userId(USER_ID).execute();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(vk.messages().getDialogs(actor).count(1).toString());
    }

    class Test {
        public Response response;

        class Response {
            public Integer count;
            public Item[] items;

            class Item {
                public Integer id;
                public Integer online;
                public String first_name;
                public String last_name;
            }
        }
    }
}