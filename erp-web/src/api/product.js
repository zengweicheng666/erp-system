import request from '@/utils/request'

export function listProducts(params) {
  return request.get('/inventory/product/list', { params })
}

export function getProduct(productId) {
  return request.get(`/inventory/product/${productId}`)
}

export function addProduct(data) {
  return request.post('/inventory/product', data)
}

export function updateProduct(data) {
  return request.put('/inventory/product', data)
}

export function delProduct(productId) {
  return request.delete(`/inventory/product/${productId}`)
}
