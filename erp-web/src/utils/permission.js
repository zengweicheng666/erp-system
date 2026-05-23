import { useUserStore } from '@/store/user'

export default {
  mounted(el, binding) {
    const userStore = useUserStore()
    const perm = binding.value
    if (perm && !userStore.hasPermission(perm)) {
      el.parentNode?.removeChild(el)
    }
  }
}
