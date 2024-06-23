package rest;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

public class OAuth2 implements IAuth {

	@Override
	public RequestSpecification auth(String token) {

		RequestSpecification reqSpec = RestAssured.given().auth().oauth2(token);
		return reqSpec;
	}

}
