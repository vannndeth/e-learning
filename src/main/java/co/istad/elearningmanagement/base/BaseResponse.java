package co.istad.elearningmanagement.base;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Accessors(chain = true)
@Data
public class BaseResponse<T> {

    private T payload;
    private String message;
    private int status;

    public static <T> BaseResponse<T> createSuccess(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.CREATED.value()).setMessage(message);
    }

    public static <T> BaseResponse<T> ok(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.OK.value()).setMessage(message);
    }

    public static <T> BaseResponse<T> notFound(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.NOT_FOUND.value()).setMessage(message);
    }

    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.BAD_REQUEST.value()).setMessage(message);
    }

    public static BaseResponse<?> internalServerError(String message) {
        return new BaseResponse<>().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(message);
    }
}
