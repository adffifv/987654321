<template>
<div>
  <!---头部区域--->
  <div style="height:60px;line-height:60px;background-color:lightblue; margin-bottom: 2px;display: flex">
    <div style="width: 300px">
      <img src="@/assets/logo.png"alt="" style="width:40px;height: 40px; position:relative; top: 10px; left: 20px;">
      <span style="margin-left: 20px; font-size: 24px;">门急诊管理系统</span>
    </div>
    <div style="flex: 1; text-align: right;padding-right: 20px">
       <el-dropdown size="medium" trigger="click">
<!--       <span class="el-dropdown-link"><i class="el-icon-user-solid"></i>-->
<!--              {{ user.username }}<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--       </span>-->
         <el-button type="primary"><i class="el-icon-user-solid"></i>
           {{ admin.username }}<i class="el-icon-arrow-down el-icon--right"></i>
         </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item><div style="width: 50px;text-align: center" @click="logout">退出</div></el-dropdown-item>
        </el-dropdown-menu>
       </el-dropdown>
    </div>
  </div>

  <!---侧边栏和主体--->
  <div style="display: flex;">
    <!---侧边栏导航--->
    <div style="width: 200px; min-height: calc( 100vh - 62px ) ; overflow: hidden; margin-right: 2px;  ">
      <el-menu :default-active="$route.path " router class="el-menu-demo"  >
<!--        <el-menu-item index="/"  style="color: #42b983 "><i class="el-icon-house"></i>首页</el-menu-item>-->
        <el-submenu index="user" style="color: #42b983">
          <template slot="title" style="color: #42b983"><i class="el-icon-user"></i>病人账户管理</template>
          <el-menu-item index="/addUser"><i class="el-icon-edit"></i>病人账户添加</el-menu-item>
          <el-menu-item index="/userList"><i class="el-icon-postcard"></i>病人账户列表</el-menu-item>
        </el-submenu>
        <el-submenu index="admin">
          <template slot="title"><i class="el-icon-help"></i>管理员管理</template>
          <el-menu-item index="/addAdmin"><i class="el-icon-edit"></i>管理员添加</el-menu-item>
          <el-menu-item index="/adminList"><i class="el-icon-postcard"></i>管理员列表</el-menu-item>
        </el-submenu>
        <el-submenu index="category">
          <template slot="title"><i class="el-icon-more"></i>门急诊职位管理</template>
          <el-menu-item index="/addCategory"><i class="el-icon-edit"></i>门急诊科类添加</el-menu-item>
          <el-menu-item index="/categoryList"><i class="el-icon-postcard"></i>门急诊职位列表</el-menu-item>
        </el-submenu>
        <el-submenu index="doctor">
          <template slot="title"><i class="el-icon-user-solid"></i>医生管理</template>
          <el-menu-item index="/addDoctor"><i class="el-icon-edit"></i>医生添加</el-menu-item>
          <el-menu-item index="/doctorList"><i class="el-icon-postcard"></i>医生信息列表</el-menu-item>
        </el-submenu>
        <el-submenu index="record">
          <template slot="title"><i class="el-icon-document"></i>挂号管理</template>
          <el-menu-item index="/addRecord"><i class="el-icon-edit"></i>挂号添加</el-menu-item>
          <el-menu-item index="/recordList"><i class="el-icon-postcard"></i>急门诊信息列表</el-menu-item>
        </el-submenu>
        <el-submenu index="returns">
          <template slot="title" style="background-color: blue"><i class="el-icon-document-copy"></i>就诊结果管理</template>
          <el-menu-item index="/returnsList"><i class="el-icon-postcard"></i>急门诊信息列表</el-menu-item>
        </el-submenu>
      </el-menu>
    </div>

    <!---侧边栏主体数据--->
    <div style="flex: 1; background-color: white">
      <router-view/>
    </div>
  </div>
</div>
</template>

<script>
import Cookies from "js-cookie";
export default {
  name: "Layout.vue",
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')):{}
    }
  },
  methods: {
    logout() {
      //清除浏览器用户数据
      Cookies.remove('admin')
      this.$router.push('/login')

    }
  }
}
</script>

<style scoped>

</style>