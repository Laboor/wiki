<template>
	<div class="login">
		<a-form-model
			ref="loginForm"
			class="login-form"
			:model="loginForm"
			:rules="rules"
			v-bind="layout"
		>
			<a-form-model-item has-feedback label="账号" prop="account">
				<a-input
					v-model="loginForm.account"
					type="text"
					autocomplete="off"
					placeholder="用户名"
				>
					<a-icon slot="prefix" type="user" class="input-icon" />
				</a-input>
			</a-form-model-item>
			<a-form-model-item has-feedback label="密码" prop="password">
				<a-input
					v-model="loginForm.password"
					type="password"
					autocomplete="off"
					placeholder="密码"
				>
					<a-icon slot="prefix" type="lock" class="input-icon" />
				</a-input>
			</a-form-model-item>
			<a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
				<a-button
					class="login-btn"
					type="primary"
					:loading="isLoggingIn"
					@click="login()"
					>登录</a-button
				>
			</a-form-model-item>
		</a-form-model>
	</div>
</template>

<script>
import cryptoJS from 'crypto-js';

export default {
	name: 'Login',
	data() {
		const validateAccount = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请输入用户名称'));
			} else {
				callback();
			}
		};
		const validatePass = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请输入用户密码'));
			} else if (this.accountValid === false) {
				callback(new Error('用户账号或密码有误'));
				callback();
			} else {
				callback();
			}
		};
		return {
			loginForm: {
				account: '',
				password: '',
			},
			rules: {
				account: [{ validator: validateAccount, trigger: 'change' }],
				password: [{ validator: validatePass, trigger: 'change' }],
			},
			layout: {
				labelCol: { span: 4 },
				wrapperCol: { span: 14 },
			},
			accountValid: null,
			isLoggingIn: false,
		};
	},
	methods: {
		// 密码加密hash函数，与服务端保持一致
		encrypt(str, salt = '', enc = 'Hex') {
			const hash = cryptoJS.SHA256(str + salt);
			if (enc) {
				enc = enc.toLowerCase();
			}
			return hash.toString(cryptoJS.enc[enc]);
		},
		// 登录函数
		login() {
			this.$refs['loginForm'].validate(async (valid) => {
				if (valid) {
					this.isLoggingIn = true;
					try {
						const result = await this.getRandomSalt();
						const userInfo = await this.checkUserInfo(result.randomSalt);
						this.isLoggingIn = false;
						this.accountValid = true;
						this.$store.commit('USER_INFO', userInfo);
						localStorage.setItem('token', userInfo.token);
						// 获取登录前缓存的页面
						const redirectUrl = this.$router.redirectUrl
							? this.$router.redirectUrl
							: 'home';
						this.$router.push(redirectUrl);
					} catch (err) {
						this.isLoggingIn = false;
						if (err.response) {
							console.error('ResponseError', err.response);
							if (err.response.status === 400) {
								this.accountValid = false;
								this.$refs.loginForm.validateField('password');
								this.accountValid = null;
								return;
							}
						} else if (err.request) {
							console.error('RequestError', err.request);
						} else {
							console.error('UnknownError', err);
						}
						this.$message.error(`数据请求失败：${err.message}`);
					}
				} else {
					return false;
				}
			});
		},
		// 获取加密随机盐函数
		getRandomSalt() {
			return this.$http
				.get(this.$api.SALT, {
					params: {
						account: this.loginForm.account,
					},
				})
				.then((res) => {
					return res.data.data;
				});
		},
		// 验证用户信息函数
		checkUserInfo(randomSalt) {
			return this.$http
				.post(this.$api.LOGIN, {
					account: this.loginForm.account,
					password: this.encrypt(
						this.encrypt(this.loginForm.password),
						randomSalt
					),
				})
				.then((res) => {
					return res.data.data;
				});
		},
	},
};
</script>

<style lang="less" scoped>
.login {
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	background-image: url('../assets/img/login-background.svg');
	background-size: 100%;
	background-repeat: no-repeat;
	background-position: center;

	.login-form {
		width: 600px;
		border-radius: 10px;
		padding: 10px;

		.login-btn {
			display: block;
			width: 100%;
		}

		.input-icon {
			color: rgba(0, 0, 0, 0.25);
		}
	}
}
</style>
