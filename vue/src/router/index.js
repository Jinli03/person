import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login', component: () => import('../views/Home.vue') },
    { path: '/manager',  component: () => import('../views/Manager.vue'), children: [
            { path: 'home', name: 'home', meta:{title: '主页'}, component: () => import('../views/Home.vue') },
            { path: 'test', name: 'test', meta:{title: '测试页'}, component: () => import('../views/Test.vue') },
            { path: 'data', name: 'data', meta:{title: '数据'}, component: () => import('../views/Data.vue') },
            { path: 'employee', name: 'employee', meta:{title: '员工信息'}, component: () => import('../views/Employee.vue') },
            { path: 'admin', name: 'admin', meta:{title: '管理员信息'}, component: () => import('../views/Admin.vue') },
            // { path: 'person', name: 'person', meta:{title: '个人信息'}, component: () => import('../views/Person.vue') },
            { path: 'homePage', name: 'homePage', meta:{title: '主页'}, component: () => import('../views/HomePage.vue') },
            { path: 'share', name: 'share', meta:{title: '知识分享'}, component: () => import('../views/Share.vue') },
            { path: 'study', name: 'study', meta:{title: '学习时长'}, component: () => import('../views/Study.vue') },
            { path: 'wall', name: 'wall', meta:{title: '勋章墙'}, component: () => import('../views/Wall.vue') },
            { path: 'life', name: 'life', meta:{title: '生活分享'}, component: () => import('../views/Life.vue') },
            { path: 'pic/:id', name: 'pic', meta:{title: '相册'}, component: () => import('../views/life/Pic.vue') },
            { path: 'books', name: 'books', meta:{title: '阅读记录'}, component: () => import('../views/record/Books.vue') },
            { path: 'check', name: 'check', meta:{title: '打卡记录'}, component: () => import('../views/Check.vue') },
            { path: 'book/:id', name: 'book', meta:{title: '图书详情'}, component: () => import('../views/record/Book.vue') },
            { path: 'article/:id', name: 'article', meta:{title: '文章详情'}, component: () => import('../views/share/Article.vue') },
            { path: 'echarts', name: 'echarts', meta:{title: '图表'}, component: () => import('../views/Echarts.vue') },
            { path: 'task', name: 'task', meta:{title: '任务'}, component: () => import('../views/goal/Task.vue') },
            { path: 'habit', name: 'habit', meta:{title: '习惯'}, component: () => import('../views/Habit.vue') },
            { path: 'friends', name: 'friends', meta:{title: '好友列表'}, component: () => import('../views/Friends.vue') },
            { path: 'pyq', name: 'pyq', meta:{title: '好友动态'}, component: () => import('../views/Pyq.vue') },
            { path: 'dynamic/:id', name: 'dynamic', meta:{title: '文章详情'}, component: () => import('../views/friends/Dynamic.vue') },
            { path: 'audit', name: 'audit', meta:{title: '审核'}, component: () => import('../views/Audit.vue') },
            { path: 'person', name: 'person', meta:{title: '个人信息'}, component: () => import('../views/Person.vue'), children: [
                    { path: 'all', name: 'all', meta:{title: '总'}, component: () => import('../views/person/All.vue') },
                    { path: 'edit', name: 'edit', meta:{title: '修改信息'}, component: () => import('../views/person/Edit.vue') },
                    { path: 'lock', name: 'lock', meta:{title: '修改密码'}, component: () => import('../views/person/Lock.vue') },] },
      ]},
    { path: '/404', name: 'NotFound', meta:{title: '找不到页面'}, component: () => import('../views/404.vue') },
    { path: '/login', name: 'login', meta:{title: '登陆页面'}, component: () => import('../views/Login.vue') },
    { path: '/register', name: 'register', meta:{title: '注册页面'}, component: () => import('../views/Register.vue') },
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
