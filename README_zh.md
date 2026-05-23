# ERP 管理系统

基于 Spring Boot 3 + Vue 3 的企业资源计划管理系统，覆盖核心业务全流程。

## 功能模块

| 模块 | 功能 |
|---|---|
| **系统管理** | 用户、角色、菜单、字典、操作日志 |
| **库存管理** | 商品、分类、仓库、入库/出库、库存流水 |
| **采购管理** | 供应商、采购订单、采购入库、采购退货 |
| **销售管理** | 客户、销售订单、销售出库、销售退货 |
| **财务管理** | 应收/应付账款、收款单/付款单、费用管理 |
| **生产管理** | BOM 物料清单、生产工单、工艺路线、工序报工 |
| **人力资源管理** | 部门、员工档案、考勤、薪资 |
| **客户关系管理** | 销售线索、商机、联系人、跟进记录 |

## 技术栈

| 层级 | 技术 |
|---|---|
| 后端 | Spring Boot 3.2, MyBatis Plus 3.5, Spring Security, JWT |
| 前端 | Vue 3, Element Plus, Pinia, Vue Router 4, Axios |
| 数据库 | MySQL 8（Docker），自动初始化表结构 |
| 构建 | Maven（多模块），Vite 8 |

## 快速开始

### 环境要求

- Java 17+
- Node.js 20+
- Docker Desktop（用于 MySQL）或本地 MySQL 8 实例

### 1. 启动 MySQL

```bash
docker compose up -d
```

首次启动时 `init.sql` 会自动执行建表。同时 `DataInitializer` 在数据库为空时自动初始化默认管理员账号和菜单数据。

### 2. 启动后端

```bash
cd erp-backend
mvn spring-boot:run -pl erp-admin
```

API 文档地址：http://localhost:8080/doc.html

### 3. 启动前端

```bash
cd erp-web
npm install
npm run dev
```

打开浏览器访问 http://localhost:3000

### 默认登录

- 用户名：`admin`
- 密码：`123456`

## 项目结构

```
erp-system/
├── erp-backend/          # Maven 多模块 Spring Boot 项目
│   ├── erp-common/       # 公共 DTO、异常处理、常量
│   ├── erp-framework/    # 安全配置、JWT、MyBatis Plus 配置
│   ├── erp-system/       # RBAC 权限管理
│   ├── erp-inventory/    # 库存管理
│   ├── erp-purchase/     # 采购管理
│   ├── erp-sales/        # 销售管理
│   ├── erp-finance/      # 财务管理
│   ├── erp-production/   # 生产管理
│   ├── erp-hr/           # 人力资源管理
│   ├── erp-crm/          # 客户关系管理
│   └── erp-admin/        # 应用入口 + DataInitializer
├── erp-web/              # Vue 3 前端
├── docker-compose.yml    # MySQL 8 容器配置
├── init.sql              # 数据库全量建表 + 初始数据
└── AGENTS.md             # AI 助手快速上手指南
```

## 架构特点

- **RBAC 权限**：基于角色的访问控制，后端 `@PreAuthorize` + 前端 `v-hasPerm` 指令实现按钮级鉴权
- **JWT 认证**：无状态 Token 认证，通过 `Authorization: Bearer <token>` 请求头传递
- **逻辑删除**：主要实体均使用 `delFlag` 字段（`0`=正常，`1`=删除）
- **自动填充**：`createTime`、`updateTime`、`createBy`、`updateBy` 由 MyBatis Plus 自动填充
- **数据初始化**：首次启动时自动创建管理员、角色、菜单
- **库存联动**：采购入库和销售出库自动更新库存数量

## License

MIT
