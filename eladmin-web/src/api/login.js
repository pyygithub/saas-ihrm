import { createAPI } from '@/utils/request'

export const login = data => createAPI('auth/login', 'post', data)
export const getInfo = data => createAPI('auth/profile', 'get', data)
export const getCodeImg = data => createAPI('auth/code', 'get', data)
export const logout = data => createAPI('auth/logout', 'delete', data)
