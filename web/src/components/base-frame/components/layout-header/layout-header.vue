<template>
	<a-layout-header class="layout-header">
		<breadcrumb class="header-breadcrumb"/>
		<content-bar />
	</a-layout-header>
</template>

<script>
import Breadcrumb from './components/breadcrumb/breadcrumb';
import ContentBar from './components/content-bar/content-bar';

export default {
	name: 'layout-header',
	components: {
		Breadcrumb,
		ContentBar,
	},
	data() {
		return {};
	},
};
</script>

<style lang="less" scoped>
.layout-header {
	background: #fff;
	padding: 0;

	.header-breadcrumb {
		padding: 0 24px;
	}
}
</style>
