import {
	createRouter,
	createWebHashHistory
}
from 'vue-router'
import Login from '../views/login.vue'
import Main from "../views/main.vue"
import Home from "../views/home.vue"
import Role from "../views/role.vue"
import User from "../views/user.vue"
import Dept from "../views/dept.vue"
import Student from "../views/student.vue"
import Teacher from "../views/teacher.vue"
import MeetingRoom from '../views/meeting_room.vue'
import OfflineMeeting from "../views/offline_meeting.vue"
import OnlineMeeting from "../views/online_meeting.vue"
import MeetingVideo from "../views/meeting_video.vue"
import Approval from "../views/approval.vue"
import Leave from "../views/leave.vue"
import Amect from "../views/amect.vue"
import AmectType from "../views/amect_type.vue"
import AmectReport from "../views/amect_report.vue"
import Reim from "../views/reim.vue"
import NotFound from "../views/404.vue"

//路由数组
const routes = [{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/',
		name: 'Main',
		component: Main,
		children: [
			{
				path: '/home',
				name: 'Home',
				component: Home,
				meta: {
					title: '首页',
				}
			},
			{
				path: "/role",
				name: "Role",
				component: Role,
				meta: {
					title: "角色管理",
					isTab: true
				}
			},
			{
				path: '/user',//user.vue
				name: 'User',
				component: User,
				meta: {
					title: '用户管理',
					isTab: true
				}
			},
			{
				path: '/student',
				name: 'Student',
				component: Student,
				meta: {
					title: '学生管理',
					isTab: true
				}
			},
			{
				path: '/teacher',
				name: 'Teacher',
				component: Teacher,
				meta: {
					title: '老师管理',
					isTab: true
				}
			},
			{
				path: '/dept',
				name: 'Dept',
				component: Dept,
				meta: {
					title: '班级管理',
					isTab: true
				}
			},
			{
				path: '/meeting_room',
				name: 'MeetingRoom',
				component: MeetingRoom,
				meta: {
					title: '教室管理',
					isTab: true
				}
			},
			{
				path: '/offline_meeting',
				name: 'OfflineMeeting',
				component: OfflineMeeting,
				meta: {
					title: '线下课堂',
					isTab: true
				}
			},
			{
				path: '/online_meeting',
				name: 'OnlineMeeting',
				component: OnlineMeeting,
				meta: {
					title: '线上课堂',
					isTab: true
				}
			},
			{
				path: '/meeting_video/:meetingId/:uuid',
				name: 'MeetingVideo',
				component: MeetingVideo,
				meta: {
					title: '视频会议',
					isTab: true
				}
			}, {
				path: '/approval',
				name: 'Approval',
				component: Approval,
				meta: {
					title: '审批任务',
					isTab: true
				}
			},
			{
				path: '/leave',
				name: 'Leave',
				component: Leave,
				meta: {
					title: '用户请假',
					isTab: true
				}
			},
			{
				path: '/amect',
				name: 'Amect',
				component: Amect,
				meta: {
					title: '缴费管理',
					isTab: true
				}
			},
			{
				path: '/amect_type',
				name: 'AmectType',
				component: AmectType,
				meta: {
					title: '缴费类型',
					isTab: true
				}
			},
			{
				path: '/amect_report',
				name: 'AmectReport',
				component: AmectReport,
				meta: {
					title: '缴费报告',
					isTab: true
				}
			},
			{
				path: '/reim',
				name: 'Reim',
				component: Reim,
				meta: {
					title: '修改头像',
					isTab: true
				}
			}
		]
	},
	{
		path: "/404",
		name: "NotFound",
		component: NotFound
	},
	{
		path: '/:pathMatch(.*)*',
		redirect: "/404"
	}
]

const router = createRouter({
	history: createWebHashHistory(),
	routes
})
router.beforeEach((to, from, next) => {
	if (to.name != "Login") {//必须先登录
		let permissions = localStorage.getItem("permissions")
		let token = localStorage.getItem("token")
		if (permissions == null || permissions == ""||token == null || token == "") {
			next({
				name: 'Login'
			})
		}
	}
	return next()
})

export default router
