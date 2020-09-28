package ${path1}.${path2}.${path3}.common.exception;

import ${path1}.${path2}.${path3}.common.constants.Constants;
import ${path1}.${path2}.${path3}.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc 异常处理
 * @author ${author}
 * @date ${dateTime}
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {


    /**
     * 统一异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        log.error(Constants.HTTP_RES_CODE_500_VALUE,e);
        return ResponseHelper.failed(Constants.HTTP_RES_CODE_500_VALUE);
    }


    /**
     * 请求参数合法性校验异常 @RequestParam上validate失败后抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" | "));
        return ResponseHelper.failed(412,message,HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * 请求参数合法性校验异常 @RequestBody上validate失败后抛出的异常
     * @param bindException
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, NestedServletException.class})
    public ResponseEntity validExceptionHandler(MethodArgumentNotValidException bindException){
        bindException.printStackTrace();
        BindingResult result = bindException.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            String message = errors.stream().map(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                return fieldError.getDefaultMessage();
            }).collect(Collectors.joining(" | "));
            return ResponseHelper.failed(412,message,HttpStatus.PRECONDITION_FAILED);
        }
        return ResponseHelper.failed(Constants.HTTP_RES_CODE_500_VALUE);
    }



}
