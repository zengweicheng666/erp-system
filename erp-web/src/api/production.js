import request from '@/utils/request'
export function listBom(productId) { return request.get(`/production/bom/${productId}`) }
export function addBom(data) { return request.post('/production/bom', data) }
export function delBom(id) { return request.delete(`/production/bom/${id}`) }
export function listProdOrders(params) { return request.get('/production/order/list', { params }) }
export function addProdOrder(data) { return request.post('/production/order', data) }
export function startProdOrder(id) { return request.put(`/production/order/start/${id}`) }
export function completeProdOrder(id) { return request.put(`/production/order/complete/${id}`) }
export function delProdOrder(id) { return request.delete(`/production/order/${id}`) }
export function listProcessRoutes() { return request.get('/production/process/list') }
export function addProcessRoute(data) { return request.post('/production/process', data) }
export function delProcessRoute(id) { return request.delete(`/production/process/${id}`) }
export function listWorkReports() { return request.get('/production/report/list') }
export function addWorkReport(data) { return request.post('/production/report', data) }
