import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'

Vue.use(VueRouter)

//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
    //  登录
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue')
  },

    //主页
  {
    path: '/',
    name: 'layout',
    component:Layout,
    redirect: '/home',
    children:[   //    子路由
      {
        path: 'home',
        name: 'Home',
        component: () => import('../../src/views/home/HomeView.vue')
      },


        // user
      {
        path: 'userList',
        name: 'UserList',
        component: () => import('../../src/views/user/User.vue')
      },
      {
        path: 'adduser',
        name: 'AddUser',
        component: () => import('../../src/views/user/AddUser.vue')
      },
      {
        path: 'editUser',
        name: 'EditUser',
        component: () => import('../../src/views/user/EditUser.vue')
      },


      // admin
      {
        path: 'adminList',
        name: 'AdminList',
        component: () => import('../views/admin/List.vue')
      },
      {
        path: 'addAdmin',
        name: 'AddAdmin',
        component: () => import('../views/admin/Add.vue')
      },
      {
        path: 'editAdmin',
        name: 'EditAdmin',
        component: () => import('../views/admin/Edit.vue')
      },


      // category
      {
        path: 'categoryList',
        name: 'CategoryList',
        component: () => import('../views/category/List.vue')
      },
      {
        path: 'addCategory',
        name: 'AddCategory',
        component: () => import('../views/category/Add.vue')
      },
      {
        path: 'editCategory',
        name: 'EditCategory',
        component: () => import('../views/category/Edit.vue')
      },


      // doctor
      {
        path: 'doctorList',
        name: 'DoctorList',
        component: () => import('../views/doctor/List.vue')
      },
      {
        path: 'addDoctor',
        name: 'AddDoctor',
        component: () => import('../views/doctor/Add.vue')
      },
      {
        path: 'editDoctor',
        name: 'EditDoctor',
        component: () => import('../views/doctor/Edit.vue')
      },


      // record
      {
        path: 'recordList',
        name: 'RecordList',
        component: () => import('../views/record/List.vue')
      },
      {
        path: 'addRecord',
        name: 'AddRecord',
        component: () => import('../views/record/Add.vue')
      },
      // returns
      {
        path: 'returnsList',
        name: 'ReturnsList',
        component: () => import('../views/returns/List.vue')
      },
    ]


  },
    // user

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
