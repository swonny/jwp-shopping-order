package cart.ui;

import cart.config.ControllerTestConfig;
import cart.dto.product.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static cart.fixture.ProductFixture.계란;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

class ProductApiControllerTest extends ControllerTestConfig {

    Long 상품_계란_등록() {
        return jdbcProductRepository.createProduct(계란);
    }

    @Test
    void getAllProducts() {
        상품_계란_등록();

        given(spec)
                .log().all()
                .filter(document("{method-name}",
                        responseFields(
                                fieldWithPath("[].id").description("상품 식별자값"),
                                fieldWithPath("[].name").description("상품명"),
                                fieldWithPath("[].price").description("상품 가격"),
                                fieldWithPath("[].imageUrl").description("상품 이미지 주소")
                        )))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void getProductById() {
        Long 계란_아이디 = 상품_계란_등록();

        given(spec)
                .log().all()
                .filter(document("{method-name}",
                        responseFields(
                                fieldWithPath("id").description("상품 식별자값"),
                                fieldWithPath("name").description("상품명"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("imageUrl").description("상품 이미지 주소")
                        )))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get("/products/" + 계란_아이디)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void createProduct() {
        given(spec)
                .log().all()
                .filter(document("{method-name}",
                        requestFields(
                                fieldWithPath("name").description("상품명"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("imageUrl").description("상품 이미지 주소")
                        )))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .body(new ProductRequest("계란", 1000, "https://계란_이미지_주소.png"))
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void updateProduct() {
        final Long 상품_계란_식별자값 = 상품_계란_등록();

        given(spec)
                .log().all()
                .filter(document("{method-name}",
                        requestFields(
                                fieldWithPath("name").description("상품명"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("imageUrl").description("상품 이미지 주소")
                        )))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .body(new ProductRequest("수정된 계란", 1000, "https://계란_이미지_주소.png"))
                .pathParam("id", 상품_계란_식별자값)
                .put("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
