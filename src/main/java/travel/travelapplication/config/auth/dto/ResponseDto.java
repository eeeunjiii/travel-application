package travel.travelapplication.config.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    private HttpStatus status;
    private String message;
    private T data; // result data

    public ResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static<T> ResponseDto<T> res(final HttpStatus status, String message) {
        return res(status, message, null);
    }

    public static<T> ResponseDto<T> res(HttpStatus status, String message, T t) {
        return ResponseDto.<T>builder()
                .status(status)
                .message(message)
                .data(t)
                .build();
    }
}
