package ui;

import domain.User;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 登录与注册界面类
 * 处理用户的登录、注册和退出功能
 * 包含用户验证、验证码校验和账号锁定机制
 */
public class Login {

    /**
     * 启动登录系统
     * 显示主菜单，提供登录、注册、退出三个选项
     */
    public void start() {

        // 用户列表，存储已注册的用户（内存中）
        ArrayList<User> users = new ArrayList<>();

        // 主循环，持续显示菜单直到用户退出
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("—————————————————————————————————");
            System.out.println("|    🎮欢迎来到文字格斗游戏🎮    |");
            System.out.println("—————————————————————————————————");
            System.out.println("|  请选择: 1.登录 2.注册 3.退出   |");
            System.out.println("—————————————————————————————————");

            Scanner sc = new Scanner(System.in);
            System.out.print("请选择: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> login(users);
                case 2 -> register(users);
                case 3 -> exit();
                default -> System.out.println("输入错误，请重新输入！！！");
            }
        }
    }

    /**
     * 用户登录功能
     * 验证用户名和密码，包含验证码校验和账号锁定机制
     * @param users 用户列表
     */
    public void login(ArrayList<User> users) {
        Scanner sc = new Scanner(System.in);
        String username;

        System.out.println("—————————————————————————————————");
        System.out.println("|          🎮登录页面🎮         |");
        System.out.println("—————————————————————————————————");
        System.out.print("请输入用户名: ");
        username = sc.next();

        // 检查用户是否存在
        if (!contains(users, username)) {
            System.out.println("用户:  "+username+"  不存在！！！");
            return;
        }

        // 获取用户对象
        int index = findIndex(users, username);
        User user = users.get(index);

        // 检查账号是否被锁定
        if(!user.isStatus()){
            System.out.println("用户:  "+username+"  已被锁定！！！");
            return;
        }

        // 最多3次密码输入机会
        for (int i = 0; i < 3; i++) {
            System.out.print("请输入密码: ");
            String password = sc.next();

            // 验证码校验循环
            while (true) {
                String rightCode = getCode();
                System.out.println("验证码为："+rightCode);
                System.out.print("请输入验证码：");
                String code = sc.next();
                if(!code.equalsIgnoreCase(rightCode)){
                    System.out.println("验证码输入错误！！！");
                }else{
                    break;
                }
            }

            // 验证密码
            if(!user.getPassword().equals(password)){
                System.out.println("密码输入错误！！！");
                if(i==2){
                    // 密码错误3次，锁定账号
                    user.setStatus(false);
                    System.out.println("密码错误次数过多，用户  "+username+"  已被锁定！！！");
                }else{
                    System.out.println("你还有  "+(2-i)+"  次机会！！！");
                }
            }else{
                // 登录成功，进入游戏
                FightingGame fg = new FightingGame();
                fg.gameStart(username);
                break;
            }
        }
    }

    /**
     * 用户注册功能
     * 验证用户名和密码的合法性
     * @param users 用户列表
     */
    public void register(ArrayList<User> users) {
        User user = new User();
        Scanner sc = new Scanner(System.in);

        // 用户名输入循环
        while (true) {

            System.out.println("—————————————————————————————————");
            System.out.println("|          🎮注册页面🎮         |");
            System.out.println("—————————————————————————————————");
            System.out.print("请输入要注册的用户名: ");
            String username = sc.next();

            // 检查用户名长度
            if (checkLength(username, 3, 16)) {
                System.out.println("用户名输入非法：用户名长度必须在3-16个字符之间！！！");
                continue;
            }
            // 检查用户名格式
            if (checkText(username, "username")) {
                System.out.println("用户名输入非法：用户名只能包含数字、字母且不能是纯数字！！！");
                continue;
            }
            // 检查用户名是否已存在
            if (contains(users, username)) {
                System.out.println("该用户名已存在，请重新输入！！！");
                continue;
            }
            user.setUsername(username);
            break;
        }

        // 密码输入循环
        while (true) {
            System.out.print("请输入密码: ");
            String password1 = sc.next();
            // 检查密码长度
            if (checkLength(password1, 3, 8)) {
                System.out.println("密码输入非法：密码长度必须在3-8个字符之间！！！");
                continue;
            }
            // 检查密码格式
            if (checkText(password1, "password")) {
                System.out.println("密码输入非法：密码需要同时包含字母、数字且不能有特殊字符！！！");
                continue;
            }
            System.out.print("请再次输入密码: ");
            String password2 = sc.next();
            // 验证两次密码是否一致
            if (!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致，请重新输入！！！");
                continue;
            }
            user.setPassword(password1);
            break;
        }

        // 添加用户到列表
        users.add(user);
        System.out.println("—————————————————————————————————");
        System.out.println("|     🎮用户注册成功！！！🎮     |");
        System.out.println("—————————————————————————————————");
    }

    /**
     * 退出系统
     */
    public void exit() {
        System.out.println("再见！！！期待您的下次游戏！！！");
        System.exit(0);
    }

    /**
     * 检查字符串长度是否在指定范围内
     * @param str 要检查的字符串
     * @param min 最小长度
     * @param max 最大长度
     * @return 超出范围返回true，否则返回false
     */
    public boolean checkLength(String str, int min, int max) {
        return str.length() < min || str.length() > max;
    }

    /**
     * 检查文本格式是否合法
     * @param str 要检查的字符串
     * @param name 检查类型（username或password）
     * @return 格式非法返回true，合法返回false
     */
    public boolean checkText(String str, String name) {
        int numCount = 0;    // 数字计数
        int charCount = 0;   // 字母计数
        int otherCount = 0;  // 其他字符计数

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                numCount++;
            } else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                charCount++;
            } else {
                otherCount++;
            }
        }

        if (name.equals("username")) {
            // 用户名：不能是纯数字，至少一个字母，无特殊字符
            return numCount < 0 || charCount <= 0 || otherCount != 0;
        } else if (name.equals("password")) {
            // 密码：必须同时包含数字和字母，无特殊字符
            return numCount <= 0 || charCount <= 0 || otherCount != 0;
        } else {
            return true;
        }
    }

    /**
     * 检查用户名是否已存在
     * @param users 用户列表
     * @param username 要检查的用户名
     * @return 存在返回true，否则返回false
     */
    public boolean contains(ArrayList<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 生成随机验证码
     * 格式：4个随机字母（大小写）+ 1个随机数字（插入到任意位置）
     * @return 生成的验证码字符串
     */
    public static String getCode() {
        ArrayList<Character> code = new ArrayList<>();
        // 添加大小写字母
        for (char i = 0; i < 26; i++) {
            code.add((char) (i + 'a'));
            code.add((char) (i + 'A'));
        }
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        // 生成4个随机字母
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(code.size());
            char c = code.get(index);
            sb.append(c);
        }
        // 在随机位置插入一个数字
        sb.insert(r.nextInt(5), r.nextInt(10));
        return sb.toString();
    }

    /**
     * 查找用户索引位置
     * @param users 用户列表
     * @param username 用户名
     * @return 用户索引，未找到返回-1
     */
    public int findIndex(ArrayList<User> users, String username){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }
}
