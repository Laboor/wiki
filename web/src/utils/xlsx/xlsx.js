import XLSX from 'xlsx';

// 导入Excel
function importExcel(file) {
	return new Promise((resolve, reject) => {
		if (!file) {
			reject(new Error('文件不存在！'));
		}
		let reader = new FileReader();
		reader.onload = (e) => {
			let data = new Uint8Array(e.target.result);
			let result = [];
			let wb = XLSX.read(data, { type: 'array' });
			console.log(wb);
			for (let sheet of wb.SheetNames) {
				let ws = wb.Sheets[sheet];
				result.push({
					sheetName: sheet,
					sheetData: XLSX.utils.sheet_to_json(ws),
				});
			}
			resolve(result);
		};
		reader.onerror = (err) => {
			reject(err);
		};
		reader.readAsArrayBuffer(file);
	});
}

// 导出Excel
function exportExcel(data, fileName) {
	return new Promise((resolve, reject) => {
		try {
			const wb = XLSX.utils.book_new();
			for (let sheet of data) {
				let ws = XLSX.utils.aoa_to_sheet(sheet.sheetData);
				for (let entry of Object.entries(sheet.sheetStyle)) {
					ws[entry[0]] = entry[1];
				}
				XLSX.utils.book_append_sheet(wb, ws, sheet.sheetName);
			}
			XLSX.writeFile(wb, fileName);
			resolve(true);
		} catch (err) {
			reject(err);
		}
	});
}

export default {
	importExcel: importExcel,
	exportExcel: exportExcel,
};
