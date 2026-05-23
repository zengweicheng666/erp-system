import request from '@/utils/request'
export function getDeptTree() { return request.get('/hr/dept/tree') }
export function addDept(data) { return request.post('/hr/dept', data) }
export function updateDept(data) { return request.put('/hr/dept', data) }
export function delDept(id) { return request.delete(`/hr/dept/${id}`) }
export function listEmployees(params) { return request.get('/hr/employee/list', { params }) }
export function addEmployee(data) { return request.post('/hr/employee', data) }
export function updateEmployee(data) { return request.put('/hr/employee', data) }
export function delEmployee(id) { return request.delete(`/hr/employee/${id}`) }
export function listAttendances() { return request.get('/hr/attendance/list') }
export function addAttendance(data) { return request.post('/hr/attendance', data) }
export function listPayrolls() { return request.get('/hr/payroll/list') }
export function addPayroll(data) { return request.post('/hr/payroll', data) }
export function updatePayroll(data) { return request.put('/hr/payroll', data) }
