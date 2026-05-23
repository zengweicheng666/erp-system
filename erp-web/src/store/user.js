import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, getUserInfo } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)
  const permissions = ref([])
  const roles = ref([])

  const isLoggedIn = computed(() => !!token.value)

  async function loginAction(loginForm) {
    const res = await login(loginForm)
    token.value = res.data.token
    localStorage.setItem('token', res.data.token)
    userInfo.value = res.data.user
    permissions.value = res.data.user.permissions || []
    roles.value = res.data.user.roles || []
    return res
  }

  async function fetchUserInfo() {
    const res = await getUserInfo()
    userInfo.value = res.data
    permissions.value = res.data.permissions || []
    roles.value = res.data.roles || []
    return res
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    permissions.value = []
    roles.value = []
    localStorage.removeItem('token')
  }

  function hasPermission(perm) {
    if (roles.value.includes('admin')) return true
    return permissions.value.includes(perm)
  }

  return {
    token,
    userInfo,
    permissions,
    roles,
    isLoggedIn,
    loginAction,
    fetchUserInfo,
    logout,
    hasPermission
  }
})
