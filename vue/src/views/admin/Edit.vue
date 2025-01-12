<template>
  <div style="width: 80%;margin-left:30px">
    <h2 style="margin-bottom: 30px"><i class="el-icon-edit"></i>编辑leib</h2>
    <el-form :inline="true" :model="form" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="form.phone" placeholder="请输入联系"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
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
  name: 'EditAdmin',
  data(){
    return {
      form: {}
    }
    },
  created(){
    const id=this.$route.query.id
    request.get("/admin/"+ id).then(res => {
      this.form=res.data
    })
  },
    methods: {
      save(){
        request.put('/admin/update',this.form).then(res =>{
          if (res.code === '200'){
            this.$notify.success('修改成功')
            //
            this.$router.push("/adminList")
          }else {
            this.$notify.error(res.msg)
          }
        })
      }
    }
  }


</script>