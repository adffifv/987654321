<template>
  <div style="width: 80%;margin-left:30px">
    <h2 style="                                                                                                                         margin-bottom: 30px"><i class="el-icon-edit"></i>编辑职位</h2>
    <el-form :inline="true" :model="form" label-width="120px">
      <el-form-item label="职位" prop="username">
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
  name: 'EditCategory',
  data(){
    return {
      form: {}
    }
    },
  created(){
    const id=this.$route.query.id
    request.get("/category/"+ id).then(res => {
      this.form=res.data
    })
  },
    methods: {
      save(){
        request.put('/category/update',this.form).then(res =>{
          if (res.code === '200'){
            this.$notify.success('修改成功')
            //
            this.$router.push("/categoryList")
          }else {
            this.$notify.error(res.msg)
          }
        })
      }
    }
  }


</script>