-- ERP System Initialization Script

CREATE DATABASE IF NOT EXISTS erp_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE erp_system;

-- ==================== System Module ====================

CREATE TABLE IF NOT EXISTS sys_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    dept_id BIGINT COMMENT '部门ID',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志(0存在 1删除)',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS sys_role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
    role_sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE IF NOT EXISTS sys_menu (
    menu_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    path VARCHAR(200) COMMENT '路由地址',
    component VARCHAR(255) COMMENT '组件路径',
    query VARCHAR(255) COMMENT '路由参数',
    menu_type TINYINT COMMENT '菜单类型(0菜单 1按钮)',
    visible CHAR(1) DEFAULT '0' COMMENT '可见状态(0显示 1隐藏)',
    status CHAR(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
    perms VARCHAR(100) COMMENT '权限标识',
    icon VARCHAR(100) COMMENT '菜单图标',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

CREATE TABLE IF NOT EXISTS sys_dict_type (
    dict_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '字典主键',
    dict_name VARCHAR(100) COMMENT '字典名称',
    dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

CREATE TABLE IF NOT EXISTS sys_dict_data (
    dict_code BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '字典编码',
    dict_sort INT DEFAULT 0 COMMENT '字典排序',
    dict_label VARCHAR(100) COMMENT '字典标签',
    dict_value VARCHAR(100) COMMENT '字典键值',
    dict_type VARCHAR(100) COMMENT '字典类型',
    css_class VARCHAR(100) COMMENT '样式属性',
    list_class VARCHAR(100) COMMENT '表格回显样式',
    is_default CHAR(1) DEFAULT 'N' COMMENT '是否默认(Y是 N否)',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

CREATE TABLE IF NOT EXISTS sys_oper_log (
    oper_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志主键',
    title VARCHAR(50) COMMENT '模块标题',
    business_type INT DEFAULT 0 COMMENT '业务类型',
    method VARCHAR(100) COMMENT '方法名称',
    request_method VARCHAR(10) COMMENT '请求方式',
    oper_url VARCHAR(255) COMMENT '请求URL',
    oper_param TEXT COMMENT '请求参数',
    json_result TEXT COMMENT '返回参数',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1异常)',
    error_msg VARCHAR(2000) COMMENT '错误消息',
    oper_name VARCHAR(50) COMMENT '操作人员',
    oper_ip VARCHAR(128) COMMENT '操作IP',
    cost_time BIGINT COMMENT '耗时(ms)',
    oper_time DATETIME COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ==================== Inventory Module ====================

CREATE TABLE IF NOT EXISTS inv_product_category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

CREATE TABLE IF NOT EXISTS inv_product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    product_code VARCHAR(50) NOT NULL COMMENT '商品编码',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    category_id BIGINT COMMENT '分类ID',
    unit VARCHAR(20) COMMENT '单位',
    spec VARCHAR(200) COMMENT '规格',
    model VARCHAR(200) COMMENT '型号',
    purchase_price DECIMAL(10,2) COMMENT '采购价',
    sale_price DECIMAL(10,2) COMMENT '销售价',
    cost_price DECIMAL(10,2) COMMENT '成本价',
    stock_warning INT DEFAULT 0 COMMENT '库存预警数量',
    remark VARCHAR(500) COMMENT '备注',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志',
    UNIQUE KEY uk_product_code (product_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

CREATE TABLE IF NOT EXISTS inv_warehouse (
    warehouse_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '仓库ID',
    warehouse_name VARCHAR(100) NOT NULL COMMENT '仓库名称',
    warehouse_code VARCHAR(50) NOT NULL COMMENT '仓库编码',
    address VARCHAR(255) COMMENT '仓库地址',
    manager VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    update_by VARCHAR(50) COMMENT '更新者',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志',
    UNIQUE KEY uk_warehouse_code (warehouse_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库表';

CREATE TABLE IF NOT EXISTS inv_stock (
    stock_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '库存ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    warehouse_id BIGINT NOT NULL COMMENT '仓库ID',
    quantity INT DEFAULT 0 COMMENT '库存数量',
    locked_quantity INT DEFAULT 0 COMMENT '锁定数量',
    unit_price DECIMAL(10,2) COMMENT '单价',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    UNIQUE KEY uk_product_warehouse (product_id, warehouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

CREATE TABLE IF NOT EXISTS inv_stock_record (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '流水ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    warehouse_id BIGINT NOT NULL COMMENT '仓库ID',
    quantity INT NOT NULL COMMENT '变动数量',
    record_type TINYINT COMMENT '类型(1入库 2出库)',
    order_no VARCHAR(100) COMMENT '关联单号',
    unit_price DECIMAL(10,2) COMMENT '单价',
    total_price DECIMAL(10,2) COMMENT '总金额',
    remark VARCHAR(500) COMMENT '备注',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存流水表';

-- ==================== Init Data ====================

-- Admin user (password: 123456)
INSERT INTO sys_user (username, password, real_name, status, create_time, update_time) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '系统管理员', 0, NOW(), NOW());

-- Roles
INSERT INTO sys_role (role_name, role_key, role_sort, status, create_time, update_time) VALUES
('超级管理员', 'admin', 1, 0, NOW(), NOW()),
('普通用户', 'user', 2, 0, NOW(), NOW());

-- Assign admin role to admin user
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- System Menus
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_time) VALUES
(1, '系统管理', 0, 1, '/system', 'Layout', 0, '0', '0', NULL, 'Setting', NOW()),
(2, '用户管理', 1, 1, 'user', '/system/user/index', 0, '0', '0', 'system:user:list', 'User', NOW()),
(3, '角色管理', 1, 2, 'role', '/system/role/index', 0, '0', '0', 'system:role:list', 'UserFilled', NOW()),
(4, '菜单管理', 1, 3, 'menu', '/system/menu/index', 0, '0', '0', 'system:menu:list', 'Menu', NOW()),
(5, '字典管理', 1, 4, 'dict', '/system/dict/index', 0, '0', '0', 'system:dict:list', 'Reading', NOW()),
(6, '操作日志', 1, 5, 'log', '/system/log/index', 0, '0', '0', 'system:log:list', 'Document', NOW()),

(20, '库存管理', 0, 2, '/inventory', 'Layout', 0, '0', '0', NULL, 'Goods', NOW()),
(21, '商品管理', 20, 1, 'product', '/inventory/product/index', 0, '0', '0', 'inventory:product:list', 'Goods', NOW()),
(22, '商品分类', 20, 2, 'category', '/inventory/category/index', 0, '0', '0', 'inventory:category:list', 'FolderOpened', NOW()),
(23, '仓库管理', 20, 3, 'warehouse', '/inventory/warehouse/index', 0, '0', '0', 'inventory:warehouse:list', 'HomeFilled', NOW()),
(24, '库存管理', 20, 4, 'stock', '/inventory/stock/index', 0, '0', '0', 'inventory:stock:list', 'Coin', NOW()),
(25, '库存流水', 20, 5, 'records', '/inventory/stock/records', 0, '0', '0', 'inventory:stock:list', 'List', NOW());

-- Menu permissions for admin role
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
(1, 20), (1, 21), (1, 22), (1, 23), (1, 24), (1, 25);
