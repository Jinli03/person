<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <div style="margin-top: 30px; margin-left: 50px">
        <h1 style="font-size: 50px; color: white; margin-left: 100px">
          Hi!  {{ data.user.name}} 👋<br />
        </h1>
        <div class="body">
          <div class="container">
            <div class="left-info">
              <div class="pic-gradient">
              </div>
              <div class="today-info">
                <span>{{ weather.weatherData[0]?.fxDate }}</span>
                <div style="display: flex; align-items: center;">
                  <!-- Icon -->
                  <i class="bx bx-current-location" style="margin-right: 8px;"></i>

                  <!-- Position Component (you can style it if needed) -->
                  <el-icon><Location /></el-icon>

                  <!-- City Name -->
                  <h2 style="margin-left: 5px;">{{ data.city }}</h2>
                </div>
              </div>
              <div class="today-weather">
                <i :class="'qi-' + (weather.weatherData?.[0]?.iconDay || '') + ' weather-icon'"></i>
                <h1 class="weather-temp">{{ weather.weatherData[0]?.tempMax }}℃</h1>
                <h3>{{ weather.weatherData[0]?.textDay }}</h3>
              </div>
            </div>
            <div class="right-info">
              <div class="day-info">
                <div>
                  <span class="title">降雨概率</span>
                  <span class="value">{{ weather.weatherData[0]?.precip }} %</span>
                </div>
                <div>
                  <span class="title">湿度</span>
                  <span class="value">{{ weather.weatherData[0]?.humidity }} %</span>
                </div>
                <div>
                  <span class="title">风速</span>
                  <span class="value">{{ weather.weatherData[0]?.windSpeedDay }} km/h</span>
                </div>
              </div>
              <ul class="days-list">
                <li>
                  <i :class="'qi-' + (weather.weatherData?.[1]?.iconDay || '') + ' weather-icon'"></i>
                  <span>{{ getMonthDay(weather.weatherData[1]?.fxDate) }}</span>
                  <span class="day-temp">{{ weather.weatherData[1]?.tempMax }}℃</span>
                </li>
                <li>
                  <i :class="'qi-' + (weather.weatherData?.[2]?.iconDay || '') + ' weather-icon'"></i>
                  <span>{{ getMonthDay(weather.weatherData[2]?.fxDate) }}</span>
                  <span class="day-temp">{{ weather.weatherData[2]?.tempMax }}℃</span>
                </li>
                <li>
                  <i :class="'qi-' + (weather.weatherData?.[3]?.iconDay || '') + ' weather-icon'"></i>
                  <span>{{ getMonthDay(weather.weatherData[3]?.fxDate) }}</span>
                  <span class="day-temp">{{ weather.weatherData[3]?.tempMax }}℃</span>
                </li>
                <li>
                  <i :class="'qi-' + (weather.weatherData?.[4]?.iconDay || '') + ' weather-icon'"></i>
                  <span>{{ getMonthDay(weather.weatherData[4]?.fxDate) }}</span>
                  <span class="day-temp">{{ weather.weatherData[4]?.tempMax }}℃</span>
                </li>
              </ul>
              <div class="btn-container">
                <el-button class="loc-button" v-model="data.city" @click="handleClick">搜索城市</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="12">
      <div style="margin-top: 150px; margin-left: 300px">
        <div class="block">
          <el-avatar :size="200" :src="data.user.avatar" />
        </div>
      </div>
      <div style="margin-left: 185px; margin-top: 50px">
        <div style="display: flex">
          <div>
            <el-card class="transparent-card">
              <img style="width:30px; cursor: pointer;" src="@/assets/github.png" cursor @click="open(data.user.github);">
            </el-card>
          </div>
          <div style="margin-left: 45px">
            <el-card class="transparent-card">
              <img style="width:30px; cursor: pointer;" src="@/assets/tiktok.png" cursor @click="open(data.user.tiktok);">
            </el-card>
          </div>
          <div style="margin-left: 45px">
            <el-card class="transparent-card">
              <img style="width:30px; cursor: pointer;" src="@/assets/redbook.png" cursor @click="open(data.user.redbook);">
            </el-card>
          </div>
          <div style="margin-left: 45px">
            <el-card class="transparent-card">
              <img style="width:30px; cursor: pointer;" src="@/assets/bilibili.png" cursor @click="open(data.user.bilibili)">
            </el-card>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
  <el-dialog v-model="data.formVisible" title="城市" width="500" destroy-on-close>
    <el-form ref="formRef"  :model="data.city" style="padding: 20px">
      <el-form-item label="标题" prop="city">
        <el-input v-model="data.city" autocomplete="off" placeholder="请输入城市"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="data.formVisible = false">取消</el-button>
      <el-button type="primary" @click="fetchWeatherData(data.city)">
        查询
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import router from "@/router/index.js";
import {reactive, ref, onMounted} from "vue";
import {Plus} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
const link = document.createElement('link');
link.rel = 'stylesheet';
link.href = 'https://cdn.jsdelivr.net/npm/qweather-icons@1.6.0/font/qweather-icons.css';
document.head.appendChild(link);

