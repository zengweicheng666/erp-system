<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :model="query" inline>
        <el-form-item label="费用类型"><el-input v-model="query.expenseType" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="query.page=1;fetchData()">查询</el-button></el-form-item>
      </el-form>
    </div>
    <div class="action-bar"><el-button type="primary" @click="handleAdd" v-hasPerm="'finance:expense:add'">新增费用</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="expenseNo" label="费用单号" width="160" />
      <el-table-column prop="expenseType" label="费用类型" />
      <el-table-column prop="amount" label="金额" width="120" />
      <el-table-column prop="department" label="部门" />
      <el-table-column prop="operatorName" label="经办人" />
      <el-table-column prop="expenseDate" label="费用日期" width="180" />
      <el-table-column label="操作" width="80"><template #default="{row}"><el-button link type="danger" @click="handleDelete(row)" v-hasPerm="'finance:expense:remove'">删除</el-button></template></el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listExpenses, addExpense, delExpense } from '@/api/finance'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const query=reactive({expenseType:'',page:1,pageSize:10})
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listExpenses(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
async function handleAdd(){const{value}=await ElMessageBox.prompt('金额','新增费用');if(!value)return;await addExpense({amount:parseFloat(value)});ElMessage.success('新增成功');fetchData()}
async function handleDelete(row){await ElMessageBox.confirm('确定删除？');await delExpense(row.expenseId);ElMessage.success('删除成功');fetchData()}
</script>
