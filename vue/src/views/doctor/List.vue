<template>
  <div style="margin-left: 30px">
    <!--    搜索表单-->
    <div style="margin-bottom: 20px;">
      <h2 style="margin-bottom: 30px"><i class="el-icon-category"></i>医生信息</h2>
      <el-input style="width: 240px" placeholder="请输入医生姓名" v-model="params.name"></el-input>
      <el-button type="primary" style="margin-left: 5px" icon="el-icon-search"  @click="load">搜索</el-button>
      <el-button type="warning" style="margin-left: 5px" icon="el-icon-refresh-right" @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData" stripe row-key="id" default-enpend-all>
      <el-table-column prop="id" label="编号"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="description" width="220px" label="描述"></el-table-column>
      <el-table-column prop="publishDate" label="坐诊开始时间"></el-table-column>
      <el-table-column prop="overDate" label="坐诊结束时间" ></el-table-column>
      <el-table-column prop="author" label="费用"></el-table-column>
      <el-table-column prop="category" label="职位"></el-table-column>
      <el-table-column prop="cover" label="诊室"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>


      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="primary" size="mini" @click="$router.push('/editDoctor?id=' + scope.row.id)">编辑<i class="el-icon-edit-outline"></i></el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template v-slot="scope">
          <!--          scope.row就是当前行数据-->
          <el-button-group>
<!--            <el-button type="primary" size="mini" @click="$router.push('/editUser?id=' + scope.row.id)">编辑<i class="el-icon-edit-outline"></i></el-button>-->
            <el-popconfirm
                title="您确定要删除吗？"
                @confirm="del(scope.row.id)"
            >
              <el-button  type="danger"  size="mini" slot="reference">删除<i class="el-icon-delete"></i></el-button>
            </el-popconfirm>

<!--            <el-button  type="warning" @click="handleChangePass">修改密码</el-button>-->
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>



    <!--    分页-->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total">
      </el-pagination>
    </div>


  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: 'DoctorList',
  data(){
    return {
      // doctor: Cookies.get('doctor') ? JSON.parse(Cookies.get('doctor')) : {},
      admin:Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData:[],
      total: 0,
      params:{
        pageNum:1,
        pageSize:10,
        name:'',
      },
      rules:{
        name: [
          {required:true,message:'请输入分类名称',trigger:'blur'}
        ]
      }
    }
  },
  created(){
    this.load()
  },
  methods:{

    
    load(){
      request.get('/doctor/page',{
        params: this.params
      }).then(res =>{
        console.log("qqqqq",res)
        if (res.code === '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset(){
      this.params = {
        pageNum:1,
        pageSize:10,
        name:'',


      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发分页
      this.params.pageNum=pageNum
      this.load()
    },
    del(id){
      request.delete("/doctor/delete/" + id).then(res =>{
        if (res.code === '200'){
          this.$notify.success('删除成功')
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },



  }
}
</script>

<style scoped>

</style>


