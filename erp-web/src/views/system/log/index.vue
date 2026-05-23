<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :model="query" inline>
        <el-form-item label="模块标题">
          <el-input v-model="query.title" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="操作人员">
          <el-input v-model="query.operName" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="action-bar">
      <el-button type="danger" @click="handleClean" v-hasPerm="'system:log:remove'">清空日志</el-button>
    </div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="operId" label="ID" width="60" />
      <el-table-column prop="title" label="模块标题" />
      <el-table-column prop="requestMethod" label="请求方式" width="80" />
      <el-table-column prop="operUrl" label="请求URL" />
      <el-table-column prop="operName" label="操作人员" />
      <el-table-column prop="operIp" label="操作IP" width="130" />
      <el-table-column prop="costTime" label="耗时(ms)" width="90" />
      <el-table-column label="状态" width="70">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'success' : 'danger'" size="small">
            {{ row.status === 0 ? '成功' : '失败' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operTime" label="操作时间" width="180" />
      <el-table-column label="操作" width="80" fixed="right">
        <template #default="{ row }">
          <el-button link type="danger" @click="handleDelete(row)" v-hasPerm="'system:log:remove'">删除</el-button>
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listLogs, delLog, cleanLogs } from '@/api/log'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)

const query = reactive({ title: '', operName: '', page: 1, pageSize: 10 })

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const res = await listLogs(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

function handleQuery() { query.page = 1; fetchData() }
function handleReset() { Object.assign(query, { title: '', operName: '', page: 1 }); fetchData() }

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除该日志？')
  await delLog(row.operId)
  ElMessage.success('删除成功')
  fetchData()
}

async function handleClean() {
  await ElMessageBox.confirm('确定清空所有日志？')
  await cleanLogs()
  ElMessage.success('清空成功')
  fetchData()
}
</script>
