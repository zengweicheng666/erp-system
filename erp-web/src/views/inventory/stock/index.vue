<template>
  <div class="page-container">
    <div class="action-bar">
      <el-button type="success" @click="handleStockIn" v-hasPerm="'inventory:stock:in'">入库</el-button>
      <el-button type="warning" @click="handleStockOut" v-hasPerm="'inventory:stock:out'">出库</el-button>
    </div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="productId" label="商品ID" width="70" />
      <el-table-column prop="warehouseId" label="仓库ID" width="70" />
      <el-table-column prop="quantity" label="库存数量" />
      <el-table-column prop="lockedQuantity" label="锁定数量" />
      <el-table-column prop="availableQuantity" label="可用数量" />
      <el-table-column prop="unitPrice" label="单价" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
    </el-table>
    <el-pagination
      v-model:current-page="query.page"
      v-model:page-size="query.pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="fetchData"
      style="margin-top: 16px"
    />

    <el-dialog v-model="dialogVisible" :title="stockType === 1 ? '入库' : '出库'" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="商品ID" prop="productId">
          <el-input-number v-model="form.productId" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="仓库ID" prop="warehouseId">
          <el-input-number v-model="form.warehouseId" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input-number v-model="form.quantity" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="单价">
          <el-input-number v-model="form.unitPrice" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="单号">
          <el-input v-model="form.orderNo" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
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
import { ref, reactive, computed, onMounted } from 'vue'
import { listStocks, stockIn, stockOut } from '@/api/stock'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const stockType = ref(1)
const submitLoading = ref(false)
const formRef = ref(null)

const query = reactive({ page: 1, pageSize: 10 })
const form = reactive({ productId: null, warehouseId: null, quantity: 1, unitPrice: 0, orderNo: '', remark: '' })
const rules = {
  productId: [{ required: true, message: '请输入商品ID', trigger: 'blur' }],
  warehouseId: [{ required: true, message: '请输入仓库ID', trigger: 'blur' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }]
}

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const res = await listStocks(query)
    tableData.value = res.data.records.map(item => ({
      ...item,
      availableQuantity: (item.quantity || 0) - (item.lockedQuantity || 0)
    }))
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

function handleStockIn() {
  stockType.value = 1
  Object.assign(form, { productId: null, warehouseId: null, quantity: 1, unitPrice: 0, orderNo: '', remark: '' })
  dialogVisible.value = true
}

function handleStockOut() {
  stockType.value = 2
  Object.assign(form, { productId: null, warehouseId: null, quantity: 1, unitPrice: 0, orderNo: '', remark: '' })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    if (stockType.value === 1) {
      await stockIn(form)
      ElMessage.success('入库成功')
    } else {
      await stockOut(form)
      ElMessage.success('出库成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}
</script>
