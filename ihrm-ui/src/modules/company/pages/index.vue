<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <!--elementui的table组件  data：数据模型 -->
        <el-table :data="dataList" border style="width: 100%">
          <!--el-table-column : 构造表格中的每一列 prop： 数组中每个元素对象的属性名
          -->
          <el-table-column type="index" label="序号" width="50"></el-table-column>
          <el-table-column prop="name" label="企业名称" width="200"></el-table-column>
          <el-table-column prop="industry" label="所属行业" width="150"></el-table-column>
          <el-table-column prop="mailbox" label="邮箱" width="150"></el-table-column>
          <el-table-column prop="companyArea" label="所在地区" width="150"></el-table-column>
          <el-table-column prop="createTime" label="申请时间" width="150" :formatter="dateFormat"></el-table-column>
          <el-table-column prop="expirationDate" label="到期时间" width="150" :formatter="dateFormat"></el-table-column>
          <el-table-column prop="version" label="版本" width="150"></el-table-column>
          <el-table-column prop="companyPhone" label="联系电话" width="150"></el-table-column>
          <el-table-column prop="state" label="状态" width="150">
            <!--scope:传递当前行的所有数据 -->
            <template slot-scope="scope">
              <!--开关组件
                  active-value：激活的数据值
                  active-color：激活的颜色
                  inactive-value：未激活
                  inactive-color：未激活的颜色
               -->
              <el-switch
                v-model="scope.row.state"
                inactive-value="0"
                active-value="1"
                disabled
                active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <router-link :to="'/company/details/'+scope.row.id"><el-button type="text">详情</el-button></router-link>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
  import {list} from '@/api/company'
  import moment from 'moment'
  export default {
    name: 'company-index',
    data () {
      return {
        dataList: []
      }
    },
    methods: {
      // 查询所有企业列表
      getList() {
        list().then(res => {
          console.log(res)
          this.dataList = res.data.data
        })
      },
      // 时间戳格式化
      dateFormat(row, column) {
        let date = row[column.property];
        if (date == undefined) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      }
    },
    // 创建完毕状态
    created() {
      this.getList()
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
