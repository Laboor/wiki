import Mock from 'mockjs'
import orgTree from './data/org-tree';
import orgTree2 from './data/org-tree2';
import login from './data/login';
import apiConfig from '../config/api-config';

// 响应延时配置
Mock.setup({
  timeout: '200-500'
});

// 配置Mock API
const BASE_URL = process.env.VUE_APP_API_ROOT;
Mock.mock(BASE_URL + apiConfig.hrsTree, orgTree);
Mock.mock(BASE_URL + apiConfig.dataPlatformTree, orgTree2);
Mock.mock(BASE_URL + apiConfig.coreTree, orgTree);
Mock.mock(BASE_URL + apiConfig.login, login);

export default Mock;
