import request from '@/utils/request'
export function listCustomers(params) { return request.get('/sales/customer/list', { params }) }
export function addCustomer(data) { return request.post('/sales/customer', data) }
export function updateCustomer(data) { return request.put('/sales/customer', data) }
export function delCustomer(id) { return request.delete(`/sales/customer/${id}`) }
export function listSalesOrders(params) { return request.get('/sales/order/list', { params }) }
export function getSalesOrder(id) { return request.get(`/sales/order/${id}`) }
export function addSalesOrder(data) { return request.post('/sales/order', data) }
export function submitSalesOrder(id) { return request.put(`/sales/order/submit/${id}`) }
export function approveSalesOrder(id) { return request.put(`/sales/order/approve/${id}`) }
export function delSalesOrder(id) { return request.delete(`/sales/order/${id}`) }
export function salesOutbound(data) { return request.post('/sales/outbound', data) }
export function listSalesOutbounds(params) { return request.get('/sales/outbound/list', { params }) }
export function listSalesReturns(params) { return request.get('/sales/return/list', { params }) }
export function addSalesReturn(data) { return request.post('/sales/return', data) }
