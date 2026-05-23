<template>
  <div class="page-container">
    <div class="action-bar"><el-button type="primary" @click="handleAdd" v-hasPerm="'finance:receivable:add'">新增应收</el-button><el-button type="success" @click="handleReceive" v-hasPerm="'finance:receivable:edit'">收款</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="invoiceNo" label="发票号" width="150" />
      <el-table-column prop="customerId" label="客户ID" width="70" />
      <el-table-column prop="totalAmount" label="总金额" width="120" />
      <el-table-column prop="paidAmount" label="已收金额" width="120" />
      <el-table-column prop="unsettledAmount" label="未收金额" width="120" />
      <el-table-column prop="dueDate" label="到期日" />
      <el-table-column label="状态" width="80"><template #default="{row}"><el-tag :type="['danger','warning','success'][row.status]" size="small">{{ ['未收款','部分收款','已收款'][row.status] }}</el-tag></template></el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listReceivables, addReceivable, receiveAmount } from '@/api/finance'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const query=reactive({page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listReceivables(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
async function handleAdd(){const{value}=await ElMessageBox.prompt('发票号','新增应收');if(!value)return;await addReceivable({invoiceNo:value});ElMessage.success('新增成功');fetchData()}
async function handleReceive(){const{value}=await ElMessageBox.prompt('应收ID,金额(逗号分隔)','收款');if(!value)return;const[id,amount]=value.split(',');await receiveAmount(parseInt(id),{amount});ElMessage.success('收款成功');fetchData()}
</script>
