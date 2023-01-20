import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresInHomeWorkTest {

    @Test
    void getUserFirstNameTest() {
        given()
                .log().uri()
        .when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .log().status()
                .statusCode(200)
                .body("data.first_name", hasItem("Tobias"));
    }

    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2),
                        "data.email", is("janet.weaver@reqres.in"),
                        "data.first_name", is("Janet"),
                        "data.last_name", is("Weaver"));
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void getResourceIdListSizeTest() {
        Response resJson = given().get("https://reqres.in/api/unknown");
        JsonPath js = resJson.jsonPath();

        int size = js.getInt("data.size()");
        assertThat(size).isEqualTo(6);

        List<Integer> listId = resJson.jsonPath().getList("data.id");
        for (Integer it : listId) {
            System.out.println("iterate: " + it);
        }
    }

    @Test
    void postCreateNewUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .log().body()
                .log().status()
                .statusCode(201)
                .body("name", is("morpheus"), "job", is("leader"));
    }

    @Test
    void putCreateNewUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("name", is("morpheus"), "job", is("zion resident"));
    }

    @Test
    void deleteUserTest() {
        given()
                .log().uri()
        .when()
                .log().body()
                .delete("https://reqres.in/api/users/2")
        .then()
                .log().body()
                .log().status()
                .statusCode(204);
    }

    @Test
    void postRegisterTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postRegisterUnsuccessfulTest() {
        given()
                .log().uri()
                .contentType(JSON)
                .body("{ \"email\": \"sydney@fife\" }")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void postSuccessfulLoginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postUnsuccessfulLoginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void getDelayTest() {
        given()
                .log().uri()
        .when()
                .get("https://reqres.in/api/users?delay=3")
        .then()
                .time(lessThan(4000L))
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }
}
