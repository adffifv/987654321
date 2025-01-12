 <template>
  <div style="width: 80%;margin-left:30px">
    <h2 style="margin-bottom: 30px"><i class="el-icon-edit"></i>新增挂号</h2>
    <el-form :inline="true" :model="form" style="" label-width="120px">
      <el-form-item label="科室">
        <el-cascader

            :props="{value:'name',label:'name'}"
            v-model="form.categories"
            :options="categories"
            @change="test"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="医生姓名" prop="doctorName">
        <el-select v-model="form.doctorName" filterable placeholder="请选择" @change="doctorId">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"

        ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="医生id" prop="username">
        <el-input v-model="form.doctorId" disabled ></el-input>
      </el-form-item>
      <el-form-item label="所需费用" prop="username">
        <el-input v-model="form.score" disabled ></el-input>
      </el-form-item>
      <el-form-item label="病人卡号" prop="userId">
        <el-select v-model="form.userId" filterable placeholder="请选择" @change="selUser">
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.username"

          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="病人姓名" prop="username">
        <el-input v-model="form.userName" disabled placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="病人联系方式" prop="userPhone">
        <el-input v-model="form.userPhone" disabled placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="余额" prop="username">
        <el-input v-model="form.account" disabled ></el-input>
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
  name: 'AddRecord',
  data(){
    return {
      form: {},
      categories:[],
      users:[],
      doctors:[],
      options:[],
      value: null
    }
    },
  created(){
    request.get('/category/tree').then(res =>{
      this.categories = res.data
    })
    request.get('/user/list').then(res =>{
      this.users = res.data
    })

  },
    methods: {

      save(){
        console.log(this.form)
        request.post('/record/save',this.form).then(res =>{
          if (res.code === '200'){
            this.$notify.success('新增成功')
            this.form={}
          }else {
            this.$notify.error(res.msg)
          }
        })
      },
      test(Values) {
        // selectedValues 是一个数组，包含用户在每一级中选择的值
        console.log(Values);

        let result = ''
        Values.forEach(item => {
          result += item.toString() + '>'
        })

        result = result.substring(0,result.length-1)
        console.log(result);
        request.get('/doctor/doctor/'+ result).then(res =>{
          console.log(res.data);
          this.doctors= res.data
          let formattedOptions = res.data.map(doctor => ({
            label: doctor.name, // 显示在 el-select 下拉菜单中的文本
            value: doctor.id    // 选中后绑定到 v-model 的值
          }));

          this.options = formattedOptions;
        }).catch(error => {
          console.error('获取医生列表失败:', error);
        })
      },
      doctorId(newValue){
        this.form.doctorId=newValue
        const doctor = this.doctors.find(v =>v.id === this.form.doctorId)
        this.form.score = doctor.author


      },
      selUser(){
        const user = this.users.find(v =>v.username === this.form.userId)
        request.get('/user/'+user.id).then(res=>{
          this.$set(this.form,'username',res.data.name)
          this.form.userName = res.data.name
          this.form.userPhone = res.data.phone
          this.form.account = res.data.account
        })


      }
    }
  }


</script>