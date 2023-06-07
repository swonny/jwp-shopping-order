package cart.exception;

import static cart.domain.product.Product.MAXIMUM_NAME_LENGTH;
import static cart.domain.product.Product.MINIMUM_NAME_LENGTH;
import static cart.domain.vo.Price.MINIMUM_PRICE;

public class ProductException extends RuntimeException {

    public ProductException(String message) {
        super(message);
    }

    public static class InvalidImageUrl extends ProductException {

        public InvalidImageUrl() {
            super("잘못된 이미지 url 형식입니다." + "'https://' 형태의 주소를 입력해주세요.");
        }
    }

    public static class InvalidIdByNull extends ProductException {

        public InvalidIdByNull() {
            super("상품 아이디를 입력해야 합니다.");
        }
    }

    public static class NotFound extends ProductException {

        public NotFound() {
            super("찾는 상품이 없습니다.");
        }
    }

    public static class InvalidPrice extends ProductException {

        public InvalidPrice() {
            super("상품 가격은 " + MINIMUM_PRICE + "원 이상이어야 합니다.");
        }
    }

    public static class InvalidNameLength extends ProductException {

        public InvalidNameLength() {
            super("상품 이름은 " + MINIMUM_NAME_LENGTH + "자 이상 " + MAXIMUM_NAME_LENGTH + "자 이하여야합니다.");
        }
    }
}
