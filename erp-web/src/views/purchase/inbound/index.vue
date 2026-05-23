<template>
  <div class="page-container">
    <div class="action-bar"><el-button type="primary" @click="handleInbound" v-hasPerm="'purchase:inbound:add'">执行入库</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="inboundNo" label="入库单号" width="160" />
      <el-table-column prop="orderId" label="订单ID" width="80" />
      <el-table-column prop="orderNo" label="订单编号" width="160" />
      <el-table-column prop="warehouseId" label="仓库ID" width="80" />
      <el-table-column prop="operator" label="操作人" />
      <el-table-column prop="inboundDate" label="入库日期" width="180" />
      <el-table-column prop="remark" label="备注" />
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listPurchaseInbounds, purchaseInbound } from '@/api/purchase'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const query=reactive({page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listPurchaseInbounds(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
async function handleInbound(){const{value}=await ElMessageBox.prompt('订单ID','执行入库');if(!value)return;await purchaseInbound({orderId:parseInt(value),warehouseId:1});ElMessage.success('入库成功');fetchData()}
</script>
