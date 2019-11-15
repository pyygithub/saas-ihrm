<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <el-tabs v-model="activeName">
          <el-tab-pane label="企业信息" name="first">
            <!--form表单
                model ： 双向绑定的数据对象
            -->
            <el-form ref="form" :model="formData" label-width="200px">
              <el-form-item class="formInfo" label="公司名称：">
                <el-input v-model="formData.name" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="公司地区：">
                <el-input v-model="formData.companyArea" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="公司地址：">
                <el-input v-model="formData.companyAddress" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="审核状态：">
                <el-input v-model="formData.auditState" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="营业执照：">
                <!--  <span v-for="item in fileList" :key='item.id' class="fileImg">-->
                <!--                   <img :src="item.url">-->
                <!--                 </span>-->
              </el-form-item>
              <el-form-item class="formInfo" label="法人代表：">
                <el-input v-model="formData.legalRepresentative" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="公司电话：">
                <el-input v-model="formData.companyPhone" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="邮箱：">
                <el-input v-model="formData.mailbox" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="公司规模：">
                <el-input v-model="formData.companySize" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="所属行业：">
                <el-input v-model="formData.industry" class="inputW" disabled></el-input>
              </el-form-item>
              <el-form-item class="formInfo" label="备注：">
                <el-input type="textarea" v-model="formData.remarks" class="inputW"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleSub('1')">审核</el-button>
                <el-button @click="handleSub('2')">拒绝</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="账户信息" name="second">账户信息</el-tab-pane>
          <el-tab-pane label="交易记录" name="third">交易记录</el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<script>
  import {detail} from '@/api/company'
  export default {
    name: 'company-detail',
    data () {
      return {
        activeName: 'first',
        formData: {}
      }
    },
    watch: {
      $route: {
        handler(newval, old) {
          var id = newval.params.id
          this.detail(id);
        },
        immediate: true,
        deep: true
      }
    },
    methods: {
      detail(id) {
        detail({id: id}).then(res => {
          this.formData = res.data.data
          console.log(id)
          console.log(this.formData)
        })
      }
    },
    // 创建完毕状态
    created() {
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .alert {
    margin: 10px 0px 0px 0px;
  }

  .pagination {
    margin-top: 10px;
    text-align: right;
  }
</style>
