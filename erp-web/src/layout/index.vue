<template>
  <div class="app-container">
    <div class="sidebar" :class="{ collapsed: appStore.sidebarCollapsed }">
      <div class="logo">
        <span class="logo-text">ERP 系统</span>
      </div>
      <el-menu
        :default-active="defaultActive"
        :collapse="appStore.sidebarCollapsed"
        :router="true"
        class="sidebar-menu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <template v-for="menu in menuStore.menus" :key="menu.menuId">
          <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path">
            <template #title>
              <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
              <span>{{ menu.menuName }}</span>
            </template>
            <el-menu-item
              v-for="child in menu.children"
              :key="child.menuId"
              :index="child.path"
            >
              <el-icon v-if="child.icon"><component :is="child.icon" /></el-icon>
              <span>{{ child.menuName }}</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="menu.path">
            <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
            <span>{{ menu.menuName }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </div>
    <div class="main-container" :class="{ collapsed: appStore.sidebarCollapsed }">
      <div class="header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="appStore.toggleSidebar" size="20">
            <Fold v-if="!appStore.sidebarCollapsed" />
            <Expand v-else />
          </el-icon>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              {{ userStore.userInfo?.realName || userStore.userInfo?.username }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/store/app'
import { useUserStore } from '@/store/user'
import { useMenuStore } from '@/store/menu'
import { Fold, Expand, ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()
const menuStore = useMenuStore()
const defaultActive = ref('')

onMounted(() => {
  defaultActive.value = route.path
})

function handleCommand(command) {
  if (command === 'logout') {
    userStore.logout()
    menuStore.clearMenus()
    router.push('/login')
  }
}
</script>

<style scoped>
.app-container { display: flex; height: 100vh; }
.sidebar { width: 210px; background: #304156; transition: width 0.3s; overflow: hidden; flex-shrink: 0; }
.sidebar.collapsed { width: 64px; }
.logo { height: 50px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 18px; font-weight: bold; border-bottom: 1px solid #1f2d3d; }
.sidebar-menu { border-right: none; }
.main-container { flex: 1; display: flex; flex-direction: column; overflow: hidden; }
.main-container.collapsed { margin-left: 0; }
.header { height: 50px; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; background: #fff; border-bottom: 1px solid #e6e6e6; }
.header-left { display: flex; align-items: center; }
.collapse-btn { cursor: pointer; }
.header-right { display: flex; align-items: center; }
.user-info { cursor: pointer; display: flex; align-items: center; gap: 4px; }
.content { flex: 1; padding: 20px; background: #f0f2f5; overflow-y: auto; }
</style>
