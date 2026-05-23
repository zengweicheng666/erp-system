<template>
  <div class="page-container">
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="recordId" label="ID" width="60" />
      <el-table-column prop="productId" label="商品ID" />
      <el-table-column prop="warehouseId" label="仓库ID" />
      <el-table-column prop="quantity" label="数量" />
      <el-table-column label="类型" width="80">
        <template #default="{ row }">
          <el-tag :type="row.recordType === 1 ? 'success' : 'warning'" size="small">
            {{ row.recordType === 1 ? '入库' : '出库' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="orderNo" label="关联单号" />
      <el-table-column prop="unitPrice" label="单价" />
      <el-table-column prop="totalPrice" label="总金额" />
      <el-table-column prop="createBy" label="操作人" />
      <el-table-column prop="createTime" label="操作时间" width="180" />
    </el-table>
    <el-pagination
      v-model:current-page="query.page"
      v-model:page-size="query.pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="fetchData"
      style="margin-top: 16px"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listStockRecords } from '@/api/stock'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ page: 1, pageSize: 10 })

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const res = await listStockRecords(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}
</script>
