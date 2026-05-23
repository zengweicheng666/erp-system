import request from '@/utils/request'

export function getCategoryTree() {
  return request.get('/inventory/category/tree')
}

export function addCategory(data) {
  return request.post('/inventory/category', data)
}

export function updateCategory(data) {
  return request.put('/inventory/category', data)
}

export function delCategory(categoryId) {
  return request.delete(`/inventory/category/${categoryId}`)
}
