import request from '@/utils/request'
export function listReceivables(params) { return request.get('/finance/receivable/list', { params }) }
export function addReceivable(data) { return request.post('/finance/receivable', data) }
export function receiveAmount(id, data) { return request.put(`/finance/receivable/receive/${id}`, data) }
export function listPayables(params) { return request.get('/finance/payable/list', { params }) }
export function addPayable(data) { return request.post('/finance/payable', data) }
export function payAmount(id, data) { return request.put(`/finance/payable/pay/${id}`, data) }
export function listReceipts(params) { return request.get('/finance/receipt/list', { params }) }
export function addReceipt(data) { return request.post('/finance/receipt', data) }
export function listPayments(params) { return request.get('/finance/payment/list', { params }) }
export function addPayment(data) { return request.post('/finance/payment', data) }
export function listExpenses(params) { return request.get('/finance/expense/list', { params }) }
export function addExpense(data) { return request.post('/finance/expense', data) }
export function delExpense(id) { return request.delete(`/finance/expense/${id}`) }
