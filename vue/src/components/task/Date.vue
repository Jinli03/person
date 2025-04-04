<!--
*@date
*@author Jinli
*@date 2025/3/9 15:49
-->
<template>
  <el-row :gutter="20">
    <el-col :span="5">
      <el-card class="transparent-card">
        <VDatePicker v-model="date" mode="date" @update:model-value="fetchDataByDate"/>
      </el-card>
    </el-col>
    <el-col :span="18">
      <el-card class="card">
        <el-collapse accordion>
          <el-collapse-item title="待办" name="1">
            <div v-if="data.tasks['未完成']">
              <div v-for="task in data.tasks['未完成']['学习']" :key="task.id" class="task-item">
                <div style="width: 80%">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <span :style="{ color: getKindColor(task.kind) }">{{ task.kind }}</span>
                <span :style="{ color: getPriorityColor(task.priority) }">{{ task.priority }}</span>
                <el-button :icon="More" circle @click="update(task.id)"></el-button>
              </div>
              <div v-for="task in data.tasks['未完成']['生活']" :key="task.id" class="task-item">
                <div style="width: 80%">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <span :style="{ color: getKindColor(task.kind) }">{{ task.kind }}</span>
                <span :style="{ color: getPriorityColor(task.priority) }">{{ task.priority }}</span>
                <el-button :icon="More" circle @click="update(task.id)"></el-button>
              </div>
              <div v-for="task in data.tasks['未完成']['提醒']" :key="task.id" class="task-item">
                <div style="width: 80%">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <span :style="{ color: getKindColor(task.kind) }">{{ task.kind }}</span>
                <span :style="{ color: getPriorityColor(task.priority) }">{{ task.priority }}</span>
                <el-button :icon="More" circle @click="update(task.id)"></el-button>
              </div>
            </div>
          </el-collapse-item>
          <el-collapse-item title="已打卡" name="2">
            <div v-if="data.tasks['已完成']">
              <div v-for="task in data.tasks['已完成']['学习']" :key="task.id" class="task-item">
                <div style="width: 80%">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <span :style="{ color: getKindColor(task.kind) }">{{ task.kind }}</span>
                <span :style="{ color: getPriorityColor(task.priority) }">{{ task.priority }}</span>
                <el-button :icon="More" circle @click="update(task.id)"></el-button>
              </div>
              <div v-for="task in data.tasks['已完成']['生活']" :key="task.id" class="task-item">
                <div style="width: 80%">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <span :style="{ color: getKindColor(task.kind) }">{{ task.kind }}</span>
                <span :style="{ color: getPriorityColor(task.priority) }">{{ task.priority }}</span>
                <el-button :icon="More" circle @click="update(task.id)"></el-button>
              </div>
              <div v-for="task in data.tasks['已完成']['提醒']" :key="task.id" class="task-item">
                <div style="width: 80%">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <span :style="{ color: getKindColor(task.kind) }">{{ task.kind }}</span>
                <span :style="{ color: getPriorityColor(task.priority) }">{{ task.priority }}</span>
                <el-button :icon="More" circle @click="update(task.id)"></el-button>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </el-col>
  </el-row>

  <el-dialog v-model="data.formVisible" title="新建任务" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item prop="content">
        <el-input v-model="data.form.content" autocomplete="off" placeholder="输入任务标题"/>
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="kind">
            <el-select v-model="data.form.kind" placeholder="添加分类" style="width: 240px">
              <el-option
                  v-for="item in data.kinds"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="priority">
            <el-select v-model="data.form.priority" placeholder="添加优先级" style="width: 240px">
              <el-option
                  v-for="item in data.priority"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :style="{ color: item.color }"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <!--      <el-form-item prop="start">-->
      <!--        <el-date-picker v-model="data.form.start" type="datetime" placeholder="选择时间"/>-->
      <!--      </el-form-item>-->
      <el-form-item prop="warn">
        <el-date-picker v-model="data.form.warn" type="datetime" placeholder="创建提醒"/>
      </el-form-item>
      <el-form-item prop="note">
        <el-input v-model="data.form.note" autocomplete="off" placeholder="添加备注"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.formVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="data.editFormVisible" title="修改任务" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item prop="content">
        <el-input v-model="data.form.content" autocomplete="off" placeholder="输入任务标题"/>
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="kind">
            <el-select v-model="data.form.kind" placeholder="添加分类" style="width: 240px">
              <el-option
                  v-for="item in data.kinds"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="priority">
            <el-select v-model="data.form.priority" placeholder="添加优先级" style="width: 240px">
              <el-option
                  v-for="item in data.priority"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :style="{ color: item.color }"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="start">
            <el-date-picker v-model="data.form.start" type="datetime" placeholder="选择开始时间"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="warn">
            <el-date-picker v-model="data.form.finish" type="datetime" placeholder="选择结束时间"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item prop="tag">
        <el-input v-model="data.form.tag" autocomplete="off" placeholder="添加标签"/>
      </el-form-item>
      <el-form-item prop="state">
        <el-select v-model="data.form.state" placeholder="选择状态" style="width: 240px">
          <el-option
              v-for="item in data.state"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :style="{ color: item.color }"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="note">
        <el-input v-model="data.form.note" autocomplete="off" placeholder="添加备注"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import {More} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const date = ref(new Date())

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  tasks: [],
  form: {},
  formVisible: false,
  editFormVisible: false,
  kinds: [
    {
      value: '学习',
      label: '学习',
    },
    {
      value: '生活',
      label: '生活',
    },
    {
      value: '提醒',
      label: '提醒',
    }
  ],
  priority: [
    {
      value: 'Ⅰ重要且紧急',
      label: 'Ⅰ重要且紧急',
      color: 'red'
    },
    {
      value: 'Ⅱ重要不紧急',
      label: 'Ⅱ重要不紧急',
      color: 'orange'
    },
    {
      value: 'Ⅲ不重要但紧急',
      label: 'Ⅲ不重要但紧急',
      color: 'blue'
    },
    {
      value: 'Ⅳ不重要不紧急',
      label: 'Ⅳ不重要不紧急',
      color: 'green'
    },
  ],
  rules: {
    content: [
      {required: true, message: '请输入标题', trigger: 'blur'}
    ],
    kind: [
      {required: true, message: '请选择种类', trigger: 'blur'}
    ],
    priority: [
      {required: true, message: '请选择优先级', trigger: 'blur'}
    ],
    start: [
      {required: true, message: '请选择时间', trigger: 'blur'}
    ],
  },
  state: [
    {
      value: '未完成',
      label: '未完成',
    },
    {
      value: '已完成',
      label: '已完成',
    }
  ]
})

