// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import naive from 'naive-ui'
import '@/assets/global.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { ElNotification } from 'element-plus' // 引入 ElNotification

const app = createApp(App)

// 全局提醒插件
const reminderPlugin = {
    install: (app) => {
        let notificationInstance = null

        // 检查时间是否在 23:30 - 4:00 之间
        const checkTime = () => {
            const now = new Date()
            const hours = now.getHours()
            const minutes = now.getMinutes()
            const currentTime = hours * 60 + minutes

            const startTime = 23 * 60 + 30// 23:30
            const endTime = 4 * 60 // 4:00

            return currentTime >= startTime || currentTime < endTime
        }

        // 显示提醒
        const showReminder = () => {
            if (!notificationInstance) {
                notificationInstance = ElNotification({
                    title: '晚间提醒',
                    message: '现在已经很晚了，请注意休息！',
                    type: 'warning',
                    duration: 0, // 不自动关闭
                    position: 'top-right',
                    onClose: () => {
                        notificationInstance = null
                    }
                })
            }
        }

        // 关闭提醒
        const closeReminder = () => {
            if (notificationInstance) {
                notificationInstance.close()
                notificationInstance = null
            }
        }

        // 定时检查
        const checkAndUpdate = () => {
            if (checkTime()) {
                showReminder()
            } else {
                closeReminder()
            }
        }

        // 每分钟检查一次
        setInterval(checkAndUpdate, 60 * 1000)
        // 立即执行一次
        checkAndUpdate()

        // 可选：提供给全局使用
        app.config.globalProperties.$reminder = {
            show: showReminder,
            close: closeReminder
        }
    }
}

// 注册插件和组件
app.use(router)
app.use(naive)
app.use(ElementPlus, {
    locale: zhCn,
})
app.use(reminderPlugin) // 添加提醒插件

// 注册 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')