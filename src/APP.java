import ui.Login;

/**
 * 游戏主入口类
 * 负责启动整个应用程序
 */
public class APP {
    public static void main(String[] args) {
        // 创建登录界面并启动
        Login l = new Login();
        l.start();
    }
}
