package com.invoker.mylibrary.http;

/**
 * Created by invoker on 2018-05-09
 * Description: 如果成功则把结果Observable<T>发射给订阅者。
 * 反之则把code交给ApiException并返回一个异常，ApiException中我们对code进行相应的处理并返回对应的错误信息
 */
public class ApiException extends RuntimeException {

    public static final int USER_NO_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    private static String message;

    public ApiException(int resultaCode) {
        this(getApiExceptionMessage(resultaCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        switch (code) {
            case USER_NO_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            case 1000:
                message = "取消dialog";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }

}
