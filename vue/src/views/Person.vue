<template>
  <el-container>
    <el-aside
        class="sidebar"
        style="width: 80px; height: 500px; background-color: white; margin-top: 50px; border-top-right-radius: 20px; border-bottom-right-radius: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
      <div style="margin: 15px">
        <!-- Edit Icon -->
        <el-icon
            class="sidebar-icon"
            :class="{ active: data.isActive === 'edit' }"
            style="font-size: 40px; margin-bottom: 10px"
            @click="handleEditClick" >
          <Edit />
        </el-icon>

        <!-- User Icon -->
        <el-icon
            class="sidebar-icon"
            :class="{ active: data.isActive === 'user' }"
            style="font-size: 40px"
            @click="handleUserClick" >
          <User />
        </el-icon>

        <el-icon
            class="sidebar-icon"
            :class="{ active: data.isActive === 'lock' }"
            style="font-size: 40px"
            @click="handleLockClick" >
          <Lock />
        </el-icon>
      </div>
    </el-aside>
    <RouterView @updateUser="updateUser" />
<!--    <el-main class="blurred-background">-->
<!--      <el-row :gutter="20">-->
<!--        <el-col :span="6">-->
<!--          <div style="margin: 20px;">-->
<!--            <el-row style="margin-bottom: 10px">-->
<!--              <el-avatar :size="80" src="https://wx1.sinaimg.cn/mw2000/006YfCtqly1hwdpd3ghlhj33lh5efnpl.jpg" />-->
<!--              <span>春风不语</span>-->
<!--            </el-row>-->
<!--            <el-row>-->
<!--              <el-card class="transparent-card">-->

<!--              </el-card>-->
<!--            </el-row>-->
<!--          </div>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->

<!--        </el-col>-->
<!--        <el-col :span="6">-->

<!--        </el-col>-->
<!--      </el-row>-->
<!--    </el-main>-->
  </el-container>
</template>

<script setup>
import { reactive, ref } from "vue";
import { Edit, User } from '@element-plus/icons-vue';
import router from "@/router/index.js";

const data = reactive({
  isActive: 'edit'
})

const setActive = (icon) => {
  data.isActive = icon;
}

const handleEditClick = () => {
  setActive('edit');
  router.push('/manager/person/all');
};

const handleUserClick = () => {
  setActive('user');
  router.push('/manager/person/edit');
};

const handleLockClick = () => {
  setActive('lock');
  router.push('/manager/person/lock');
};

const emits = defineEmits(['updateUser']);
const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('pilot'));
  emits('updateUser');
};

</script>

<style scoped>


.sidebar {
  transition: all 0.5s ease; /* 设置默认的过渡时间为 0.5 秒 */
}

.sidebar:hover {
  transform: scale(1.05); /* Slightly enlarge the sidebar */
  transition: all 0.3s ease;
  filter: brightness(1.2); /* Increase brightness */
}

/* Icon styles */
.sidebar-icon {
  cursor: pointer; /* Change the cursor to indicate it's clickable */
  transition: transform 0.2s ease, color 0.2s ease; /* Smooth transition for the hover effect */
}

/* Active state for icons */
.sidebar-icon.active {
  color: #409EFF; /* Color when icon is active (Element Plus primary blue) */
  transform: scale(1.1); /* Slightly enlarge the icon when it's active */
}


</style>
