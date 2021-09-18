<template>
	<a-list
		item-layout="vertical"
		size="large"
		class="book-list"
		:pagination="pagination"
		:data-source="listData"
	>
		<a-list-item slot="renderItem" key="item.title" slot-scope="item">
			<template v-for="{ type, text } in actions" slot="actions">
				<span :key="type">
					<a-icon :type="type" style="margin-right: 8px" />
					{{ text }}
				</span>
			</template>
			<a-list-item-meta :description="item.description">
				<a slot="title" :href="item.href">{{ item.title }}</a>
				<a-avatar slot="avatar" :src="item.avatar" />
			</a-list-item-meta>
			{{ item.content }}
		</a-list-item>
	</a-list>
</template>
<script>
const listData = [];
for (let i = 0; i < 1; i++) {
	listData.push({
		href: 'https://www.antdv.com/',
		title: `ant design vue part ${i}`,
		avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
		description:
			'Ant Design, a design language for background applications, is refined by Ant UED Team.',
		content:
			'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
	});
}

export default {
	name: 'home',
	data() {
		return {
			listData,
			pagination: {
				onChange: (page) => {
					console.log(page);
				},
				pageSize: 3,
			},
			actions: [
				{ type: 'star-o', text: '156' },
				{ type: 'like-o', text: '156' },
				{ type: 'message', text: '2' },
			],
		};
	},
};
</script>
<style lang="less" scoped>
.book-list {
	padding: 42px 24px 50px;
}
</style>>
