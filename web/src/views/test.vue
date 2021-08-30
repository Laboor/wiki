<template>
	<div>
		<input
			type="file"
			multiple="false"
			id="sheetjs-input"
			accept="' + SheetJSFT + '"
			@change="onchange"
		/>
		<button type="button" id="export-table" @click="onexport">
			Export to XLSX
		</button>
	</div>
</template>

<script>
import xlsx from '../components/xlsx/xlsx';
// var SheetJSFT = [
// 	"xlsx", "xlsb", "xlsm", "xls", "xml", "csv", "txt", "ods", "fods", "uos", "sylk", "dif", "dbf", "prn", "qpw", "123", "wb*", "wq*", "html", "htm"
// ].map(function(x) { return "." + x; }).join(",");

export default {
	name: 'test',
	data() {
		return {
			sheet: {},
		};
	},
	methods: {
		onchange(evt) {
			var file;
			var files = evt.target.files;

			if (!files || files.length == 0) return;

			file = files[0];
			console.time('test');
			xlsx.importExcel(file).then((res) => {
				console.log(res);
				console.timeEnd('test');
				this.sheet = res;
			});
		},
		onexport() {
			let obj = [
				{
					sheetName: '三年一班',
					sheetData: [
						['姓名', '年级', '部门'],
						['王大', '1', '开发部'],
						['张三', '2', '运维部'],
					],
					sheetStyle: {
						'!cols': [{ wpx: 100 }, { wpx: 100 }, { wpx: 100 }],
						'!merges': [
							{
								s: {
									r: 0,
									c: 0,
								},
								e: {
									r: 0,
									c: 1,
								},
							},
						],
					},
				},
				{
					sheetName: '三年二班',
					sheetData: [
						['姓名', '年级', '部门'],
						['王大', '1', '开发部'],
						['张三', '2', '运维部'],
					],
					sheetStyle: {},
				},
			];
			xlsx.exportExcel(obj, '部门人员.xlsx');
		},
	},
};
</script>

<style lang="less" scoped>
.btn {
	margin: 100px;
}
</style>
