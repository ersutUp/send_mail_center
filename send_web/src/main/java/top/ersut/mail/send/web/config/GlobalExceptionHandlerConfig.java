package top.ersut.mail.send.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.ersut.mail.send.web.exception.code.CodeException;
import top.ersut.mail.send.model.common.result.Result;
import top.ersut.mail.send.model.common.result.code.ResultCode;
import top.ersut.mail.send.model.common.result.code.SystemResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 全局异常处理
 * @author 王二飞
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler(value = CodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result codeExceptionHandler(HttpServletRequest request, CodeException codeException) {
        //从异常中获取状态码
        ResultCode resultCode = codeException.getResultCode();

        log.info(codeException.getMessage(),codeException);

        return new Result(resultCode);
    }


    /**
     * 对 @Validated 验证失败的处理
     * body pararm
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class,BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result methodArgumentNotValidExceptionHandler(HttpServletRequest request, BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String errorMsg = errorMsg(bindingResult);
        log.info("param valid faid",exception);
        return new Result(SystemResultCode.PARAM_ERROR,errorMsg);
    }


    /**
     * 其他异常处理
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Object defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("unknown error",e);
        return new Result(SystemResultCode.UNKNOWN_ERROR);
    }

    /**
     * 获取验证注解的 message
     * @param bindingResult
     * @return
     */
    private String errorMsg(BindingResult bindingResult){
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        builder.append(fieldErrors.get(0).getDefaultMessage());
        return builder.toString();
    }
}
