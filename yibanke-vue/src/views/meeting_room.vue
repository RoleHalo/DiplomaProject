<template>
	<div>
		<el-form :inline="true" :model="dataForm" :rules="dataRule" ref="dataForm">
			<el-form-item prop="name">
				<el-input
					v-model="dataForm.name"
					placeholder="教室名称"
					size="medium"
					class="input"
					clearable="clearable"
				/>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.canDelete" class="input" placeholder="条件" size="medium">
					<el-option label="全部" value="all" />
					<el-option label="可删除" value="true" />
					<el-option label="不可删除" value="false" />
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button size="medium" type="primary" @click="searchHandle()">查询</el-button>
				<el-button
					size="medium"
					type="primary"
					:disabled="!isAuth(['ROOT', 'MEETING_ROOM:INSERT'])"
					@click="addHandle()"
				>
					新增
				</el-button>
				<el-button
					size="medium"
					type="danger"
					:disabled="!isAuth(['ROOT', 'MEETING_ROOM:DELETE'])"
					@click="deleteHandle()"
				>
					批量删除
				</el-button>
			</el-form-item>
		</el-form>
		<!-- 表格控件：Ajax查询出来的数据要显示在表格控件   -->
		<el-table
			:data="dataList"
			border
			v-loading="dataListLoading"
			@selection-change="selectionChangeHandle"
			cell-style="padding: 4px 0"
			style="width: 100%;"
			size="medium"
		>
			<el-table-column type="selection" header-align="center" align="center" width="50" />
			<el-table-column type="index" header-align="center" align="center" width="100" label="序号">
				<template #default="scope">
					<span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
				</template>
			</el-table-column>
			<el-table-column prop="name" header-align="center" align="center" min-width="150" label="教室名称" />
			<el-table-column header-align="center" align="center" min-width="120" label="人数上限">
				<template #default="scope">
					<span>{{ scope.row.max }}人</span>
				</template>
			</el-table-column>
			<el-table-column header-align="center" align="center" min-width="100" label="状态">
				<template #default="scope">
					<span>{{ scope.row.status == 1 ? '可使用' : '已停用' }}</span>
				</template>
			</el-table-column>
			<el-table-column prop="desc" header-align="center" align="center" label="备注" min-width="400" />
			<el-table-column header-align="center" align="center" width="150" label="操作">
				<template #default="scope">
					<el-button
						type="text"
						size="medium"
						:disabled="!isAuth(['ROOT', 'MEETING_ROOM:UPDATE']) || scope.row.id == 0"
						@click="updateHandle(scope.row.id)"
					>
						修改
					</el-button>
					<el-button
						type="text"
						size="medium"
						:disabled="!isAuth(['ROOT', 'MEETING_ROOM:DELETE']) || scope.row.id == 0"
						@click="deleteHandle(scope.row.id)"
					>
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<!-- 分页控件 -->
		<el-pagination
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle"
			:current-page="pageIndex"
			:page-sizes="[10, 20, 50]"
			:page-size="pageSize"
			:total="totalCount"
			layout="total, sizes, prev, pager, next, jumper"
		></el-pagination>
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="loadDataList"></add-or-update>
	</div>
</template>

<script>
import AddOrUpdate from './meeting_room-add-or-update.vue';
export default {
	components: {
		AddOrUpdate
	},
	data: function() {
		return {
			dataForm: {
				name: null,
				canDelete: null
			},
			dataList: [],
			pageIndex: 1,
			pageSize: 10,
			totalCount: 0,
			dataListLoading: false,
			dataListSelections: [],
			addOrUpdateVisible: false,
			dataRule: {
				name: [{ required: false, pattern: '^[a-zA-Z0-9\u4e00-\u9fa5]{1,20}$', message: '教室名称格式错误' }]
			}
		};
	},
	methods: {
		
		selectionChangeHandle: function(val) {
			//复选框完整的数据  ids需要提取
			this.dataListSelections = val;
		},
		sizeChangeHandle: function(val) {
			this.pageSize = val;
			this.pageIndex = 1;
			this.loadDataList();
		},
		currentChangeHandle: function(val) {
			this.pageIndex = val;
			this.loadDataList();
		},
		loadDataList: function() {
		    let that = this;
		    that.dataListLoading = true;
		    let data = {
		        name: that.dataForm.name,
		        canDelete: that.dataForm.canDelete == 'all' ? null : that.dataForm.canDelete,
		        page: that.pageIndex,
		        length: that.pageSize
		    };
		    that.$http('meeting_room/searchMeetingRoomByPage', 'POST', data, true, function(resp) {
		        let page = resp.page;
		        that.dataList = page.list;
		        that.totalCount = page.totalCount;
		        that.dataListLoading = false;
		    });
		},
		// 为分页控件设置翻页的回调函数
		sizeChangeHandle: function(val) {
		    this.pageSize = val;
		    this.pageIndex = 1;
		    this.loadDataList();
		},
		// 为分页控件改变显示记录数量的回调函数。
		currentChangeHandle: function(val) {
		    this.pageIndex = val;
		    this.loadDataList();
		},
		//声明查询按钮点击事件对应的回调函数
		searchHandle: function() {
		    this.$refs['dataForm'].validate(valid => {
		        if (valid) {
		            this.$refs['dataForm'].clearValidate();
		            if (this.dataForm.name == '') {
		                this.dataForm.name = null;
		            }
		            if (this.pageIndex != 1) {
		                this.pageIndex = 1;
		            }
		            this.loadDataList();
		        } else {
		            return false;
		        }
		    });
		},
		//新增按钮回调函数
		addHandle: function() {
		    this.addOrUpdateVisible = true;
		    this.$nextTick(() => {
		        this.$refs.addOrUpdate.init();
		    });
		},
		//修改按钮回调函数
		updateHandle: function(id) {
		    this.addOrUpdateVisible = true;
		    this.$nextTick(() => {
				//修改需要带回id
		        this.$refs.addOrUpdate.init(id);
		    });
		},
		//删除和批量删除同时处理
		deleteHandle: function(id) {
		    let that = this;
		    let ids = id
		        ? [id]
		        : that.dataListSelections.map(item => {
		              return item.id;
		          });
		    if (ids.length == 0) {
		        that.$message({
		            message: '没有选中记录',
		            type: 'warning',
		            duration: 1200
		        });
		    } else {
		        that.$confirm(`确定要删除选中的记录？`, '提示', {
		            confirmButtonText: '确定',
		            cancelButtonText: '取消',
		            type: 'warning'
		        }).then(() => {
		            that.$http('meeting_room/deleteMeetingRoomByIds', 'POST', { ids: ids }, true, function(resp) {
		                if (resp.rows > 0) {
		                    that.$message({
		                        message: '操作成功',
		                        type: 'success',
		                        duration: 1200
		                    });
		                    that.loadDataList();
		                } else {
		                    that.$message({
		                        message: '未能删除记录',
		                        type: 'warning',
		                        duration: 1200
		                    });
		                }
		            });
		        });
		    }
		},



	},//methods结束
	
	created: function() {
	    this.loadDataList();
	}


};
</script>

<style lang="less"></style>
