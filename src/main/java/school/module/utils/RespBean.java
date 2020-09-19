package school.module.utils;

/**
 * 描述：用于简单的操作，封装返回请求的结果和返回信息
 *
 * @Author: Lsl_Mercury
 * @Date: 2020-09-18  23:29
 */

public class RespBean {
    private String status;
    private String msg;

    public RespBean() {
    }

    public RespBean(String status, String msg) {

        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
