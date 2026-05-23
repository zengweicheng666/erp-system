<template>
  <div class="page-container">
    <div class="action-bar"><el-button type="primary" @click="handleAdd">新增付款单</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="paymentNo" label="付款单号" width="160" />
      <el-table-column prop="supplierId" label="供应商ID" width="80" />
      <el-table-column prop="amount" label="金额" width="120" />
      <el-table-column prop="paymentMethod" label="付款方式" />
      <el-table-column prop="paymentDate" label="付款日期" width="180" />
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listPayments, addPayment } from '@/api/finance'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const query=reactive({page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listPayments(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
async function handleAdd(){const{value}=await ElMessageBox.prompt('供应商ID','新增付款单');if(!value)return;await addPayment({supplierId:parseInt(value)});ElMessage.success('新增成功');fetchData()}
</script>
