<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :model="query" inline>
        <el-form-item label="商品编码">
          <el-input v-model="query.productCode" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="query.productName" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="action-bar">
      <el-button type="primary" @click="handleAdd" v-hasPerm="'inventory:product:add'">新增商品</el-button>
    </div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="productCode" label="商品编码" width="130" />
      <el-table-column prop="productName" label="商品名称" />
      <el-table-column prop="unit" label="单位" width="60" />
      <el-table-column prop="spec" label="规格" />
      <el-table-column prop="purchasePrice" label="采购价" width="100" />
      <el-table-column prop="salePrice" label="销售价" width="100" />
      <el-table-column prop="stockWarning" label="库存预警" width="100" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'success' : 'danger'" size="small">
            {{ row.status === 0 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)" v-hasPerm="'inventory:product:edit'">修改</el-button>
          <el-button link type="danger" @click="handleDelete(row)" v-hasPerm="'inventory:product:remove'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="query.page"
      v-model:page-size="query.pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="fetchData"
      style="margin-top: 16px"
    />

    <el-dialog v-model="dialogVisible" :title="isEdit ? '修改商品' : '新增商品'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="商品编码" prop="productCode">
          <el-input v-model="form.productCode" />
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" />
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="form.unit" />
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="form.spec" />
        </el-form-item>
        <el-form-item label="采购价">
          <el-input-number v-model="form.purchasePrice" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="销售价">
          <el-input-number v-model="form.salePrice" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="预警数量">
          <el-input-number v-model="form.stockWarning" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listProducts, addProduct, updateProduct, delProduct } from '@/api/product'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)

const query = reactive({ productCode: '', productName: '', page: 1, pageSize: 10 })
const form = reactive({ productId: null, productCode: '', productName: '', unit: '', spec: '', purchasePrice: 0, salePrice: 0, stockWarning: 0, status: 0 })
const rules = {
  productCode: [{ required: true, message: '请输入商品编码', trigger: 'blur' }],
  productName: [{ required: true, message: '请输入商品名称', trigger: 'blur' }]
}

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const res = await listProducts(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

function handleQuery() { query.page = 1; fetchData() }
function handleReset() { Object.assign(query, { productCode: '', productName: '', page: 1 }); fetchData() }

function handleAdd() {
  isEdit.value = false
  Object.assign(form, { productId: null, productCode: '', productName: '', unit: '', spec: '', purchasePrice: 0, salePrice: 0, stockWarning: 0, status: 0 })
  dialogVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await updateProduct(form)
      ElMessage.success('修改成功')
    } else {
      await addProduct(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除该商品？')
  await delProduct(row.productId)
  ElMessage.success('删除成功')
  fetchData()
}
</script>
