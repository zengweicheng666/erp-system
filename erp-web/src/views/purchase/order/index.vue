<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :model="query" inline>
        <el-form-item label="订单编号"><el-input v-model="query.orderNo" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="query.page=1;fetchData()">查询</el-button><el-button @click="reset">重置</el-button></el-form-item>
      </el-form>
    </div>
    <div class="action-bar"><el-button type="primary" @click="handleAdd" v-hasPerm="'purchase:order:add'">新增订单</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="orderNo" label="订单编号" width="160" />
      <el-table-column prop="supplierId" label="供应商ID" width="80" />
      <el-table-column prop="totalAmount" label="总金额" width="120" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{row}">
          <el-tag :type="['info','primary','success','warning','danger'][row.status]" size="small">
            {{ ['草稿','已提交','已审核','已入库','已取消'][row.status] || '未知' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createBy" label="创建人" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="260" fixed="right">
        <template #default="{row}">
          <el-button link type="primary" v-if="row.status===0" @click="handleSubmitOrder(row)">提交</el-button>
          <el-button link type="primary" v-if="row.status===1" @click="handleApprove(row)">审核</el-button>
          <el-button link type="danger" v-if="row.status===0" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listPurchaseOrders, submitPurchaseOrder, approvePurchaseOrder, delPurchaseOrder, addPurchaseOrder } from '@/api/purchase'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0)
const query=reactive({orderNo:'',page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listPurchaseOrders(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
function reset(){Object.assign(query,{orderNo:'',page:1});fetchData()}
async function handleSubmitOrder(row){await ElMessageBox.confirm('确定提交？');await submitPurchaseOrder(row.orderId);ElMessage.success('提交成功');fetchData()}
async function handleApprove(row){await ElMessageBox.confirm('确定审核通过？');await approvePurchaseOrder(row.orderId);ElMessage.success('审核通过');fetchData()}
async function handleDelete(row){await ElMessageBox.confirm('确定删除？');await delPurchaseOrder(row.orderId);ElMessage.success('删除成功');fetchData()}
async function handleAdd(){const{value}=await ElMessageBox.prompt('供应商ID','新增采购订单');if(!value)return;await addPurchaseOrder({supplierId:value,items:[{productId:1,quantity:1,unitPrice:100}]});ElMessage.success('新增成功');fetchData()}
</script>
