# 文字格斗游戏 (TextFightingGame)

一个基于命令行的文字格斗游戏，玩家可以注册登录、创建角色、与各种敌人进行回合制战斗。

## 项目结构

```
TextFightingGame/
└── src/
    ├── APP.java                 # 游戏主入口
    ├── domain/                  # 领域模型
    │   ├── Character.java       # 角色基类
    │   ├── HeroCharacter.java   # 玩家角色类
    │   ├── EnemyCharacter.java  # 敌人类
    │   └── User.java            # 用户实体类
    └── ui/                      # 用户界面
        ├── Login.java           # 登录/注册界面
        └── FightingGame.java    # 战斗游戏核心
```

## 游戏功能

### 1. 用户系统
- **注册**：用户名3-16字符，密码3-8字符，需同时包含字母和数字
- **登录**：验证用户名、密码和验证码
- **锁定机制**：密码连续错误3次将锁定账号

### 2. 角色系统
- 玩家可自由分配20点属性：
  - 生命值：每点 +10 HP（基础100）
  - 攻击力：每点 +2 ATK（基础10）
  - 防御力：每点 +1 DEF（基础0）
- 初始拥有3个技能：
  - 普通攻击
  - 强力一击（消耗10HP，1.8倍伤害）
  - 生命汲取（消耗10HP，恢复0-20HP）

### 3. 战斗系统
- 回合制战斗，双方交替攻击
- 4种敌人类型：
  - 初级战士（技能：猛击）
  - 敏捷刺客（技能：快速攻击）
  - 重装坦克（技能：防御姿态）
  - 神秘法师（技能：火球术）
- 每场战斗结束后恢复20-40点生命值
- 连胜3场触发属性提升奖励

## 运行方式

### 命令行运行

```bash
cd src
javac -encoding UTF-8 *.java
java -Dfile.encoding=UTF-8 APP
```

### Windows PowerShell

```powershell
chcp 65001
cd src
javac -encoding UTF-8 *.java
java -Dfile.encoding=UTF-8 APP
```

### IDE运行

在 IntelliJ IDEA 或 Eclipse 中直接运行 `APP.java` 的 main 方法，建议在运行配置中添加 VM 参数：`-Dfile.encoding=UTF-8`

## 游戏流程

1. 选择「注册」创建账号
2. 输入用户名和密码登录
3. 分配角色属性点
4. 选择技能进行战斗
5. 击败敌人累积胜场
6. 继续战斗或退出

## 注意事项

- 运行前确保已安装 JDK 8+
- 为避免中文乱码，请务必使用 UTF-8 编码运行