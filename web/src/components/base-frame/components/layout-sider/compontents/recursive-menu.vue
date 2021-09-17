<template>
	<a-menu theme="dark" mode="inline" :default-selected-keys="['1']">
		<template v-for="item of $store.state.mainMenu">
			<template v-if="!item.meta.isSubMenu">
				<a-menu-item :key="item.name" v-if="!item.meta.hideInMenu">
					<router-link :to="item.path">
						<a-icon :type="item.meta.icon" />
						<span>{{ item.meta.title }}</span>
					</router-link>
				</a-menu-item>
			</template>
			<sub-menu v-else :key="item.name" :menu-item="item" />
		</template>
	</a-menu>
</template>

<script>
import { Menu } from 'ant-design-vue';

const subMenu = {
	name: 'sub-menu',
	isSubMenu: true,
	props: {
		...Menu.SubMenu.props,
		menuItem: Object,
	},
	template: `
    <a-sub-menu :key="menuItem.name" v-if="!menuItem.meta.hideInMenu" v-bind="$props" v-on="$listeners" class="layout-sider-sub-menu">
      <span slot="title">
        <a-icon :type="menuItem.meta.icon" />
        <span>{{ menuItem.meta.title }}</span>
      </span>

      <template v-for="item of menuItem.children">
        <template v-if="!item.meta.isSubMenu" class="menu-item">
          <a-menu-item :key="item.name" v-if="!item.meta.hideInMenu">
            <router-link :to="item.path">
							<a-icon :type="item.meta.icon" />
              <span>{{ item.meta.title }}</span>
            </router-link>
          </a-menu-item>
        </template>
        <sub-menu v-else :key="item.name" :menu-item="item" />
      </template>
    </a-sub-menu>
  `,
};

export default {
	name: 'recursive-menu',
	components: {
		'sub-menu': subMenu,
	},
};
</script>

<!-- 全局样式 -->
<style lang="less">
.layout-sider-sub-menu ul {
	box-shadow: none !important;
}
</style>
