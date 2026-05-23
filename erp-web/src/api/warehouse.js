import request from '@/utils/request'

export function listWarehouses() {
  return request.get('/inventory/warehouse/list')
}

export function addWarehouse(data) {
  return request.post('/inventory/warehouse', data)
}

export function updateWarehouse(data) {
  return request.put('/inventory/warehouse', data)
}

export function delWarehouse(warehouseId) {
  return request.delete(`/inventory/warehouse/${warehouseId}`)
}
