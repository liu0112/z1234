package com.towery.beans;

/**
 * 
 * @author 
 *
 */
public class MediaData {

	private String taskid;
	
	private String questionid;
	
	private String filename;
	
	private String filepath;
	
	private String taskname;

	public MediaData(String taskid, String questionid, String filename,
			String filepath,String taskname) {
		super();
		this.taskid = taskid;
		this.questionid = questionid;
		this.filename = filename;
		this.filepath = taskname;
		this.taskname = taskname;
	}

	public MediaData() {
		super();
	}


	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	@Override
	public String toString() {
		return "MediaData [taskid=" + taskid + ", questionid=" + questionid
				+ ", filename=" + filename + ", filepath=" + filepath
				+ ", taskname=" + taskname + "]";
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	
}
