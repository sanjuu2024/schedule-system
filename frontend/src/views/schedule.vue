<template>
    <div class="box">
        <el-card>
            <el-button
                :pre-icon="Plus"
                type="primary"
                @click="handleAddSchedule"
            >
                添加日程
            </el-button>
            <el-table
                border
                :data="scheduleList"
                @selection-change=""
                stripe
                max-height="1000"
                row-key="id"
                scrollbar-always-on
            >
                <el-table-column
                    type="index"
                    label="序号"
                    align="center"
                    width="80px"
                ></el-table-column>
                <el-table-column
                    label="进度"
                    align="center"
                    width="130px"
                    prop="completed"
                >
                    <template #="{ row }">
                        <el-checkbox
                            :model-value="row.completed === 1"
                            @change="toggleComplete(row)"
                            style="margin: 5px; vertical-align: -1px"
                        ></el-checkbox>
                        {{ row.completed ? '已' : '未' }}完成
                    </template>
                </el-table-column>
                <el-table-column
                    label="日程内容"
                    align="center"
                    prop="title"
                ></el-table-column>
                <el-table-column label="操作" align="center" width="200px">
                    <template #="{ row }">
                        <el-button
                            type="warning"
                            :icon="Edit"
                            size="small"
                            @click="handleEditSchedule(row)"
                        >
                            编辑
                        </el-button>
                        <!-- 删除按钮套一个气泡确认框 -->
                        <el-popconfirm
                            :title="`确定要删除日程${row.title}吗？`"
                            @confirm="deleteSchedule(row.sid)"
                            width="250px"
                            :icon="Delete"
                            icon-color="red"
                        >
                            <template #reference>
                                <el-button
                                    type="danger"
                                    :icon="Delete"
                                    size="small"
                                >
                                    删除
                                </el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页器部分 -->
            <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="pageSize"
                :page-sizes="[3, 5, 7, 9]"
                :pager-cont="7"
                background
                layout="prev, pager, next, jumper, ->, sizes, total"
                :total="total"
                @size-change="getScheduleList"
                @current-change="getScheduleList"
            />
        </el-card>

        <!-- 修改日程的对话框 -->
        <el-dialog
            v-model="scheduleDialogVisible"
            :title="`${scheduleForm.sid ? '修改' : '添加'}日程`"
            @opened="handleDialogOpened"
        >
            <el-form
                label-width="auto"
                label-position="right"
                :model="scheduleForm"
                :rules="rules"
                ref="theScheduleForm"
            >
                <el-form-item label="日程内容" prop="title">
                    <el-input
                        placeholder="请输入日程内容"
                        v-model.trim="scheduleForm.title"
                        ref="theInput"
                    ></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="scheduleDialogVisible = false">
                    取消
                </el-button>
                <el-button
                    type="primary"
                    @click="confirmScheduleForm"
                    :disabled="!validated"
                >
                    确定
                </el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import {
    reqAddSchedule,
    reqDeleteSchedule,
    reqScheduleList,
    reqUpdateSchedule,
} from '@/api/schedule';
import type { Schedule, ScheduleListResponseData } from '@/api/type';
import { Delete, Edit, Plus } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { nextTick, onMounted, reactive, ref } from 'vue';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();

// 分页器相关
let currentPage = ref<number>(1);
let pageSize = ref<number>(5);
let total = ref<number>(0);

// 控制对话框是否显示
let scheduleDialogVisible = ref<boolean>(false);

// 对话框的确认按钮是否可用
let validated = ref<boolean>(false);

// 日程表单数据
let scheduleForm = reactive<Schedule>({
    sid: 0,
    uid: 0,
    title: '',
    completed: 0,
});

// 日程表单实例对象
let theScheduleForm = ref();

// 输入框实例对象
let theInput = ref();

// 日程表格数据
let scheduleList = ref<Schedule[]>([]);

// 获取日程列表
async function getScheduleList() {
    try {
        let res: ScheduleListResponseData = await reqScheduleList(
            currentPage.value,
            pageSize.value,
        );
        if (res.code == 200) {
            scheduleList.value = res.data.records;
            total.value = res.data.total;
            ElMessage.success('获取日程列表成功！');
        } else {
            console.log(res.message);
        }
    } catch (err) {
        console.log(err);
    }
}

onMounted(() => {
    getScheduleList();
});

// 删除日程
async function deleteSchedule(id: number) {
    try {
        let res = await reqDeleteSchedule(id);
        if (res.code === 200) {
            ElMessage.success('删除日程成功！');
            getScheduleList(); // 刷新列表
        } else {
            console.log(res.message);
        }
    } catch (err) {
        console.log(err);
    }
}

// 对话框打开后的回调（自动聚焦）
function handleDialogOpened() {
    theInput.value?.focus();
}

// 点击添加日程按钮
function handleAddSchedule() {
    scheduleForm.sid = 0;
    scheduleForm.uid = userStore.userInfo.uid || 0;
    scheduleForm.title = '';
    scheduleForm.completed = 0;
    validated.value = false;
    scheduleDialogVisible.value = true;
    nextTick(() => {
        theScheduleForm.value.resetFields();
    });
}

// 点击修改日程按钮
function handleEditSchedule(row: Schedule) {
    scheduleForm.sid = row.sid;
    scheduleForm.uid = userStore.userInfo.uid || 0;
    scheduleForm.title = row.title;
    scheduleForm.completed = row.completed;
    validated.value = true;
    scheduleDialogVisible.value = true;
}

// 添加日程
async function addSchedule() {
    try {
        let res = await reqAddSchedule(scheduleForm);
        if (res.code === 200) {
            ElMessage.success('添加日程成功！');
            getScheduleList(); // 刷新列表
            scheduleDialogVisible.value = false; // 关闭对话框
        }
    } catch (err) {
        console.log(err);
    }
}

// 修改日程
async function updateSchedule(data: Schedule = scheduleForm) {
    try {
        let res = await reqUpdateSchedule(data);
        if (res.code === 200) {
            ElMessage.success('修改日程成功！');
            getScheduleList(); // 刷新列表
            scheduleDialogVisible.value = false; // 关闭对话框
        }
    } catch (err) {
        console.log(err);
    }
}

// 点击对话框的确认按钮
function confirmScheduleForm() {
    if (scheduleForm.sid) {
        updateSchedule();
    } else {
        addSchedule();
    }
}

// 切换日程完成状态
function toggleComplete(row: Schedule) {
    row.completed ^= 1;
    updateSchedule(row);
}

// 表单验证规则
const rules = {
    title: [
        {
            required: true,
            message: '日程内容长度应该在1~100个字符之间',
            trigger: 'change',
            validator: (_rule: any, value: string, callback: any) => {
                if (value.length > 0) {
                    validated.value = true;
                    callback();
                } else if (value.length > 100) {
                    validated.value = false;
                    callback(new Error('日程内容长度应该在1~100个字符之间'));
                } else {
                    validated.value = false;
                    callback(new Error('日程内容不能为空'));
                }
            },
        },
    ],
};
</script>

<style scoped lang="scss">
.box {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding: 20px 0 60px 0;
    padding-top: 80px;
    max-height: 100vh;
}

.el-table {
    margin: 10px 0 20px 0;
}

.el-card {
    width: 80vw;
}

.el-button {
    margin: 10px;
}
</style>
