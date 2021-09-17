let apiConfig = {};

for (let config in process.env) {
  if (config.includes('VUE_APP_API_')) {
    let apiName = config.slice(12);
    apiConfig[apiName] = process.env[config];
  }
}

export default apiConfig;
