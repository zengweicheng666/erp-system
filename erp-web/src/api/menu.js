import request from '@/utils/request'

export function getMenuTree() {
  return request.get('/system/menu/tree')
}

export function getMenu(menuId) {
  return request.get(`/system/menu/${menuId}`)
}

export function addMenu(data) {
  return request.post('/system/menu', data)
}

export function updateMenu(data) {
  return request.put('/system/menu', data)
}

export function delMenu(menuId) {
  return request.delete(`/system/menu/${menuId}`)
}
