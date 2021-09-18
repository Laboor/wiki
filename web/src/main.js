import 'core-js/stable';
import 'regenerator-runtime/runtime';
import 'nprogress/nprogress.css';
import Vue from 'vue';
import App from '@/App.vue';
import router from '@router';
import store from '@store';
import http from '@http';
import VueAxios from 'vue-axios';
import getBrowserInfo from 'get-browser-info';
import api from '@config/api';
import {
	Button,
	Layout,
	Menu,
	Icon,
	Row,
	Col,
	Avatar,
	Input,
	Dropdown,
	Breadcrumb,
	Result,
	Drawer,
	Spin,
	message,
	Empty,
	Popover,
	FormModel,
	Tooltip,
	InputNumber,
	Select,
	Card,
	List,
} from 'ant-design-vue';

// 实际打包时不引入mock
// if (process.env.NODE_ENV !== 'production') require('@/mock');

// 打印浏览器信息
const browserInfo = getBrowserInfo();
if (process.env.NODE_ENV !== 'production') console.log(browserInfo);

// 实例挂载API配置
Vue.prototype.$api = api;

// 事件总线
Vue.prototype.$eventBus = new Vue();

// 挂载实例方法
Vue.prototype.$message = message;

// 生产环境去掉警告提示
Vue.config.productionTip = false;

// 插件注册
Vue.use(VueAxios, http);
Vue.use(Button);
Vue.use(Layout);
Vue.use(Menu);
Vue.use(Icon);
Vue.use(Row);
Vue.use(Col);
Vue.use(Avatar);
Vue.use(Input);
Vue.use(Dropdown);
Vue.use(Breadcrumb);
Vue.use(Result);
Vue.use(Drawer);
Vue.use(Spin);
Vue.use(Empty);
Vue.use(Popover);
Vue.use(FormModel);
Vue.use(Tooltip);
Vue.use(InputNumber);
Vue.use(Select);
Vue.use(Card);
Vue.use(List);

new Vue({
	router,
	store,
	render: (h) => h(App),
}).$mount('#app');
