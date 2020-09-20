package school.module.entity;

public class User {
    private Integer id;

    private String account;

    private String password;

    private String tokenVersion;

    public User(Integer id, String account, String password, String tokenVersion) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.tokenVersion = tokenVersion;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(String tokenVersion) {
        this.tokenVersion = tokenVersion == null ? null : tokenVersion.trim();
    }
}