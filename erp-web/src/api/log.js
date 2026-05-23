import request from '@/utils/request'

export function listLogs(params) {
  return request.get('/system/log/list', { params })
}

export function delLog(operId) {
  return request.delete(`/system/log/${operId}`)
}

export function cleanLogs() {
  return request.delete('/system/log/clean')
}
