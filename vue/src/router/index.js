import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/manager/homePage', component: () => import('../views/Home.vue') },
    { path: '/manager',  component: () => import('../views/Manager.vue'), children: [
        { path: 'home', name: 'home', meta:{title: '主页'}, component: () => import('../views/Home.vue') },
        { path: 'test', name: 'test', meta:{title: '测试页'}, component: () => import('../views/Test.vue') },
        { path: 'data', name: 'data', meta:{title: '数据'}, component: () => import('../views/Data.vue') },
        { path: 'employee', name: 'employee', meta:{title: '员工信息'}, component: () => import('../views/Employee.vue') },
        { path: 'homePage', name: 'homePage', meta:{title: '主页'}, component: () => import('../views/HomePage.vue') },
      ]},
    { path: '/404', name: 'NotFound', meta:{title: '找不到页面'}, component: () => import('../views/404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404'}
    // { path: '/home', name: 'home', component: () => import('../views/Home.vue') },
    // { path: '/test', name: 'test', component: () => import('../views/Test.vue') },
  ],
})


router.beforeEach((to, from, next) => {
  document.title = to.meta.title
  next()
})
export default router
