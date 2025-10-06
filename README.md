# iRunner API - 一个功能完备的跑步社交App后端服务

这是一个 Spring Boot 后端项目。它为一个虚构的跑步社交应用 `iRunner` 提供了全部核心后端功能。

**线上API文档 (由 Vercel 部署): [点击这里访问](https://i-runner-api-docs.vercel.app/)** 
*(这是一个示例链接，我们将在第二部分创建你自己的链接)*

---

## ✨ 项目核心功能

这个项目完整地实现了三大核心模块的功能：

#### **1. 👤 用户中心 (User Center)**
- [x] **用户注册**: 使用 md5 加密存储密码。
- [x] **用户登录**: 基于 Session 的会话管理。
- [x] **信息查询**: 获取当前登录用户的信息。
- [x] **信息更新**: 用户可以更新自己的昵称、头像等个人资料。

#### **2. 🏃 运动记录 (Activity)**
- [x] **上传记录**: 用户可以上传一次完整的跑步数据，包括距离、时长、开始时间以及 **GPS轨迹点 (JSON存储)**。
- [x] **历史列表**: 分页获取用户的个人跑步历史，按时间倒序。
- [x] **单次详情**: 获取某一次跑步的完整数据，用于在地图上绘制轨迹。

#### **3. 💬 社区动态 (Post)**
- [x] **发布动态**: 用户可以发布纯文字、带图片或关联某次跑步记录的动态。
- [x] **动态列表**: **(联表查询)** 分页获取社区的公共动态流，同时展示作者的昵称和头像。
- [x] **删除动态**: **(逻辑删除)** 用户可以安全地删除自己的动态，数据并不会从数据库中物理移除。

---

## 🛠️ 技术栈 (Technology Stack)

*   **核心框架**: Spring Boot 2.6.13
*   **数据库交互**: MyBatis-Plus
*   **数据库**: MySQL 5.7
*   **API文档**: SpringDoc (OpenAPI 3 / Swagger UI)
*   **工具库**: Lombok, Gson, Commons Lang3

---

## 🚀 如何在本地运行

1.  **克隆仓库**:
    ```bash
    git clone https://github.com/yclin30/YOUR_REPO_NAME.git
    cd YOUR_REPO_NAME
    ```

2.  **配置数据库**:
    - 创建一个名为 `lyc` 的 MySQL 数据库。
    - 将项目中的 `*.sql` 文件导入到数据库中以创建表结构。
    - 修改 `src/main/resources/application.yml` 文件，更新你的数据库 `username` 和 `password`。

3.  **运行项目**:
    - 使用你的 IDE (如 IntelliJ IDEA) 打开项目，等待 Maven 依赖下载完成。
    - 找到 `UserCenterBackendApplication.java` (或你的主启动类) 并运行。

4.  **访问服务**:
    - 后端服务运行在 `http://localhost:8080`。
    - API 文档 (Swagger) 位于 `http://localhost:8080/swagger-ui.html`。

---

## 📝 API 接口概览

完整的、可交互的 API 文档请访问顶部的 **线上API文档链接**。

### 用户模块 (`/user`)
- `POST /user/register`: 注册
- `POST /user/login`: 登录
- `GET /user/current`: 获取当前用户
- `POST /user/update`: 更新信息

### 运动模块 (`/activity`)
- `POST /activity/add`: 上传运动记录
- `GET /activity/list`: 分页获取历史记录
- `GET /activity/detail/{id}`: 获取单次详情

### 社区模块 (`/post`)
- `POST /post/add`: 发布动态
- `GET /post/list`: 分页获取动态列表
- `DELETE /post/delete/{id}`: 删除动态
