<template>
  <div style="width: 80%;margin-left:30px">
    <h2 style="margin-bottom: 30px"><i class="el-icon-edit"></i>修改医护人员信息</h2>
    <el-form :inline="true" :model="form" label-width="120px">
      <el-form-item label="姓名" prop="username">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input style="width: 500px" type="textarea" v-model="form.description" placeholder="请输入备注"></el-input>
      </el-form-item>
      <el-form-item label="坐诊开始时间">
        <el-time-picker
            v-model="form.publishDate"
            :picker-options="{
      selectableRange: '00:00:00 - 23:59:59'
    }"
            placeholder="任意时间点"
            value-format="HH:mm:ss"> <!-- 修改这里为时间格式 -->
        </el-time-picker>
      </el-form-item>
      <el-form-item label="坐诊结束时间">
        <el-time-picker
            v-model="form.overDate"
            :picker-options="{
      selectableRange: '00:00:00 - 23:59:59'
    }"

            placeholder="任意时间点"
            value-format="HH:mm:ss">
        </el-time-picker>
        <!--        value-format="yyyy-MM-dd"-->
      </el-form-item>
      <el-form-item label="费用">
        <el-input-number v-model="form.author" :min="1" placeholder="请输入费用"></el-input-number>
      </el-form-item>
      <el-form-item label="职位">
        <el-cascader

            :props="{value:'name',label:'name'}"
            v-model="form.categories"
            :options="categories"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="诊室">
<!--        <el-input v-model="form.cover" placeholder="请填充照片"></el-input>-->
        <el-select v-model="form.cover" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.label">
          </el-option>
        </el-select>
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
  name: 'EditDoctor',
  data(){
    return {
      form: {},
      categories:[],
      options: [{
        value: '选项1',
        label: '101'
      },  {
        value: '选项3',
        label: '102'
      }, {
        value: '选项4',
        label: '103'
      },{
        value: '选项4',
        label: '104'
      },{
        value: '选项4',
        label: '201'
      },{
        value: '选项4',
        label: '202'
      },{
        value: '选项4',
        label: '203'
      },{
        value: '选项4',
        label: '204'
      },
      ],
      value: ''

    }
    },
  created(){
    request.get('/category/tree').then(res =>{
      this.categories = res.data
    })
    const id=this.$route.query.id
    request.get("/doctor/"+ id).then(res => {
      this.form=res.data
      if (this.form.category){
        this.form.categories = this.form.category.split(">")
      }
    })
  },
    methods: {

      save(){
        request.put('/doctor/update',this.form).then(res =>{
          if (res.code === '200'){
            this.$notify.success('修改成功')
            //
            this.$router.push("/doctorList")
          }else {
            this.$notify.error(res.msg)
          }
        })
      }
    }
  }


</script>