<template>
  <div class="page-container">
    <div class="action-bar"><el-button type="primary" @click="handleAdd" v-hasPerm="'finance:payable:add'">新增应付</el-button><el-button type="warning" @click="handlePay" v-hasPerm="'finance:payable:edit'">付款</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="invoiceNo" label="发票号" width="150" />
      <el-table-column prop="supplierId" label="供应商ID" width="80" />
      <el-table-column prop="totalAmount" label="总金额" width="120" />
      <el-table-column prop="paidAmount" label="已付金额" width="120" />
      <el-table-column prop="unsettledAmount" label="未付金额" width="120" />
      <el-table-column label="状态" width="80"><template #default="{row}"><el-tag :type="['danger','warning','success'][row.status]" size="small">{{ ['未付款','部分付款','已付款'][row.status] }}</el-tag></template></el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listPayables, addPayable, payAmount } from '@/api/finance'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const query=reactive({page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listPayables(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
async function handleAdd(){const{value}=await ElMessageBox.prompt('发票号','新增应付');if(!value)return;await addPayable({invoiceNo:value});ElMessage.success('新增成功');fetchData()}
async function handlePay(){const{value}=await ElMessageBox.prompt('应付ID,金额(逗号分隔)','付款');if(!value)return;const[id,amount]=value.split(',');await payAmount(parseInt(id),{amount});ElMessage.success('付款成功');fetchData()}
</script>
