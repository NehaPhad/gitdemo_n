import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;


public class Part1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI=("https://rahulshettyacademy.com");
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(body.json_body.AddPlace()).when().post("/maps/api/place/add/json").then().log().all()
		.assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		
		JsonPath js = new JsonPath(response);//for parsing jason(converting from string to Json)
		String placeId= js.getString("place_id");
		
		System.out.println(placeId);
		//Update Address 
		String newAddress = "B-17,Mauli Niwas";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "      \"place_id\": \""+placeId+"\",\r\n"
				+ "        \"address\": \""+newAddress+"\",\r\n"
				+ "      \"key\":\"qaclick123\"\r\n"
				+ "}").when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//Get Address
		String GetAddress = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println(GetAddress);
	
		JsonPath js1 = new JsonPath(GetAddress);//for parsing jason(converting from string to Json)
		String Actualaddress= js1.getString("address");
		System.out.println(Actualaddress);
		Assert.assertEquals(Actualaddress, newAddress);
		
	}

}
