package school.module.entity;

public class Info {
    private Integer id;

    private String code;

    private String info;

    public Info(Integer id, String code, String info) {
        this.id = id;
        this.code = code;
        this.info = info;
    }

    public Info() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}