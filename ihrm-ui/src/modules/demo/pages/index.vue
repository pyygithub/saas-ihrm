<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <el-table :data="dataList" fit highlight-current-row style="width: 100%" border>
          <el-table-column align="center" :label="$t('table.operationType')">
            <template slot-scope="scope">
              <span>{{scope.row.type}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" :label="$t('table.operator')">
            <template slot-scope="scope">
              <span>{{scope.row.author}}</span>
            </template>
          </el-table-column>
          <el-table-column :label="$t('table.results')">
            <template slot-scope="scope">
              <span>{{scope.row.title}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" :label="$t('table.operationDate')">
            <template slot-scope="scope">
              <span>{{scope.row.display_time}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" :label="$t('table.describe')">
            <template slot-scope="scope">
              <span>{{scope.row.forecast}}</span>
            </template>
          </el-table-column>
        </el-table>
        demo页面
      </el-card>
    </div>
  </div>
</template>

<script>
    import {list} from '@/api/example/table'

    export default {
        name: 'saas-clients-table-index',
        // 数据模型（双向绑定）
        data() {
            return {
                dataList: []
            }
        },
        methods: {
            // 获取列表数据
            getList() {
                list().then(res => {
                    this.dataList = res.data.items
                    console.log(this.dataList)
                })
            }
        },
        // 在模板渲染成html前调用，通常初始化某些属性，然后再渲染成视图
        created() {
            //console.log("created方法执行")
            this.getList()
        },
        // 在模板渲染成html后调用，通常是初始化页面完成后，再对html页的dom节点进行一些操作
        mounted() {
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
