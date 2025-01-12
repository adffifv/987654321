<template>
  <div style="width: 80%;margin-left:30px">
    <h2 style="margin-bottom: 30px"><i class="el-icon-edit"></i>编辑账户</h2>
    <el-form :inline="true" :model="form" style="" label-width="120px">
      <el-form-item label="姓名">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
<!--      <el-form-item label="用户名">-->
<!--        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="年龄">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top:30px">
      <el-button type="primary" @click="save">提交</el-button>
<!--      <el-button type="danger" @click="">取消</el-button>-->
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: 'EditUser',
  data(){
    return {
      form: {}
    }
    },
  created(){
    const id=this.$route.query.id
    request.get("/user/"+ id).then(res => {
      this.form=res.data
    })
  },
    methods: {
      save(){
        request.put('/user/update',this.form).then(res =>{
          if (res.code === '200'){
            this.$notify.success('修改成功')
            //
            this.$router.push("/userList")
          }else {
            this.$notify.error(res.msg)
          }
        })
      }
    }
  }


</script>