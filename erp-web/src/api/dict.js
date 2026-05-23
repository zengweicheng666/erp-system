import request from '@/utils/request'

export function listDictTypes(params) {
  return request.get('/system/dict/type/list', { params })
}

export function getDictType(dictId) {
  return request.get(`/system/dict/type/${dictId}`)
}

export function addDictType(data) {
  return request.post('/system/dict/type', data)
}

export function updateDictType(data) {
  return request.put('/system/dict/type', data)
}

export function delDictType(dictId) {
  return request.delete(`/system/dict/type/${dictId}`)
}

export function getDictDataByType(dictType) {
  return request.get(`/system/dict/data/type/${dictType}`)
}

export function addDictData(data) {
  return request.post('/system/dict/data', data)
}

export function updateDictData(data) {
  return request.put('/system/dict/data', data)
}

export function delDictData(dictCode) {
  return request.delete(`/system/dict/data/${dictCode}`)
}
