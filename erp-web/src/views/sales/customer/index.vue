<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :model="query" inline>
        <el-form-item label="客户编码"><el-input v-model="query.customerCode" clearable /></el-form-item>
        <el-form-item label="客户名称"><el-input v-model="query.customerName" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="query.page=1;fetchData()">查询</el-button><el-button @click="reset">重置</el-button></el-form-item>
      </el-form>
    </div>
    <div class="action-bar"><el-button type="primary" @click="handleAdd" v-hasPerm="'sales:customer:add'">新增客户</el-button></div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="customerCode" label="编码" width="120" />
      <el-table-column prop="customerName" label="客户名称" />
      <el-table-column prop="contactPerson" label="联系人" />
      <el-table-column prop="phone" label="电话" width="120" />
      <el-table-column label="状态" width="70"><template #default="{row}"><el-tag :type="row.status===0?'success':'danger'" size="small">{{row.status===0?'正常':'停用'}}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{row}">
          <el-button link type="primary" @click="handleEdit(row)" v-hasPerm="'sales:customer:edit'">修改</el-button>
          <el-button link type="danger" @click="handleDelete(row)" v-hasPerm="'sales:customer:remove'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.page" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="fetchData" style="margin-top:16px" />
    <el-dialog v-model="dialogVisible" :title="isEdit?'修改客户':'新增客户'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="编码" prop="customerCode"><el-input v-model="form.customerCode" /></el-form-item>
        <el-form-item label="名称" prop="customerName"><el-input v-model="form.customerName" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contactPerson" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :value="0">正常</el-radio><el-radio :value="1">停用</el-radio></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listCustomers, addCustomer, updateCustomer, delCustomer } from '@/api/sales'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading=ref(false);const tableData=ref([]);const total=ref(0);const dialogVisible=ref(false);const isEdit=ref(false);const submitLoading=ref(false);const formRef=ref(null)
const query=reactive({customerCode:'',customerName:'',page:1,pageSize:10})
const form=reactive({customerId:null,customerCode:'',customerName:'',contactPerson:'',phone:'',address:'',status:0})
const rules={customerCode:[{required:true,message:'请输入编码',trigger:'blur'}],customerName:[{required:true,message:'请输入名称',trigger:'blur'}]}
onMounted(()=>fetchData())
async function fetchData(){loading.value=true;try{const res=await listCustomers(query);tableData.value=res.data.records;total.value=res.data.total}finally{loading.value=false}}
function reset(){Object.assign(query,{customerCode:'',customerName:'',page:1});fetchData()}
function handleAdd(){isEdit.value=false;Object.assign(form,{customerId:null,customerCode:'',customerName:'',contactPerson:'',phone:'',address:'',status:0});dialogVisible.value=true}
function handleEdit(row){isEdit.value=true;Object.assign(form,row);dialogVisible.value=true}
async function handleSubmit(){const valid=await formRef.value.validate().catch(()=>false);if(!valid)return;submitLoading.value=true;try{if(isEdit.value){await updateCustomer(form)}else{await addCustomer(form)}ElMessage.success(isEdit.value?'修改成功':'新增成功');dialogVisible.value=false;fetchData()}finally{submitLoading.value=false}}
async function handleDelete(row){await ElMessageBox.confirm('确定删除？');await delCustomer(row.customerId);ElMessage.success('删除成功');fetchData()}
</script>
