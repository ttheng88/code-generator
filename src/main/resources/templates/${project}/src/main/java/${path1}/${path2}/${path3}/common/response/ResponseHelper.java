package ${path1}.${path2}.${path3}.common.response;

import ${path1}.${path2}.${path3}.common.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @desc 返回工具类
 * @author ${author}
 * @date ${dateTime}
 */
public class ResponseHelper<T> {

    public ResponseEntity responseOriginal(T data) {
        return new ResponseEntity(data,HttpStatus.OK);
    }

    public static ResponseEntity<RestResult> successful() {
        return successful(null);
    }

    public static ResponseEntity successful(Object data) {
        RestResult result = new RestResult();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(Constants.HTTP_RES_CODE_200);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity<RestResult> failed(String message) {
        return failed(Constants.HTTP_RES_CODE_500, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<RestResult> failed(int code, String message) {
        return failed(code, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<RestResult> failed(int code, String message, HttpStatus httpStatus) {
        RestResult result = new RestResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(message);
        return new ResponseEntity(result, httpStatus);
    }

    public static ResponseEntity<RestResult> failed(String message, HttpStatus httpStatus) {
        return failed(Constants.HTTP_RES_CODE_500, message, httpStatus);
    }

}
