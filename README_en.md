# ERP System

[中文](README.md)

A full-featured Enterprise Resource Planning system built with Spring Boot 3 + Vue 3.

## Modules

| Module | Features |
|---|---|
| **System** | User, Role, Menu, Dict, Operation Log |
| **Inventory** | Product, Category, Warehouse, Stock In/Out, Stock Records |
| **Purchase** | Supplier, Purchase Order, Inbound, Return |
| **Sales** | Customer, Sales Order, Outbound, Return |
| **Finance** | Receivable, Payable, Receipt, Payment, Expense |
| **Production** | BOM, Production Order, Process Route, Work Report |
| **HR** | Department, Employee, Attendance, Payroll |
| **CRM** | Lead, Opportunity, Contact, Follow-up |

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.2, MyBatis Plus 3.5, Spring Security, JWT |
| Frontend | Vue 3, Element Plus, Pinia, Vue Router 4, Axios |
| Database | MySQL 8 (Docker), schema auto-init |
| Build | Maven (multi-module), Vite 8 |

## Quick Start

### Prerequisites

- Java 17+
- Node.js 20+
- Docker Desktop (for MySQL) or a local MySQL 8 instance

### 1. Start MySQL

```bash
docker compose up -d
```

The `init.sql` script is automatically loaded on first container start. Alternatively, the `DataInitializer` seeds default data (admin user, menus) when the backend starts with an empty database.

### 2. Start Backend

```bash
cd erp-backend
mvn spring-boot:run -pl erp-admin
```

API docs available at: http://localhost:8080/doc.html

### 3. Start Frontend

```bash
cd erp-web
npm install
npm run dev
```

Open http://localhost:3000

### Default Login

- Username: `admin`
- Password: `123456`

## Project Structure

```
erp-system/
├── erp-backend/          # Maven multi-module Spring Boot project
│   ├── erp-common/       # Shared DTOs, exceptions, constants
│   ├── erp-framework/    # Security config, JWT, MyBatis Plus config
│   ├── erp-system/       # RBAC management
│   ├── erp-inventory/    # Inventory management
│   ├── erp-purchase/     # Purchase management
│   ├── erp-sales/        # Sales management
│   ├── erp-finance/      # Financial management
│   ├── erp-production/   # Production management
│   ├── erp-hr/           # Human resources
│   ├── erp-crm/          # Customer relationship management
│   └── erp-admin/        # Application entry point + DataInitializer
├── erp-web/              # Vue 3 frontend
├── docker-compose.yml    # MySQL 8 container
├── init.sql              # Database schema + seed data
└── AGENTS.md             # Agent onboarding guide
```

## Architecture Highlights

- **RBAC Permissions**: Role-based access control with per-button permission checks via `@PreAuthorize` (backend) and `v-hasPerm` directive (frontend)
- **JWT Auth**: Stateless token authentication, token stored in `Authorization: Bearer <token>` header
- **Logical Delete**: All major entities use `delFlag` column (`0` = active, `1` = deleted)
- **Auto Fill**: `createTime`, `updateTime`, `createBy`, `updateBy` are auto-populated by MyBatis Plus
- **Data Initializer**: Seeds default admin user, roles, and menus on first startup
- **Auto Stock Sync**: Purchase inbound and sales outbound automatically update inventory

## License

MIT
