<template>
  <div class="page-container">
    <div class="action-bar">
      <el-button type="primary" @click="handleAdd" v-hasPerm="'system:menu:add'">新增菜单</el-button>
    </div>
    <el-table :data="tableData" border stripe row-key="menuId" default-expand-all v-loading="loading" :tree-props="{ children: 'children' }">
      <el-table-column prop="menuName" label="菜单名称" />
      <el-table-column prop="icon" label="图标" width="80">
        <template #default="{ row }">
          <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="排序" width="60" />
      <el-table-column prop="path" label="路由地址" />
      <el-table-column prop="component" label="组件路径" />
      <el-table-column prop="perms" label="权限标识" />
      <el-table-column label="类型" width="80">
        <template #default="{ row }">
          <el-tag :type="row.menuType === 0 ? 'primary' : 'info'" size="small">
            {{ row.menuType === 0 ? '菜单' : '按钮' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)" v-hasPerm="'system:menu:edit'">修改</el-button>
          <el-button link type="danger" @click="handleDelete(row)" v-hasPerm="'system:menu:remove'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '修改菜单' : '新增菜单'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级菜单">
          <el-tree-select v-model="form.parentId" :data="menuTree" :props="{ label: 'menuName', value: 'menuId', children: 'children' }" placeholder="顶级菜单" check-strictly clearable style="width: 100%" />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-radio-group v-model="form.menuType">
            <el-radio :value="0">菜单</el-radio>
            <el-radio :value="1">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.orderNum" :min="0" />
        </el-form-item>
        <el-form-item label="路由地址" v-if="form.menuType === 0">
          <el-input v-model="form.path" placeholder="例如: user" />
        </el-form-item>
        <el-form-item label="组件路径" v-if="form.menuType === 0">
          <el-input v-model="form.component" placeholder="例如: /system/user/index" />
        </el-form-item>
        <el-form-item label="权限标识" v-if="form.menuType === 1">
          <el-input v-model="form.perms" placeholder="例如: system:user:list" />
        </el-form-item>
        <el-form-item label="图标" v-if="form.menuType === 0">
          <el-input v-model="form.icon" placeholder="Element Plus 图标名" />
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
import { getMenuTree, addMenu, updateMenu, delMenu } from '@/api/menu'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const menuTree = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)

const form = reactive({ menuId: null, parentId: null, menuName: '', menuType: 0, orderNum: 0, path: '', component: '', perms: '', icon: '' })
const rules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  menuType: [{ required: true, message: '请选择菜单类型', trigger: 'change' }]
}

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const res = await getMenuTree()
    tableData.value = res.data
    menuTree.value = [{ menuId: null, menuName: '顶级菜单', children: res.data }]
  } finally {
    loading.value = false
  }
}

function handleAdd() {
  isEdit.value = false
  Object.assign(form, { menuId: null, parentId: null, menuName: '', menuType: 0, orderNum: 0, path: '', component: '', perms: '', icon: '' })
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
      await updateMenu(form)
      ElMessage.success('修改成功')
    } else {
      await addMenu(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除该菜单？')
  await delMenu(row.menuId)
  ElMessage.success('删除成功')
  fetchData()
}
</script>
