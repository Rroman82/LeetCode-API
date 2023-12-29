package TestsAPI;

import com.beust.ah.A;
import com.google.gson.*;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestAPI extends TestInit {
    @Test
    public void testApi() {
        APIResponse newIssue = request.get("/api/users");
        System.out.println(newIssue.ok());
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);
        System.out.println(json);
        JsonArray dataArray = json.getAsJsonArray("data");
        for (JsonElement element : dataArray) {
            JsonObject dataObject = element.getAsJsonObject();
            System.out.println("ID:" + dataObject.get("id").getAsInt());
            System.out.println("Email:" + dataObject.get("email").getAsString());
            System.out.println("First name:" + dataObject.get("first_name").getAsString());
            System.out.println("Last name" + dataObject.get("last_name").getAsString());
            System.out.println("Avatar:" + dataObject.get("avatar").getAsString());

        }
    }

    @Test
    public void listUsers() {
        APIResponse newIssue = request.get("/api/users");
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);
        JsonPrimitive page = json.getAsJsonPrimitive("page");
        JsonPrimitive perPage = json.getAsJsonPrimitive("per page");
        JsonPrimitive total = json.getAsJsonPrimitive("total");
        JsonPrimitive totalPage = json.getAsJsonPrimitive("totalPage");
        JsonArray dataArray = json.getAsJsonArray("data");
        JsonObject support = json.getAsJsonObject("support");
        Assert.assertTrue(newIssue.ok());

        Assert.assertEquals(dataArray.size(), 6);
        Assert.assertEquals(page.getAsInt(), 1);
        Assert.assertEquals(perPage.getAsInt(), 6);
        Assert.assertEquals(total.getAsInt(), 12);
        Assert.assertEquals(totalPage.getAsInt(), 2);
        Assert.assertTrue(support.has("url"));
        Assert.assertTrue(support.has("text"));

        for (JsonElement element : dataArray) {
            JsonObject dataObject = element.getAsJsonObject();

            Assert.assertTrue(dataObject.has("id"));
            Assert.assertTrue(dataObject.has("email"));
            Assert.assertTrue(dataObject.has("first_name"));
            Assert.assertTrue(dataObject.has("last name"));
            Assert.assertTrue(dataObject.has("avatar"));
        }
    }

    @Test
    public void singUser() {
        APIResponse newIssue1 = request.get("/api/user/2");
        JsonObject json1 = new Gson().fromJson(newIssue1.text(), JsonObject.class);
        JsonObject dataObject = json1.getAsJsonObject("data");
        JsonObject supportObject = json1.getAsJsonObject("support");

        Assert.assertTrue(newIssue1.ok());

        Assert.assertTrue(json1.has("data"));
        Assert.assertTrue(json1.has("support"));

        Assert.assertTrue(dataObject.has("id"));
        Assert.assertTrue(dataObject.has("email"));
        Assert.assertTrue(dataObject.has("first_name"));
        Assert.assertTrue(dataObject.has("last_name"));
        Assert.assertTrue(dataObject.has("avatar"));

        Assert.assertTrue(supportObject.has("url"));
        Assert.assertTrue(supportObject.has("text"));
    }

    @Test
    public void singleUserNotFound() {
        APIResponse newIssue2 = request.get("/api/users/23");
        JsonObject json2 = new Gson().fromJson(newIssue2.text(), JsonObject.class);

        Assert.assertEquals(newIssue2.status(), 404);
        Assert.assertEquals(json2.size(), 0);

    }





    @Test
    public void singleResources() {
        APIResponse newIssues4 = request.get("/api/unknown/2");
        JsonObject json = new Gson().fromJson(newIssues4.text(), JsonObject.class);
        JsonObject dataObect = json.getAsJsonObject("data");
        JsonObject supportObjekt = json.getAsJsonObject("support");


        Assert.assertTrue(newIssues4.ok());

        Assert.assertTrue(json.has("data"));
        Assert.assertTrue(json.has("support"));

        Assert.assertTrue(dataObect.has("id"));
        Assert.assertTrue(dataObect.has("name"));
        Assert.assertTrue(dataObect.has("year"));
        Assert.assertTrue(dataObect.has("color"));
        Assert.assertTrue(dataObect.has("pantone_value"));

        Assert.assertTrue(supportObjekt.has("url"));
        Assert.assertTrue(supportObjekt.has("text"));
    }

    @Test
    public void singleResourcesNotFound() {
        APIResponse newIssue = request.get("/api/unknown/23");
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);

        Assert.assertEquals(newIssue.status(), 404);
        Assert.assertEquals(json.size(), 0);
    }

    @Test
    public void create() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "morpheus");
        data.put("job", "leader");
        APIResponse newIssue = request.post("/api/users",
                RequestOptions.create().setData(data));
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);

        Assert.assertTrue(newIssue.ok());

        Assert.assertTrue(json.has("name"));
        Assert.assertTrue(json.has("job"));
        Assert.assertTrue(json.has("id"));
        Assert.assertTrue(json.has("createdAt"));
    }

    @Test
    public void update() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "morpheus");
        data.put("job", "zoin resident");
        APIResponse newIssue = request.put("/api/users/2",
                RequestOptions.create().setData(data));
        JsonObject json1 = new Gson().fromJson(newIssue.text(), JsonObject.class);

        Assert.assertTrue(newIssue.ok());

        Assert.assertTrue(json1.has("name"));
        Assert.assertTrue(json1.has("job"));
        Assert.assertTrue(json1.has("updatedAt"));
    }

    @Test
    public void updatedAt() {
        APIResponse newIssue = request.patch("/api/users/2");
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);

        Assert.assertTrue(newIssue.ok());
        Assert.assertTrue(json.has("updatedAt"));
    }

    @Test
    public void delete() {
        APIResponse newIssue = request.delete("api/users/2");

        Assert.assertEquals(newIssue.status(), 204);
    }

    @Test
    public void registerSuccessful() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@request.in");
        data.put("password", "pistol");
        APIResponse newIssue = request.post("api/register",
                RequestOptions.create().setData(data));
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);

        Assert.assertTrue(newIssue.ok());

        Assert.assertTrue(json.has("id"));
        Assert.assertTrue(json.has("token"));
    }


    @Test
    public void registerUnsuccessful() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "sydney@fife");
        APIResponse newIssue = request.post("/api/register",
                RequestOptions.create().setData(data));
        Assert.assertEquals(newIssue.status(), 400);

    }

    @Test
    public void loginSuccessful() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@reqres.in");
        data.put("password", "cityslicka");
        APIResponse newIssue = request.post("/api/login",
                RequestOptions.create().setData(data));
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);

        Assert.assertTrue(newIssue.ok());
        Assert.assertTrue(json.has("token"));
    }


    @Test
    public void loginUnsuccessful() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "peter@klaven");
        APIResponse newIssue = request.post("api/login",
                RequestOptions.create().setData(data));
        Assert.assertEquals(newIssue.status(), 400);
    }

    @Test
    public void delayedResponse() {
        APIResponse newIssue = request.get("/api/users");
        JsonObject json = new Gson().fromJson(newIssue.text(), JsonObject.class);
        JsonPrimitive page = json.getAsJsonPrimitive("page");
        JsonPrimitive perPage = json.getAsJsonPrimitive("per page");
        JsonPrimitive total = json.getAsJsonPrimitive("total");
        JsonArray dataArray = json.getAsJsonArray("data");
        JsonObject support = json.getAsJsonObject("support");
        Assert.assertTrue(newIssue.ok());

        Assert.assertEquals(dataArray.size(), 6);
        Assert.assertEquals(page.getAsInt(), 1);
        Assert.assertEquals(perPage.getAsInt(), 12);
        Assert.assertTrue(support.has("url"));
        Assert.assertTrue(support.has("text"));

        for (JsonElement element : dataArray) {
            JsonObject dataObject = element.getAsJsonObject();

            Assert.assertTrue(dataObject.has("id"));
            Assert.assertTrue(dataObject.has("email"));
            Assert.assertTrue(dataObject.has("first_name"));
            Assert.assertTrue(dataObject.has("last_name"));
            Assert.assertTrue(dataObject.has("avatar"));

        }


    }

}

