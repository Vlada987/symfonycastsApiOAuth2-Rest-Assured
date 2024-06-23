package tests;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import io.restassured.response.Response;
import rest.Context;
import rest.EContentType;
import rest.IAuth;
import rest.Methods;
import rest.OAuth2;

@Listeners(tests.Listeners.class)
public class TestClass {

	String baseUrl = "http://coop.apps.symfonycasts.com";
	String userID = "4999";
	String client_secret = "b9287934451400765035d9caa36737a44";
	String token;

	@Test(priority = 1, groups = "positive")
	public void test01_GetTheOAuthToken() throws JSONException {

		Context context = new Context();
		context.baseURL = baseUrl;
		context.URI = "/token";
		Map<String, Object> forms = new HashMap<>();
		forms.put("client_id", "22jun24");
		forms.put("client_secret", client_secret);
		forms.put("grant_type", "client_credentials");
		forms.put("client_id", "22jun24");
		context.formParams = forms;
		Response resp = Methods.POST(context);
		token = resp.jsonPath().get("access_token").toString();
		int expireIn = resp.jsonPath().get("expires_in");

		Assert.assertTrue(resp.statusCode() == 200);
		Assert.assertTrue(expireIn == 86400);
	}

	@Test(priority = 2, groups = "positive")
	public void test02_Authenticate() throws JSONException {

		Context context = new Context();

		context.baseURL = baseUrl;
		context.URI = "/api/" + userID + "/chickens-feed";
		context.auth = new OAuth2();
		context.baererToken = token;
		Response resp = Methods.POST(context);

		Assert.assertTrue(resp.statusCode() == 200);
		Assert.assertTrue(resp.jsonPath().getBoolean("success") == true);
		Assert.assertTrue(resp.jsonPath().get("message").toString().contains("full and happy"));
	}

	@Test(groups = "negative")
	public void test03_AuthenticateFail() throws JSONException {

		Context context = new Context();

		context.baseURL = baseUrl;
		context.URI = "/api/" + userID + "/chickens-feed";
		context.auth = new OAuth2();
		context.baererToken = Methods.createRandomToken(20);
		Response resp = Methods.POST(context);

		Assert.assertTrue(resp.statusCode() == 401);
		Assert.assertTrue(resp.jsonPath().get("error").toString().equals("invalid_token"));

	}

}
