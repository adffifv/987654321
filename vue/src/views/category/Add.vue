<template>
  <div style="width: 80%;margin-left:30px">
    <h2 style="margin-bottom: 30px"><i class="el-icon-edit"></i>新增急门诊科类</h2>
    <el-form :inline="true" :model="form" style="" label-width="120px">
      <el-form-item label="科类名称" prop="username">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
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
  name: 'AddCategory',
  data(){
    return {
      form: {}
    }
    },
    methods: {
      save(){
        request.post('/category/save',this.form).then(res =>{
          if (res.code === '200'){
            this.$notify.success('新增成功')
            this.form={}
          }else {
            this.$notify.error(res.msg)
          }
        })
      }
    }
  }


</script>