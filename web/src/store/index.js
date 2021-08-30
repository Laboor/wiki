import Vue from 'vue';
import Vuex from 'vuex';
import {
	TRIGGER_COLLAPSED,
	CHANGE_ORGTREE_LEVEL,
	ORGTREE_MAX_LEVEL,
	USER_INFO
} from './mutation-types';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		siderCollapsed: false,
		orgTreeLevel: 0,
		orgTreeMaxLevel: 5,
		userInfo: {
			id: '',
			name: '',
			authorityLevel: 0,
			token: '',
		},
	},
	mutations: {
		[TRIGGER_COLLAPSED](state, val) {
			if (val != null) {
				state.siderCollapsed = val;
			} else {
				state.siderCollapsed = !state.siderCollapsed;
			}
		},
		[CHANGE_ORGTREE_LEVEL](state, level) {
			state.orgTreeLevel = level;
		},
		[ORGTREE_MAX_LEVEL](state, level) {
			state.orgTreeMaxLevel = level;
		},
		[USER_INFO](state, userInfo) {
			Object.assign(state.userInfo, userInfo);
		},
	},
	actions: {},
	modules: {},
});
