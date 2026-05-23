# ERP System — Agent Guide

## Stack

- **Backend**: Spring Boot 3.2 + MyBatis Plus 3.5.5 + Java 17, Maven multi-module
- **Frontend**: Vue 3 + Element Plus + Vite 8 + Pinia + Vue Router 4
- **Database**: MySQL 8 (Docker), schema in `init.sql` (auto-loaded on first DB start)
- **Auth**: JWT (jjwt 0.12) + Spring Security, stateless, token in `Authorization: Bearer <token>`
- **Database auth**: auto-initialized via `DataInitializer` (admin/123456) if DB is empty

## Project structure

```
C:\Users\Wesley\erp-system\
├── docker-compose.yml          # MySQL container (root/root123, port 3306)
├── init.sql                    # Full schema + seed menus (optional, DataInitializer also seeds menus)
├── setup.ps1                   # Setup script (install maven, build both)
│
├── erp-backend/                # Maven multi-module (root pom)
│   ├── erp-common/             # Result<T>, PageResult, constants, global exception handler
│   ├── erp-framework/          # JWT util, SecurityConfig, MyBatisPlusConfig, Knife4j config
│   ├── erp-system/             # user/role/menu/dict/log CRUD, login controller
│   ├── erp-inventory/          # product/category/warehouse/stock/stock-record
│   ├── erp-purchase/           # supplier/purchase-order/inbound/return
│   ├── erp-sales/              # customer/sales-order/outbound/return
│   ├── erp-finance/            # receivable/payable/receipt/payment/expense
│   ├── erp-production/         # bom/production-order/process-route/work-report
│   ├── erp-hr/                 # dept/employee/attendance/payroll
│   ├── erp-crm/                # lead/opportunity/contact/follow-up
│   └── erp-admin/              # @SpringBootApplication entrypoint + DataInitializer
│
└── erp-web/                    # Vite + Vue 3
    ├── src/api/                # Axios request per module (system/purchase/sales/finance/etc.)
    ├── src/views/              # One .vue per entity, under module subdir
    ├── src/router/             # Static routes (dynamic menu from backend)
    ├── src/store/              # Pinia: user, app, menu
    └── src/utils/              # Axios instance with JWT interceptor + v-hasPerm directive
```

## Key commands

```bash
# Start MySQL
docker compose up -d

# Backend (requires Maven in PATH)
cd erp-backend
mvn spring-boot:run -pl erp-admin

# Frontend
cd erp-web
npm run dev     # http://localhost:3000

# Build frontend for production
npm run build

# Backend Build (skip tests since there are none)
mvn clean install -DskipTests
```

## Architecture notes

- **API docs**: http://localhost:8080/doc.html (Knife4j/Swagger)
- **Default login**: admin / 123456
- **Permission model**: RBAC — menus have `perms` string (e.g. `system:user:list`), frontend checks via `v-hasPerm` directive, backend via `@PreAuthorize`
- **Business rules**: purchase inbound + sales outbound auto-call `StockService.stockIn/stockOut` — inventory is updated when inbound/outbound is recorded
- **Orders have lifecycle status**: 0=draft → 1=submitted → 2=approved → 3=received/delivered → 4=cancelled
- **Finance receivable/payable**: status 0=unpaid, 1=partial, 2=settled; `receive/pay` methods update amounts incrementally
- **DataInitializer** auto-seeds admin user, roles, menus on first startup (checks `sys_user` count). Disable by removing `@Component`.
- **MyBatis Plus** config: auto-fill `createTime/updateTime/createBy/updateBy`, logical delete on `delFlag` field (`0`=active, `1`=deleted)
- **CORS** is enabled globally (backend `WebConfig`)
- **No tests exist** in any module

## Module conventions

- Each `erp-*` backend module follows: `entity/` → `mapper/` → `service/` (interface) → `service/impl/` → `controller/` → `dto/`
- Controller base path matches module: `/system/user/**`, `/inventory/product/**`, `/purchase/supplier/**`, etc.
- Frontend API files in `src/api/<module>.js`, pages in `src/views/<module>/<entity>/index.vue`
- Adding a new module requires: POM (child + parent modules section + admin pom dependency), entity/mapper/service/controller, frontend API + page + router entry + DataInitializer menu + init.sql table
- All entities use `@TableId(type = IdType.AUTO)` for primary key generation

## Database

- MySQL connection: `localhost:3306`, db `erp_system`, user `root` / `root123`
- `docker-compose up -d` auto-mounts `init.sql` into `docker-entrypoint-initdb.d/`
- If MySQL is already seeded, `DataInitializer` skips (no duplicate data issue)
- Table names prefixed by module: `sys_*`, `inv_*`, `pur_*`, `sal_*`, `fin_*`, `pro_*`, `hr_*`, `crm_*`
