import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'
import { useMenuStore } from '@/store/menu'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/system/user',
    children: [
      {
        path: 'system/user',
        name: 'User',
        component: () => import('@/views/system/user/index.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'system/role',
        name: 'Role',
        component: () => import('@/views/system/role/index.vue'),
        meta: { title: '角色管理', icon: 'UserFilled' }
      },
      {
        path: 'system/menu',
        name: 'Menu',
        component: () => import('@/views/system/menu/index.vue'),
        meta: { title: '菜单管理', icon: 'Menu' }
      },
      {
        path: 'system/dict',
        name: 'Dict',
        component: () => import('@/views/system/dict/index.vue'),
        meta: { title: '字典管理', icon: 'Reading' }
      },
      {
        path: 'system/log',
        name: 'Log',
        component: () => import('@/views/system/log/index.vue'),
        meta: { title: '操作日志', icon: 'Document' }
      },
      {
        path: 'inventory/product',
        name: 'Product',
        component: () => import('@/views/inventory/product/index.vue'),
        meta: { title: '商品管理', icon: 'Goods' }
      },
      {
        path: 'inventory/category',
        name: 'Category',
        component: () => import('@/views/inventory/category/index.vue'),
        meta: { title: '商品分类', icon: 'FolderOpened' }
      },
      {
        path: 'inventory/warehouse',
        name: 'Warehouse',
        component: () => import('@/views/inventory/warehouse/index.vue'),
        meta: { title: '仓库管理', icon: 'HomeFilled' }
      },
      {
        path: 'inventory/stock',
        name: 'Stock',
        component: () => import('@/views/inventory/stock/index.vue'),
        meta: { title: '库存管理', icon: 'Coin' }
      },
      {
        path: 'inventory/stock/records',
        name: 'StockRecords',
        component: () => import('@/views/inventory/stock/records.vue'),
        meta: { title: '库存流水', icon: 'List' }
      },
      { path: 'purchase/supplier', name: 'Supplier', component: () => import('@/views/purchase/supplier/index.vue'), meta: { title: '供应商管理', icon: 'User' } },
      { path: 'purchase/order', name: 'PurchaseOrder', component: () => import('@/views/purchase/order/index.vue'), meta: { title: '采购订单', icon: 'List' } },
      { path: 'purchase/inbound', name: 'PurchaseInbound', component: () => import('@/views/purchase/inbound/index.vue'), meta: { title: '采购入库', icon: 'Upload' } },
      { path: 'purchase/preturn', name: 'PurchaseReturn', component: () => import('@/views/purchase/return/index.vue'), meta: { title: '采购退货', icon: 'RefreshLeft' } },
      { path: 'sales/customer', name: 'Customer', component: () => import('@/views/sales/customer/index.vue'), meta: { title: '客户管理', icon: 'User' } },
      { path: 'sales/order', name: 'SalesOrder', component: () => import('@/views/sales/order/index.vue'), meta: { title: '销售订单', icon: 'List' } },
      { path: 'sales/outbound', name: 'SalesOutbound', component: () => import('@/views/sales/outbound/index.vue'), meta: { title: '销售出库', icon: 'Download' } },
      { path: 'sales/sreturn', name: 'SalesReturn', component: () => import('@/views/sales/return/index.vue'), meta: { title: '销售退货', icon: 'RefreshRight' } },
      { path: 'finance/receivable', name: 'Receivable', component: () => import('@/views/finance/receivable/index.vue'), meta: { title: '应收账款', icon: 'Wallet' } },
      { path: 'finance/payable', name: 'Payable', component: () => import('@/views/finance/payable/index.vue'), meta: { title: '应付账款', icon: 'WalletFilled' } },
      { path: 'finance/receipt', name: 'Receipt', component: () => import('@/views/finance/receipt/index.vue'), meta: { title: '收款单', icon: 'Money' } },
      { path: 'finance/payment', name: 'Payment', component: () => import('@/views/finance/payment/index.vue'), meta: { title: '付款单', icon: 'Money' } },
      { path: 'finance/expense', name: 'Expense', component: () => import('@/views/finance/expense/index.vue'), meta: { title: '费用管理', icon: 'Document' } }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    hidden: true
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  if (to.path === '/login') {
    if (userStore.isLoggedIn) {
      next('/')
    } else {
      next()
    }
    return
  }

  if (!userStore.isLoggedIn) {
    next('/login')
    return
  }

  if (!userStore.userInfo) {
    try {
      await userStore.fetchUserInfo()
      const menuStore = useMenuStore()
      await menuStore.fetchMenus()
    } catch (e) {
      userStore.logout()
      next('/login')
      return
    }
  }
  next()
})

export default router
