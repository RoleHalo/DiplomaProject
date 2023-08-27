<template>
	<div class="page">
		<el-row type="flex" justify="center" align="middle" class="container">
			<el-col :lg="14" :xl="10" class="hidden-md-and-down">
				<el-row class="panel">
					<el-col :span="12">
						<div class="left">
							<img src="../assets/login/logo.png" class="logo" />
							<img src="../assets/login/big-1.png" class="big" />
						</div>
					</el-col>
					<el-col :span="12">
						<div class="right">
							<div class="title-container">
								<h2>翼班课辅助教学平台</h2>
								<span>( Ver 1.0 )</span>
							</div>
							<div v-if="!qrCodeVisible">
								<div class="row">
									<el-input v-model="username" placeholder="用户名" prefix-icon="el-icon-user"
										clearable="clearable" />
								</div>
								<div class="row">
									<el-input type="password" v-model="password" placeholder="密码"
										prefix-icon="el-icon-lock" clearable="clearable" />
								</div>
								<div class="row">
									<el-button type="primary" class="btn" @click="login">登录系统</el-button>
								</div>

								<div class="row"><a class="link" @click="faceLogin">人脸识别登录</a></div>
								
								<div v-show="faceBlock">
									<div class="video-box" >
										<video id="video" width="320" height="240" preload autoplay loop muted></video>
										<canvas id="canvas" width="320" height="240"></canvas>
										<canvas id="screenshotCanvas" width="320" height="240"></canvas>
									</div>
									<br />
									<div class="row">
										<el-button type="primary" class="btn" @click="faceLoginBtn">识别登录</el-button>
									</div>
								</div>

							</div>
						
						</div>
					</el-col>
				</el-row>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import 'element-plus/lib/theme-chalk/display.css';
	import router from '../router/index.js';
	import {
		isUsername,
		isPassword
	} from '../utils/validate.js';

	import '../assets/tracking/build/tracking-min.js';
	import '../assets/tracking/build/data/face-min.js';

	export default {

		// mounted() {
		// 	this.faceLogin();
		// },

		data: function() {
			return {
				//人脸识别数据
				video: null,
				screenshotCanvas: null,
				uploadLock: false, // 上传锁
				faceBlock: false,
				faceData:null,

				username: null,
				password: null,
				// qrCodeVisible: false,
				// qrCode: '',
				uuid: null,
				qrCodeTimer: null,
				loginTimer: null
			};
		},

		methods: {
			login: function() {
				let that = this;
				if (!isUsername(that.username)) {
					that.$message({
						message: '用户名格式不正确',
						type: 'error',
						duration: 1200
					});
				} else if (!isPassword(that.password)) {
					that.$message({
						message: '密码格式不正确',
						type: 'error',
						duration: 1200
					});
				} else {
					let data = {
						username: that.username,
						password: that.password
					};
					//发送登陆请求
					that.$http('user/login', 'POST', data, true, function(resp) {
						if (resp.result) {
							//在浏览器的storage中存储用户权限列表，这样其他页面也可使用storage中的数据，实现共享
							let permissions = resp.permissions;
							//取出Token令牌，保存到storage中
							let token = resp.token;
							localStorage.setItem('permissions', permissions);
							localStorage.setItem('token', token);
							//console.log("授权字符串："+token)
							//让路由跳转页面，这里的Home是home.vue页面的名字
							router.push({
								name: 'Home'
							});
							
						} else {
							that.$message({
								message: '登录失败',
								type: 'error',
								duration: 1200
							});
						}
					});
				}
			},
			faceLogin: function() {
				let that = this
				if (that.faceBlock == false) {
					that.faceBlock = true
					// 请求接口成功以后打开锁
					// that.uploadLock = true;
					that.faceLoginInit();
				} else {
					that.faceBlock = false
					
					// that.uploadLock = false;
					location.reload()
				}
			},
			faceLoginBtn: function() {
				let that = this
				that.uploadLock = true;
			},
			// 初始化设置
			faceLoginInit: function() {

				this.video = document.getElementById('video');
				this.screenshotCanvas = document.getElementById('screenshotCanvas');

				let canvas = document.getElementById('canvas');
				let context = canvas.getContext('2d');

				
				// 固定写法
				let tracker = new window.tracking.ObjectTracker('face');
				tracker.setInitialScale(4);
				tracker.setStepSize(2);
				tracker.setEdgesDensity(0.1);
				window.tracking.track('#video', tracker, {
					camera: true
				});

				let _this = this;
				tracker.on('track', function(event) {

					// 检测出人脸 绘画人脸位置
					context.clearRect(0, 0, canvas.width, canvas.height);
					event.data.forEach(function(rect) {
						context.strokeStyle = '#0764B7';
						context.strokeRect(rect.x, rect.y, rect.width, rect.height);

						// 上传图片
						_this.uploadLock && _this.screenshotAndUpload();
					});
				});

			},
		
			// 上传图片
			screenshotAndUpload: function() {
				// 上锁避免重复发送请求
				this.uploadLock = false;

				
				// 绘制当前帧图片转换为base64格式
				let canvas = this.screenshotCanvas;
				let video = this.video;
				let ctx = canvas.getContext('2d');
				ctx.clearRect(0, 0, canvas.width, canvas.height);
				ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
				let base64Img = canvas.toDataURL('image/jpeg');
				// 使用 base64Img 请求接口即可
				let data = {faceData:base64Img}
				console.log('data', data)
				
				//发送登陆请求
				this.$http('user/faceLogin', 'POST', data, false, function(resp) {
					if (resp.result) {
						//在浏览器的storage中存储用户权限列表，这样其他页面也可使用storage中的数据，实现共享
						let permissions = resp.permissions;
						//取出Token令牌，保存到storage中
						let token = resp.token;
						localStorage.setItem('permissions', permissions);
						localStorage.setItem('token', token);
						
						router.push({
							name: 'Home'
						});
					} else {
						that.$message({
							message: '登录失败',
							type: 'error',
							duration: 1200
						});
					}
				});
				// 请求接口成功以后打开锁
				// this.uploadLock = true;
			},
		
		}
	};
</script>

<style lang="less" scoped="scoped">
	@import url('login.less');
</style>
