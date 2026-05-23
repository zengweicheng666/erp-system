import request from '@/utils/request'

export function listStocks(params) {
  return request.get('/inventory/stock/list', { params })
}

export function stockIn(data) {
  return request.post('/inventory/stock/in', data)
}

export function stockOut(data) {
  return request.post('/inventory/stock/out', data)
}

export function listStockRecords(params) {
  return request.get('/inventory/stock/records', { params })
}
