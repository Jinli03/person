<!--
*@sort
*@author Jinli
*@date 2025/3/9 15:49
-->
<template>
  <el-row :gutter="20">
    <el-col :span="6" class="full-height-row">
      <el-card class="transparent-card">
        <div style="margin: 0px; padding: 0px; display: flex; align-items: center; margin-bottom: 10px">
          <img src="@/assets/task/calander.png" style="width: 20px; margin: 5px"/>
          <span style="font-size: 20px; margin-right: 180px">全部</span>
          <el-button type="warning" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
        <hr>
        <el-card class="card">
          <el-collapse accordion>
            <el-collapse-item title="待办" name="1">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['学习']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <div v-for="task in data.tasks['未完成']['生活']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <div v-for="task in data.tasks['未完成']['提醒']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
            <el-collapse-item title="已打卡" name="2">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['学习']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <div v-for="task in data.tasks['已完成']['生活']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
                <div v-for="task in data.tasks['已完成']['提醒']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-card>
    </el-col>

    <el-col :span="6">
      <el-card class="transparent-card">
        <div style="margin: 0px; padding: 0px; display: flex; align-items: center; margin-bottom: 10px">
          <img src="@/assets/task/study.png" style="width: 20px; margin: 5px"/>
          <span style="font-size: 20px; margin-right: 200px">学习</span>
          <el-button type="danger" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
        <hr>
        <el-card class="card">
          <el-collapse accordion>
            <el-collapse-item title="待办" name="1" class="transparent-collapse">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['学习']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
            <el-collapse-item title="已打卡" name="2" class="transparent-collapse">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['学习']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-card>
    </el-col>

    <el-col :span="6">
      <el-card class="transparent-card">
        <div style="margin: 0px; padding: 0px; display: flex; align-items: center; margin-bottom: 10px">
          <img src="@/assets/task/life.png" style="width: 20px; margin: 5px"/>
          <span style="font-size: 20px; margin-right: 200px">生活</span>
          <el-button type="success" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
        <hr>
        <el-card class="card">
          <el-collapse accordion>
            <el-collapse-item title="待办" name="1">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['生活']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
            <el-collapse-item title="已打卡" name="2">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['生活']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-card>
    </el-col>

    <el-col :span="6">
      <el-card class="transparent-card">
        <div style="margin: 0px; padding: 0px; display: flex; align-items: center; margin-bottom: 10px">
          <img src="@/assets/task/todo.png" style="width: 20px; margin: 5px"/>
          <span style="font-size: 20px; margin-right: 200px">提醒</span>
          <el-button type="primary" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
        <hr>
        <el-card class="card">
          <el-collapse accordion>
            <el-collapse-item title="待办" name="1">
              <div v-if="data.tasks['未完成']">
                <div v-for="task in data.tasks['未完成']['提醒']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
            <el-collapse-item title="已打卡" name="2">
              <div v-if="data.tasks['已完成']">
                <div v-for="task in data.tasks['已完成']['提醒']" :key="task.id" class="task-item">
                  <el-checkbox
                      :checked="task.state === '已完成'"
                      :class="{'completed': task.state === '已完成'}"
                      @change="toggleTaskStatus(task)"
                  >{{ task.content }}</el-checkbox>
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-card>
    </el-col>
  </el-row>
  <el-dialog>

  </el-dialog>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { Plus } from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import { ElMessage } from "element-plus";

const data = reactive({
  tasks: {},
});

const toggleTaskStatus = (task) => {
  // 切换状态
  task.state = task.state === '已完成' ? '未完成' : '已完成';

  // 发送请求到后端更新状态
  request.put('/task/updateById', task).then(res => {
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
  request.get('/task/all').then(res => {
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

onMounted(() => {
  load();
});
</script>

<style scoped>
.full-height-row {
  height: 78vh;
  display: flex;
}

.transparent-card {
  background-color: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(0, 0, 0, 0.1);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  padding: 5px;
  transition: background-color 0.3s ease;
  position: relative;
  height: 100%;
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9);
}

.card {
  height: 450px;
  border-radius: 8px;
}

.el-collapse-item__header {
  border-radius: 8px;
  background-color: transparent;
}

.task-item {
  margin: 8px 0;
}

.completed {
  text-decoration: line-through;
  color: #999;
}
</style>
