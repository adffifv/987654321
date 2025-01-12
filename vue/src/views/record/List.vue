<template>
  <div style="margin-left: 30px">
    <!--    搜索表单-->
    <div style="margin-bottom: 20px;">
      <h2 style="margin-bottom: 30px"><i class="el-icon-category"></i>病人信息</h2>
      <el-input style="width: 240px" placeholder="请输入医生姓名" v-model="params.doctorName"></el-input>
      <el-input style="width: 240px" placeholder="请输入病人姓名" v-model="params.userName"></el-input>
      <el-input style="width: 240px" placeholder="请输入病人卡号" v-model="params.userId"></el-input>
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


      <el-table-column label="完成就诊">
<!--        <template v-slot="scope">-->
<!--          <el-button type="primary" size="mini" @click="retur(scope.row)" v-if="scope.row.status === '等待就诊'">完成<i class="el-icon-edit-outline"></i></el-button>-->
<!--        </template>-->
        <template v-slot="scope">
          <el-button
              type="primary"
              size="mini"
              @click="showPrescriptionDialog(scope.row)"
              v-if="scope.row.status === '等待就诊'"
          >
            完成<i class="el-icon-edit-outline"></i>
          </el-button>
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

          <el-dialog
              title="处方"
              :visible.sync="dialogVisible"
              width="30%"
              @close="handleDialogClose"
          >
            <el-form :model="prescriptionForm">
              <el-form-item label="处方内容">
                <el-input v-model="prescriptionForm.descrip" placeholder="请输入处方内容"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="savePrescription(scope.row)">确定</el-button>
              <!-- 注意：这里的 scope.row 在这里不可用，需要在 showPrescriptionDialog 方法中传递 -->
            </div>
          </el-dialog>

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
  name: 'RecordList',
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
      },
      dialogVisible: false,
      prescriptionForm: {
        descrip: ''
      },
      // 临时存储当前行的数据，以便在弹窗中使用
      currentRow: null
    }
  },
  created(){
    this.load()
  },
  methods:{

    retur(row) {
      request.post("/record/saveRetur",row).then(res =>{
        if (res.code === '200'){
          this.$notify.success('就诊完成')
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },

    load(){
      request.get('/record/page',{
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
      request.delete("/record/delete/" + id).then(res =>{
        if (res.code === '200'){
          this.$notify.success('删除成功')
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
    showPrescriptionDialog(row) {
      // 存储当前行的数据
      this.currentRow = row;
      // 显示弹窗
      this.dialogVisible = true;
    },
    handleDialogClose() {
      // 弹窗关闭时重置表单
      this.prescriptionForm.descrip = '';
      this.currentRow = null;
    },
    savePrescription() {
      // 获取处方内容并附加到当前行的数据中
      const updatedRow = { ...this.currentRow, descrip: this.prescriptionForm.descrip };
      console.log(updatedRow);
      // 发送 POST 请求
      request.post("/record/saveRetur", updatedRow).then(res => {
        if (res.code === '200') {
          this.$notify.success('就诊完成');
          this.load(); // 假设 load 方法用于刷新数据
          // 关闭弹窗并重置表单
          this.dialogVisible = false;
          this.prescriptionForm.descrip = '';
          this.currentRow = null;
        } else {
          this.$notify.error(res.msg);
        }
      });
    },


  }
}
</script>

<style scoped>

</style>


