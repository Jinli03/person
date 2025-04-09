<!--
*@four
*@author Jinli
*@date 2025/3/9 15:50
-->
<template>
  <el-row :gutter="10">
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 280px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 550px;  color: #ff4949">Ⅰ重要且紧急</span>
          <el-button type="danger" :icon="Plus" circle @click="addOne()"></el-button>
        </div>
        <hr>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['Ⅰ重要且紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['Ⅰ重要且紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 280px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 550px; color: #ffad00">Ⅱ重要不紧急</span>
          <el-button type="warning" :icon="Plus" circle @click="addTwo()"></el-button>
        </div>
        <hr>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card class="four-card">
              <div class="scrollable-content">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['Ⅱ重要不紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['Ⅱ重要不紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
  <div style="margin: 10px"></div>
  <el-row :gutter="10">
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 280px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 535px; color: #0066bc">Ⅲ不重要但紧急</span>
          <el-button type="primary" :icon="Plus" circle @click="addThree()"></el-button>
        </div>
        <hr>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['Ⅲ不重要但紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['Ⅲ不重要但紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 280px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 535px; color: #13ce66">Ⅳ不重要不紧急</span>
          <el-button type="success" :icon="Plus" circle @click="addFour()"></el-button>
        </div>
        <hr>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['Ⅳ不重要不紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card class="four-card">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['Ⅳ不重要不紧急']" :key="task.id" class="task-item">
                  <div style="width: 70%">
                    <el-checkbox
                        :checked="task.state === '已完成'"
                        :class="{'completed': task.state === '已完成'}"
                        @change="toggleTaskStatus(task)"
                    >{{ task.content }}</el-checkbox>
                  </div>
                  <span :style="{ color: getColor(task.kind)}">{{ task.kind }}</span>
                  <el-button :icon="More" circle @click="update(task.id)"></el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
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

import {More, Plus} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";


const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  tasks: {},
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
  state: [
    {
      value: '未完成',
      label: '未完成',
      color: 'orange'
    },
    {
      value: '已完成',
      label: '已完成',
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
    state: [
      {required: true, message: '请选择状态', trigger: 'blur'}
    ],
  },
});

const addOne = () => {
  data.form = {}
  data.form.priority = 'Ⅰ重要且紧急'
  data.formVisible = true
}

const addTwo = () => {
  data.form = {}
  data.form.priority = 'Ⅱ重要不紧急'
  data.formVisible = true
}

const addThree = () => {
  data.form = {}
  data.form.priority = 'Ⅲ不重要但紧急'
  data.formVisible = true
}

const addFour = () => {
  data.form = {}
  data.form.priority = 'Ⅳ不重要不紧急'
  data.formVisible = true
}

const getColor = (kind) => {
  if (kind === "学习") return "red";
  if (kind === "生活") return "green";
  if (kind === "提醒") return "blue";
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

const load = () => {
  request.get('/task/getPriorityById/'+ data.user.username).then(res => {
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
};

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
  data.editFormVisible = false
  request.put('task/updateById', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('修改成功')
      data.form = {}
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

onMounted(() => {
  load();
});
</script>


<style>

.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 5px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
  position: relative;
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}

.four-card{
  height: 200px;
}

/* 滚动内容区域 */
.scrollable-content {
  height: 100%; /* 占满卡片高度 */
  overflow-y: auto; /* 启用垂直滚动条 */
  padding-right: 10px; /* 避免滚动条遮挡内容 */
}

/* 自定义滚动条样式（可选） */
.scrollable-content::-webkit-scrollbar {
  width: 6px; /* 滚动条宽度 */
}

.scrollable-content::-webkit-scrollbar-thumb {
  background: #c1c1c1; /* 滚动条滑块颜色 */
  border-radius: 3px;
}

.scrollable-content::-webkit-scrollbar-track {
  background: #f1f1f1; /* 滚动条轨道颜色 */
}
</style>