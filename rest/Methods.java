package rest;

import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Methods {

	private static final String String = null;

	public static void setBaseUrl(String URL) {

		if (!URL.isEmpty()) {
			RestAssured.baseURI = URL;
		}
	}

	public static RequestSpecification authorization(IAuth Icontext, String token) {

		Context context = new Context();
		RequestSpecification reqSpec;
		if (Icontext == null) {
			reqSpec = RestAssured.given();
		} else {
			reqSpec = Icontext.auth(token);
		}
		return reqSpec;
	}

	private static RequestSpecification init(Context context) {

		setBaseUrl(context.baseURL);
		RequestSpecification reqSpec = authorization(context.auth, context.baererToken);

		if (!context.pathParams.isEmpty()) {
			reqSpec.pathParams(context.pathParams);
		}
		if (!context.formParams.isEmpty()) {
			reqSpec.formParams(context.formParams);
		}
		if (!context.queryParams.isEmpty()) {
			reqSpec.queryParams(context.queryParams);
		}
		if (context.requestContentType != null && !context.requestContentType.equals("")) {
			reqSpec.contentType(context.requestContentType.getContentType());
		}
		return reqSpec;
	}

	public static Response GET(Context context) {

		Response resp = null;
		RequestSpecification reqSpec = init(context);

		if (!context.requestHeaderParams.isEmpty()) {
			for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
				reqSpec.header(header.getKey(), header.getValue());
			}
		}
		if (context.URI != null && !context.URI.equals("")) {
			resp = reqSpec.get(context.URI);
		}
		return resp;
	}

	public static Response POST(Context context) throws JSONException {

		Response resp = null;
		RequestSpecification reqSpec = init(context);

		if (context.requestBody != null) {
			reqSpec.body(context.requestBody.toString());
		}
		if (!context.multiParts.isEmpty()) {
			for (Map.Entry<String, Object> multi : context.multiParts.entrySet()) {
				reqSpec.multiPart(multi.getKey(), multi.getValue());
			}
		}
		if (!context.requestHeaderParams.isEmpty()) {
			for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
				reqSpec.header(header.getKey(), header.getValue());
			}
		}
		if (!context.formParams.isEmpty()) {
			reqSpec.formParams(context.formParams);
		}

		if (context.URI != null && !context.URI.equals("")) {
			resp = reqSpec.post(context.URI);
		} else {
			resp = reqSpec.post();
		}
		return resp;
	}

	public static String createRandomToken(int size) {

		Random r = new Random();
		String token = "";
		String a = "0123456789";
		String b = "qwertyuioplkjhgfdsazxcvbnm";
		String c = b.toUpperCase();
		String d = a + b + c;
		String[] chars = d.split("");
		for (int x = 0; x <= size; x++) {
			token = token + chars[r.nextInt(chars.length)];
		}

		return token;
	}

}
