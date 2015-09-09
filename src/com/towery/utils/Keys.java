package com.towery.utils;

import android.R.string;
import android.os.Environment;

public class Keys {

	public static final String SDURL = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/";// SD卡路径
	public static final String SP = "SP";// SharedPreferences名称
	public static final String SP_TASKNAME = "sp_taskname";// 任务键名
	public static final String SP_USERNAME = "sp_username";// 用户名
	public static final String SP_TASKTYPE = "sp_tasktype";// 任务类型键名
	public static final String SP_TASKID = "sp_taskid";// 任务编号
	public static final String SP_QUESTIONID = "sp_questionid";//地址ID

	public static final String ZD_DWRWQ = "定位任务区";
	public static final String ZD_CJ = "采集";
	public static final String ZD_BJ = "编辑";
	public static final String ZD_RWLB = "任务列表";
	public static final String ZD_WC = "完成";

	public static final String BD_POILB = "poi列表";
	public static final String BD_DWRWQ = "定位任务区";
	public static final String BD_CJ = "采集";
	public static final String BD_BDDZ = "被动地址";
	public static final String BD_BJ = "编辑";
	public static final String BD_RWLB = "任务列表";
	public static final String BD_WC = "完成";

	public static final String FQ_POILB = "poi列表";
	public static final String FQ_DWRWQ = "定位任务区";
	public static final String FQ_CJ = "采集";
	public static final String FQ_FQDZ = "废弃地址";
	public static final String FQ_BJ = "编辑";
	public static final String FQ_RWLB = "任务列表";
	public static final String FQ_WC = "完成";

	public static final String ZJ_POILB = "poi列表";
	public static final String ZJ_DWRWQ = "定位任务区";
	public static final String ZJ_CJ = "采集";
	public static final String ZJ_ZJDZ = "质检地址";
	public static final String ZJ_BJ = "编辑";
	public static final String ZJ_RWLB = "任务列表";
	public static final String ZJ_WC = "完成";
	
	public static final String BJ_BJ="编辑";
	public static final String BJ_DW="定位";
	public static final String BJ_SC="删除";
	
	public static final String DZ_HS="核实";
	public static final String DZ_DW="定位";
	

}
