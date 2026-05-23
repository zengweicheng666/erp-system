<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :model="query" inline>
        <el-form-item label="字典名称">
          <el-input v-model="query.dictName" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="字典类型">
          <el-input v-model="query.dictType" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="action-bar">
      <el-button type="primary" @click="handleAdd" v-hasPerm="'system:dict:add'">新增字典</el-button>
    </div>
    <el-table :data="tableData" border stripe v-loading="loading">
      <el-table-column prop="dictId" label="ID" width="60" />
      <el-table-column prop="dictName" label="字典名称" />
      <el-table-column prop="dictType" label="字典类型" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'success' : 'danger'">
            {{ row.status === 0 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="250" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleDictData(row)">字典数据</el-button>
          <el-button link type="primary" @click="handleEdit(row)" v-hasPerm="'system:dict:edit'">修改</el-button>
          <el-button link type="danger" @click="handleDelete(row)" v-hasPerm="'system:dict:remove'">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="isEdit ? '修改字典' : '新增字典'" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="form.dictName" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="form.dictType" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">停用</el-radio>
          </el-radio-group>
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

    <el-dialog v-model="dataDialogVisible" title="字典数据" width="600px">
      <div style="margin-bottom: 12px">
        <el-button type="primary" size="small" @click="handleAddData">新增数据</el-button>
      </div>
      <el-table :data="dictDataList" border stripe>
        <el-table-column prop="dictSort" label="排序" width="60" />
        <el-table-column prop="dictLabel" label="标签" />
        <el-table-column prop="dictValue" label="键值" />
        <el-table-column label="默认" width="60">
          <template #default="{ row }">
            {{ row.isDefault === 'Y' ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'success' : 'danger'" size="small">
              {{ row.status === 0 ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEditData(row)">修改</el-button>
            <el-button link type="danger" @click="handleDeleteData(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { listDictTypes, addDictType, updateDictType, delDictType, getDictDataByType, addDictData, updateDictData, delDictData } from '@/api/dict'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dataDialogVisible = ref(false)
const isEdit = ref(false)
const submitLoading = ref(false)
const dictDataList = ref([])
const currentDictType = ref('')
const formRef = ref(null)

const query = reactive({ dictName: '', dictType: '', page: 1, pageSize: 10 })
const form = reactive({ dictId: null, dictName: '', dictType: '', status: 0, remark: '' })
const rules = {
  dictName: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
  dictType: [{ required: true, message: '请输入字典类型', trigger: 'blur' }]
}

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const res = await listDictTypes(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

function handleQuery() { query.page = 1; fetchData() }
function handleReset() { Object.assign(query, { dictName: '', dictType: '', page: 1 }); fetchData() }

function handleAdd() {
  isEdit.value = false
  Object.assign(form, { dictId: null, dictName: '', dictType: '', status: 0, remark: '' })
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
      await updateDictType(form)
      ElMessage.success('修改成功')
    } else {
      await addDictType(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDictData(row) {
  currentDictType.value = row.dictType
  const res = await getDictDataByType(row.dictType)
  dictDataList.value = res.data
  dataDialogVisible.value = true
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除该字典？')
  await delDictType(row.dictId)
  ElMessage.success('删除成功')
  fetchData()
}

function handleAddData() {
  ElMessageBox.prompt('标签', '新增字典数据', { inputValue: '', inputPlaceholder: '请输入字典标签' }).then(async ({ value }) => {
    if (!value) return
    await addDictData({ dictType: currentDictType.value, dictLabel: value, dictValue: value, status: 0 })
    ElMessage.success('新增成功')
    const res = await getDictDataByType(currentDictType.value)
    dictDataList.value = res.data
  })
}

async function handleEditData(row) {
  ElMessageBox.prompt('标签', '修改字典数据', { inputValue: row.dictLabel, inputPlaceholder: '请输入字典标签' }).then(async ({ value }) => {
    if (!value) return
    await updateDictData({ ...row, dictLabel: value, dictValue: value })
    ElMessage.success('修改成功')
    const res = await getDictDataByType(currentDictType.value)
    dictDataList.value = res.data
  })
}

async function handleDeleteData(row) {
  await ElMessageBox.confirm('确定删除该字典数据？')
  await delDictData(row.dictCode)
  ElMessage.success('删除成功')
  const res = await getDictDataByType(currentDictType.value)
  dictDataList.value = res.data
}
</script>
