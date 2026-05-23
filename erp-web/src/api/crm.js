import request from '@/utils/request'
export function listLeads(params) { return request.get('/crm/lead/list', { params }) }
export function getLead(id) { return request.get(`/crm/lead/${id}`) }
export function addLead(data) { return request.post('/crm/lead', data) }
export function updateLead(data) { return request.put('/crm/lead', data) }
export function delLead(id) { return request.delete(`/crm/lead/${id}`) }
export function listOpportunities(params) { return request.get('/crm/opportunity/list', { params }) }
export function addOpportunity(data) { return request.post('/crm/opportunity', data) }
export function updateOpportunity(data) { return request.put('/crm/opportunity', data) }
export function delOpportunity(id) { return request.delete(`/crm/opportunity/${id}`) }
export function listContacts() { return request.get('/crm/contact/list') }
export function addContact(data) { return request.post('/crm/contact', data) }
export function delContact(id) { return request.delete(`/crm/contact/${id}`) }
export function listFollowUps() { return request.get('/crm/followup/list') }
export function addFollowUp(data) { return request.post('/crm/followup', data) }
