import request from '@/utils/request'

export function listRoles(params) {
  return request.get('/system/role/list', { params })
}

export function getRole(roleId) {
  return request.get(`/system/role/${roleId}`)
}

export function addRole(data) {
  return request.post('/system/role', data)
}

export function updateRole(data) {
  return request.put('/system/role', data)
}

export function delRole(roleId) {
  return request.delete(`/system/role/${roleId}`)
}

export function getMenuIds(roleId) {
  return request.get(`/system/role/menuIds/${roleId}`)
}

export function assignMenus(data) {
  return request.put('/system/role/assignMenus', data)
}
