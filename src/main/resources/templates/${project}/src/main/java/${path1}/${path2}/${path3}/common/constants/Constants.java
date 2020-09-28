package ${path1}.${path2}.${path3}.common.constants;

/**
 * @desc 常量
 * @author ${author}
 * @date ${dateTime}
 */
public interface Constants {
    /**
     * 响应请求成功code
     */
    Integer HTTP_RES_CODE_200 = 200;
    /**
     * 系统错误code
     */
    Integer HTTP_RES_CODE_500 = 500;

    /**
     * 系统错误提示信息
     */
    String HTTP_RES_CODE_500_VALUE = "服务器正在繁忙，请稍后再试!";
}
