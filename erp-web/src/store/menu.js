import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getRouters } from '@/api/auth'

export const useMenuStore = defineStore('menu', () => {
  const menus = ref([])

  async function fetchMenus() {
    const res = await getRouters()
    menus.value = res.data
    return res.data
  }

  function clearMenus() {
    menus.value = []
  }

  return {
    menus,
    fetchMenus,
    clearMenus
  }
})
