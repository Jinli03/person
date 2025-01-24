import axios from 'axios'
import router from "@/router";
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: 'http://localhost:9090',
    timeout: 30000,
});
// 请求拦截器
request.interceptors.request.use(config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        let user = JSON.parse(localStorage.getItem("pilot") || '{}')
        config.headers['token'] = user.token
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject(error);
    }
);

// 响应拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if(res.code === '401') {
            router.push("/login")
        }
        return res;
    },
    error => {
        if (error.response.status === 404) {
            ElMessage.error("未找到请求接口")
        } else if (error.response.status === 500) {
            ElMessage.error("系统异常，请查看后端控制器报错")
        } else {
            console.error(error.message);
        }
        return Promise.reject(error);
    }
)
export default request;