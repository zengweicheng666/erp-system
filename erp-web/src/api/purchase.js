import request from '@/utils/request'
export function listSuppliers(params) { return request.get('/purchase/supplier/list', { params }) }
export function addSupplier(data) { return request.post('/purchase/supplier', data) }
export function updateSupplier(data) { return request.put('/purchase/supplier', data) }
export function delSupplier(id) { return request.delete(`/purchase/supplier/${id}`) }
export function listPurchaseOrders(params) { return request.get('/purchase/order/list', { params }) }
export function getPurchaseOrder(id) { return request.get(`/purchase/order/${id}`) }
export function addPurchaseOrder(data) { return request.post('/purchase/order', data) }
export function submitPurchaseOrder(id) { return request.put(`/purchase/order/submit/${id}`) }
export function approvePurchaseOrder(id) { return request.put(`/purchase/order/approve/${id}`) }
export function delPurchaseOrder(id) { return request.delete(`/purchase/order/${id}`) }
export function purchaseInbound(data) { return request.post('/purchase/inbound', data) }
export function listPurchaseInbounds(params) { return request.get('/purchase/inbound/list', { params }) }
export function listPurchaseReturns(params) { return request.get('/purchase/return/list', { params }) }
export function addPurchaseReturn(data) { return request.post('/purchase/return', data) }
