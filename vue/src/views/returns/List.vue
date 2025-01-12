<template>
  <div style="margin-left: 30px">
    <!--    搜索表单-->
    <div style="margin-bottom: 20px;">
      <h2 style="margin-bottom: 30px"><i class="el-icon-category"></i>病人信息</h2>
      <el-input style="width: 240px" placeholder="请输入医生姓名" v-model="params.doctorName"></el-input>
      <el-input style="width: 240px" placeholder="请输入医生姓名" v-model="params.userName"></el-input>
      <el-input style="width: 240px" placeholder="请输入医生姓名" v-model="params.userId"></el-input>
      <el-button type="primary" style="margin-left: 5px" icon="el-icon-search"  @click="load">搜索</el-button>
      <el-button type="warning" style="margin-left: 5px" icon="el-icon-refresh-right" @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData" stripe row-key="id" default-enpend-all>
      <el-table-column prop="id" label="编号"></el-table-column>
      <el-table-column prop="doctorName" label="医生姓名"></el-table-column>
      <el-table-column prop="userId"  label="病人卡号"></el-table-column>
      <el-table-column prop="userName"  label="病人姓名"></el-table-column>
      <el-table-column prop="userPhone" label="病人联系方式"></el-table-column>
      <el-table-column prop="score" label="所需费用" ></el-table-column>
<!--      <el-table-column prop="account" label="余额" ></el-table-column>-->
      <el-table-column prop="createtime" label="门诊时间"></el-table-column>
      <el-table-column prop="status" label="就诊状态"></el-table-column>


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
  name: 'ReturList',
  data(){
    return {
      // record: Cookies.get('record') ? JSON.parse(Cookies.get('record')) : {},
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
    retur(row){

    },
    
    load(){
      request.get('/record/pageRetur',{
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
        doctorName:'',
        userName:'',
        userId:'',


      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发分页
      this.params.pageNum=pageNum
      this.load()
    },
    del(id){
      request.delete("/record/deleteRetur/" + id).then(res =>{
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


