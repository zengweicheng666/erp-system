<template>
  <div class="page-container">
    <div class="action-bar"><el-button type="primary" @click="handleReturn" v-hasPerm="'sales:return:add'">新增退货</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="returnNo" label="退货单号" width="160" />
      <el-table-column prop="customerId" label="客户ID" width="80" />
      <el-table-column prop="orderId" label="订单ID" width="80" />
      <el-table-column prop="totalAmount" label="金额" width="120" />
      <el-table-column prop="reason" label="原因" />
      <el-table-column prop="returnDate" label="退货日期" width="180" />
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listSalesReturns, addSalesReturn } from '@/api/sales'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const query=reactive({page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listSalesReturns(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
async function handleReturn(){const{value}=await ElMessageBox.prompt('订单ID','新增退货');if(!value)return;await addSalesReturn({orderId:parseInt(value)});ElMessage.success('退货成功');fetchData()}
</script>
