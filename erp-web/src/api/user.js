import request from '@/utils/request'

export function listUsers(params) {
  return request.get('/system/user/list', { params })
}

export function getUser(userId) {
  return request.get(`/system/user/${userId}`)
}

export function addUser(data) {
  return request.post('/system/user', data)
}

export function updateUser(data) {
  return request.put('/system/user', data)
}

export function delUser(userId) {
  return request.delete(`/system/user/${userId}`)
}

export function resetPwd(userId) {
  return request.put(`/system/user/resetPwd/${userId}`)
}

export function updateStatus(params) {
  return request.put('/system/user/status', null, { params })
}