const fetchDataByDate = () => {
  const formattedDate = date.value.toISOString().split("T")[0];
  request.get('/task/getAllByIdAndDate',  {
    params: {
      username: data.user.username,
      date: formattedDate,
    },
  }).then(res => {
    if (res.code === '200') {
      // 清空现有数据并更新
      Object.keys(data.tasks).forEach(key => {
        delete data.tasks[key];
      });
      Object.assign(data.tasks, res.data);
    } else {
      ElMessage.error(res.msg);
    }
  });
}

const update = (id) => {
  request.get('/task/selectById/'+ id).then(res => {
    if (res.code === '200') {
      data.form = res.data
      data.editFormVisible = true
    } else {
      ElMessage.error(res.msg);
    }
  });
  data.editFormVisible = true
}

const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.form.id ? edit() : add();
    } else {
      ElMessage.error('请检查表单内容！');
    }
  })
  // data.form.id ? edit() : add();
}

const add = () => {
  data.formVisible = false
  data.form.username = data.user.username
  request.post('task/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      data.form = {}
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const edit = () => {
  // data.formVisible = false
  request.put('task/updateById', data.form).then(res => {
    if (res.code === '200') {
      data.editFormVisible = false
      ElMessage.success('修改成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const load = () => {
  fetchDataByDate()
};

onMounted(() => {
  load();
});

const getKindColor = (kind) => {
  if (kind === "学习") return "red";
  if (kind === "生活") return "green";
  if (kind === "提醒") return "blue";
  return "black"; // 默认颜色
}

const getPriorityColor = (priority) => {
  if (priority === "Ⅰ重要且紧急") return "red";
  if (priority === "Ⅱ重要不紧急") return "orange";
  if (priority === "Ⅲ不重要但紧急") return "blue";
  if (priority === "Ⅳ不重要不紧急") return "green";
  return "black"; // 默认颜色
}

const toggleTaskStatus = (task) => {
  // 切换状态
  task.state = task.state === '已完成' ? '未完成' : '已完成';

  // 发送请求到后端更新状态
  request.put('/task/updateStateById', task).then(res => {
    if (res.code === '200') {
      // 更新成功后重新加载所有数据
      load();
      ElMessage.success('状态更新成功');
    } else {
      ElMessage.error(res.msg);
    }
  }).catch(err => {
    ElMessage.error('更新失败');
  });
};

</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.task-card {
  max-width: 300px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  cursor: pointer;
}

.completed {
  text-decoration: line-through;
  color: #999;
}

</style>