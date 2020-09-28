package ${path1}.${path2}.${path3}.common.response;

import lombok.Data;

/**
 * @desc 返回报文实体
 * @author ${author}
 * @date ${dateTime}
 */
@Data
public class RestResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 返回描述
     */
    private String msg;
    /**
     * 返回内容
     */
    private Object data;
}
