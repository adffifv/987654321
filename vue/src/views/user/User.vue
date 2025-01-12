<template>
  <div style="margin-left: 30px">
    <!--    搜索表单-->
    <div style="margin-bottom: 20px;">
      <h2 style="margin-bottom: 30px"><i class="el-icon-user"></i>已建卡账户</h2>
      <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
      <el-input style="width: 240px;margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-button type="primary" style="margin-left: 5px" icon="el-icon-search"  @click="load">搜索</el-button>
      <el-button type="warning" style="margin-left: 5px" icon="el-icon-refresh-right" @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号"></el-table-column>
      <el-table-column prop="username" label="卡号"></el-table-column>
      <el-table-column prop="name" label="名字"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="account" label="余额"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="操作">
        <template v-slot="scope">
          <!--          scope.row就是当前行数据-->

          <el-button type="warning" size="mini" @click="handleAccount(scope.row)">缴费<i class="el-icon-circle-plus-outline"></i></el-button>

          <!--            <el-button  size="mini">删除</el-button>-->

        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
<!--          scope.row就是当前行数据-->

          <el-button type="primary" size="mini" @click="$router.push('/editUser?id=' + scope.row.id)">编辑<i class="el-icon-edit-outline"></i></el-button>

<!--            <el-button  size="mini">删除</el-button>-->

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
            <!--            <el-button  size="mini">删除</el-button>-->
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
    <el-dialog title="缴费" :visible.sync="dialogFormVisible" width="300px">
      <el-form :model="form" label-width="100px" :rules="rules" >
        <el-form-item label="当前余额" >
          <el-input v-model="form.account" disabled autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="缴入费用" >
          <el-input v-model="form.score" autocomplete="off" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>



</template>

<script>
import request from "@/utils/request";

export default {
  name: 'User',
  data(){
    const checkNums = (rule,value,callback) =>{
      value=parseInt(value)
      if (value < 0||value>200){
        callback(new Error('请输入大于0小于等于200的整数'));
      }
      callback();
    }
    return {
      tableData:[],
      total: 0,
      params:{
        pageNum:1,
        pageSize:10,
        name:'',
        phone:''
      },
      dialogFormVisible:false,
      form:{},
      rules:{
        score:[
          {required:true,message:'请输入费用',trigger:'blur'},
          {validator:checkNums,trigger:'blur'}
        ]
      }
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      //   fetch( 'http://localhost:9090/user/list').then(res => res.json()).then(res=>{
      //     console.log(res)
      //     this.tableData=res.date
      //   })
      //
      request.get('/user/page',{
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
        phone:''
      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发分页
      this.params.pageNum=pageNum
      this.load()
    },
    del(id){
      request.delete("/user/delete/" + id).then(res =>{
        if (res.code === '200'){
          this.$notify.success('删除成功')
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleAccount(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true
    },
    save(){

      request.post('/user/account',this.form).then(res =>{
        if (res.code === '200'){
          this.$notify.success('缴费成功')
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    }


  }
}
</script>

<style scoped>

</style>