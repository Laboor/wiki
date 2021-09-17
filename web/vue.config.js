const resolve = require('path').resolve;

module.exports = {
	publicPath: '/',
	outputDir: 'dist',
	assetsDir: '',
	indexPath: 'index.html',
	filenameHashing: true,
	pages: {
		index: {
			entry: 'src/main.js',
			template: 'public/index.html',
			filename: 'index.html',
			title: process.env.VUE_APP_BASE_TITLT,
			favicon: resolve('public/favicon.ico'),
			chunks: ['chunk-vendors', 'chunk-common', 'index'],
		},
	},
	productionSourceMap: false,
	runtimeCompiler: true, // 支持template编译
	chainWebpack: config => {
		config.resolve.alias
			.set('@', resolve('src'))
			.set('@assets', resolve('src/assets'))
      .set('@components', resolve('src/components'))
      .set('@views', resolve('src/views'))
      .set('@store', resolve('src/store'))
      .set('@utils', resolve('src/utils'))
      .set('@config', resolve('src/config'))
      .set('@router', resolve('src/router'))
      .set('@http', resolve('src/http'))
	},
	devServer: {
		open: false,
		host: '0.0.0.0',
		port: 8080,
		https: false,
		hotOnly: false,
		proxy: null,
		compress: true,
	},
	pwa: {
		iconPaths: {
			favicon32: 'favicon.ico',
			favicon16: 'favicon.ico',
			appleTouchIcon: 'favicon.ico',
			maskIcon: 'favicon.ico',
			msTileImage: 'favicon.ico',
		},
	},
	css: {
		loaderOptions: {
			// 向 CSS 相关的 loader 传递选项
			less: {
				javascriptEnabled: true,
				//定制antd主题
				modifyVars: {
					// 'layout-sider-background': 'red',
				},
			},
		},
	},
};
