import axios from 'axios'
import { Notification, MessageBox, Message } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'
import Config from '@/config'

// 创建axios实例
const instance = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? process.env.BASE_API : '/', // api 的 base_url
  timeout: Config.timeout // 请求超时时间
})

// 请求成功响应码
const ok = '200'

// request拦截器
instance.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    config.headers['Content-Type'] = 'application/json'
    return config
  },
  error => {
    // Do something with request error
    Message.error('对不起，出错了')
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
instance.interceptors.response.use(
  response => {
    // debugger
    const res = response.data
    const code = res.code
    if (code !== undefined) {
      // debugger
      // 401:Token 过期了;
      if (code === 401 || code === 10004 || code === 100005) {
        MessageBox.confirm(
          '登录状态已过期，您可以继续留在该页面，或者重新登录',
          '系统提示',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('LogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      } else if (code !== ok) {
        const errorMsg = response.data.message
        if (errorMsg !== undefined) {
          Notification.error({
            title: errorMsg,
            duration: 3000
          })
        }
      }
      return response
    } else {
      return response
    }
  },
  error => {
    if (error.toString().indexOf('Error: timeout') !== -1) {
      Notification.error({
        title: '网络请求超时',
        duration: 2500
      })
      return Promise.reject(error)
    }
    if (error.toString().indexOf('Error: Network Error') !== -1) {
      Notification.error({
        title: '网络请求错误',
        duration: 2500
      })
      return Promise.reject(error)
    }
    return Promise.reject(error)
  }
)

// restful请求
export const createAPI = (url, method, data) => {
  const config = {}
  if (method === 'get') {
    config.params = data
  } else {
    config.data = data
  }
  return instance({
    url,
    method,
    ...config
  })
}

// form表单请求
export const createFormAPI = (url, method, data) => {
  const config = {}
  config.data = data
  config.headers = {
    'Cache-Control': 'no-cache',
    'Content-Type': 'application/x-www-form-urlencoded'
  }
  config.responseType = 'json'
  config.transformRequest = [
    function(data) {
      let ret = ''
      for (const it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }
  ]
  return instance({
    url,
    method,
    ...config
  })
}

// 图片上传请求
export const createImgAPI = (url, method, data) => {
  const config = {}
  config.data = data
  config.headers = {
    'Cache-Control': 'no-cache',
    'Content-Type': 'application/x-www-form-urlencoded'
  }
  config.responseType = 'blob'
  config.transformRequest = [
    function(data) {
      let ret = ''
      for (const it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }
  ]
  return instance({
    url,
    method,
    ...config
  })
}
// 数据导出
export const createFileAPI = (url, method, data) => {
  const config = {}
  config.data = data
  config.headers = {
    'Cache-Control': 'no-cache',
    'Content-Type': 'application/x-www-form-urlencoded'
  }
  config.responseType = 'arraybuffer'
  config.transformRequest = [
    function(data) {
      let ret = ''
      for (const it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }
  ]
  return instance({
    url,
    method,
    ...config
  })
}
// 员工导出
export const createDown = (url, method, data) => {
  const config = {}
  if (method === 'get') {
    config.params = data
  } else {
    config.data = data
  }
  config.headers = {
    'Cache-Control': 'no-cache',
    'Content-Type': 'application/x-www-form-urlencoded'
  }
  config.responseType = 'blob'
  return instance({
    url,
    method,
    ...config
  })
}

