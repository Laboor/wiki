import Mock from 'mockjs'
import login from './data/login';
import menu from './data/menu';
import api from '@config/api-config';

// 响应延时配置
Mock.setup({
  timeout: '200-500'
});

// 配置Mock API
const BASE_URL = process.env.VUE_APP_API_ROOT;
Mock.mock(BASE_URL + api.LOGIN, login);
Mock.mock(BASE_URL + api.MENU, menu);

export default Mock;
