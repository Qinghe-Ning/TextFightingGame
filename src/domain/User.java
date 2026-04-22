package domain;

import java.util.Random;

/**
 * 用户实体类
 * 用于存储用户账号信息，包括用户名、密码和账号状态
 */
public class User {
    private String id;          // 用户唯一标识ID
    private String username;    // 用户名
    private String password;    // 密码
    private boolean status;     // 账号状态，true为正常，false为锁定

    /**
     * 默认构造函数
     * 自动生成用户ID，初始化账号状态为正常
     */
    public User(){
        id = createId();
        status = true;
    }

    /**
     * 带参构造函数
     * @param username 用户名
     * @param password 密码
     */
    public User(String username, String password){
        id = createId();
        this.username = username;
        this.password = password;
        status = true;
    }

    /**
     * 生成用户唯一ID
     * 格式：TextFightGame + 5位随机数字
     * @return 生成的唯一ID字符串
     */
    public String createId(){
        StringBuilder sb = new StringBuilder("TextFightGame");

        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }

        return sb.toString();
    }

    // ========== Getter和Setter方法 ==========

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
