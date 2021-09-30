<template>
	<a-list
		item-layout="vertical"
		size="large"
		class="book-list"
		:grid="{ gutter: 20, column: 3 }"
		:data-source="ebooks"
	>
		<a-list-item slot="renderItem" key="item.name" slot-scope="item">
			<template v-for="{ type, text } in actions" slot="actions">
				<span :key="type">
					<a-icon :type="type" style="margin-right: 8px" />
					{{ text }}
				</span>
			</template>
			<a-list-item-meta :description="item.description">
				<a slot="title" :href="item.href">{{ item.name }}</a>
				<a-avatar slot="avatar" :src="item.cover" />
			</a-list-item-meta>
		</a-list-item>
	</a-list>
</template> 
<script>
export default {
	name: 'home',
	data() {
		return {
			ebooks: [],
			actions: [
				{ type: 'star-o', text: '156' },
				{ type: 'like-o', text: '156' },
				{ type: 'message', text: '2' },
			],
		};
	},
	created() {
		let that = this;
		this.$http.get(this.$api.EBOOK_LIST).then(res => {
			that.ebooks = res.data.data;
		})
	}
};
</script>
<style lang="less" scoped>
.book-list {
	padding: 42px 24px 50px;
}
</style>>
