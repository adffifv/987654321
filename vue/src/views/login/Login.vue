<template>
  <div style="height: 100vh">
    <div style="width: 600px;height: 300px;background-color: white;border-radius: 10px;margin: 150px auto">
      <div style="margin: 30px; text-align: center;font-size: 30px;font-weight: bold;color: dodgerblue; " ><i class="el-icon-login"></i>门急诊管理系统</div>
      <el-form ref="form" :model="admin" label-width="80px">
        <el-form-item label="账号">
          <el-input v-model="admin.username" placeholder="请输入账号" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="admin.password" placeholder="请输入账号" show-password prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 80%" size="medium" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>




<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: 'LOGIN',
  data(){
    return{
      admin:{}
    }
  },
  methods:{
    login(){
      request.post('/admin/login',this.admin).then(res =>{
        if (res.code === '200'){
          this.$notify.success("登录成功")
          this.$router.push('/')
          if (res.data !== null){
            Cookies.set('admin',JSON.stringify(res.data))
          }
        }else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<!--<el-form :model="admin" ref="loginForm">-->
<!--  <el-form-item prop="username">-->
<!--    <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.username"></el-input>-->
<!--  </el-form-item>-->
<!--  <el-form-item prop="password">-->
<!--    <el-input placeholder="请输入密码" prefix-icon="el-icon-lock" size="medium" v-model="admin.password"></el-input>-->
<!--  </el-form-item>-->
<!--  <el-form-item>-->
<!--    <el-button style="width: 100%" size="medium" type="primary" @click="login">登录</el-button>-->
<!--  </el-form-item>-->
<!--</el-form>-->