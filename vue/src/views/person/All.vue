<template>
      <el-main class="blurred-background">
        <el-row :gutter="20">
          <el-col :span="6">
            <div style="margin: 20px;">
              <el-row style="margin-bottom: 20px">
                <el-avatar :size="80" src="https://wx1.sinaimg.cn/mw2000/006YfCtqly1hwdpd3ghlhj33lh5efnpl.jpg" />
                <span>春风不语</span>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/pencil.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">文章总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">15</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/star.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">收藏总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">15</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/tag.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">标签总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">15</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
            </div>
          </el-col>
          <el-col :span="12">

          </el-col>
          <el-col :span="6">

          </el-col>
        </el-row>
      </el-main>
</template>

<script setup>

</script>

<style scoped>
.blurred-background {
  background-color: rgba(255, 255, 255, 0.6); /* 设置一个半透明的白色背景，增强模糊效果 */
  backdrop-filter: blur(10px); /* 应用 10px 的模糊效果 */
  padding: 20px;
  border-radius: 10px; /* 可选：为 el-main 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 可选：添加阴影效果 */
  height: 630px;
  margin-left: 20px;
  margin-right: 20px;
}

.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 15px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}
</style>