const formRef = ref()

const weather = reactive({
  weatherData: []
})

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  city: '上海',
  formVisible: false,
  rules: {
    city: [
      {required: true, message: '请输入城市', trigger: 'blur'}
    ],
  },
})

function open(data) {
  if (data) {
    window.open(data, '_blank')
  } else {
    ElMessage.error('地址未设置')
  }
}


const getMonthDay = (fxDate) => {
  if (!fxDate) return 'Loading...'; // Handle undefined date
  const [year, month, day] = fxDate.split('-');  // Split fxDate by '-'
  return `${month}-${day}`;  // Return formatted MM-DD
};

const apiKey = 'cc513af71986488585c4f4f4eea5c265'
const apiHost = 'nd4nmrc8au.re.qweatherapi.com'
// const locButton = document.querySelector('.loc-button')
// const todayInfo = document.querySelector('.today-info')
// const todayWeatherIcon = document.querySelector('.today-weather i')
// const todayTemp = document.querySelector('.today-temp')
// const daysList = document.querySelector('.days-list')

onMounted(() => {
  fetchWeatherData(data.city)
})

const fetchWeatherData = (city) => {
  const locationUrl = `https://${apiHost}/geo/v2/city/lookup?location=${data.city}&key=${apiKey}`
  fetch(locationUrl).then(res => {
    if(res.status !== 200) {
      throw new Error('请输入正确的城市名')
    }
    return res.json()
  }).then(
    data => {
      const locationId = data.location[0].id
      const furtherWeatherUrl = `https://${apiHost}/v7/weather/7d?location=${locationId}&key=${apiKey}`
      fetch(furtherWeatherUrl).then(res => res.json()).then(data => {
        console.log(data)
        weather.weatherData = data.daily
        console.log(data.daily)
        console.log(weather.weatherData)
        console.log(weather.weatherData[0])
      })
    }
  )
  data.formVisible = false
}

const handleClick = () => {
  data.formVisible = true
}
</script>

<style scoped>
.transparent-card {
  background-color: transparent; /* 半透明白色背景 */
  border-radius: 8px; /* 圆角处理 */
  border: none;
}
.transparent-card:hover {
  transform: rotate(45deg); /* 悬浮时顺时针旋转 45 度 */
}
.block {
  display: inline-block; /* 确保元素是块级或内联块元素 */
  transition: transform 0.3s ease; /* 添加平滑的过渡效果 */
}

.block:hover {
  transform: rotate(45deg); /* 悬停时旋转 15 度 */
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}

.body {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  height: 400px;
  background-color: #232931;
  color: #fff;
  border-radius: 25px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
}

.left-info {
  width: 260px;
  height: 100%;
  float: left;
  display: flex;
  border-radius: 25px;
  justify-content: center;
  background-image: url("https://haowallpaper.com/link/common/file/previewFileImg/16477395881414016");
  background-position: center;
  background-size: cover;
  transform: scale(1.05) perspective(200px);
  cursor: pointer;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
}

.left-info:hover {
  transform: scale(1.1) perspective(2500px) rotateY(5deg);
}

.pic-gradient {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;

  border-radius: 25px;
  opacity: .5;
}

.today-info {
  position: absolute;
  display: flex;
  flex-direction: column;
  margin-top: 25px;
}

.today-weather {
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  bottom: 20px;
  left: 20px;
}

.today-weather .weather-icon {
  font-size: 4.6rem;
}

.weather-temp {
  font-weight: 700;
  font-size: 3.5rem;
}

.right-info {
  float: right;
  position: relative;
  height: 100%;
  padding-top: 25px;
}

.day-info {
  padding: 25px 35px;
  display: flex;
  flex-direction: column;
}

.day-info div:not(:last-child) {
  margin-bottom: 6px;
}

.day-info div .title {
  font-weight: 700;
}

.day-info div .value {
  float: right;
}

.days-list {
  display: flex;
  list-style: none;
  margin: 10px 35px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
  border-radius: 10px;
}

.days-list li {
  padding: 15px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.days-list li.weather-icon {
  margin-bottom: 5px;
  font-size: 2rem;
}

.days-list li:hover {
  transform: scale(1.1);
  background: #fff;
  color: #222831;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
}

.days-list .day-temp {
  margin-top: 6px;
  font-weight: 700;
}

.btn-container {
  padding: 25px 35px;
}

.loc-button {
  outline: none;
  width: 100%;
  border: none;
  font-weight: 700;
  border-radius: 25px;
  padding: 10px;
  background: #ffffff;
  color: #000;
  cursor: pointer;
  font-size: 1rem;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
  transition: all 0.3s ease;
}

.loc-button:hover {
  background-color: #fff;
}
</style>