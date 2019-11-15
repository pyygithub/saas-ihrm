import {createAPI, createFormAPI} from '@/utils/request'

export const list = data => createAPI('/companys', 'get', data)
export const detail = data => createAPI(`/company/${data.id}`, 'get', data)
