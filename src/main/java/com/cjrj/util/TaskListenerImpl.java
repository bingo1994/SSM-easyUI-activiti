package com.cjrj.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		// delegateTask.setAssignee("aa");
		delegateTask.addCandidateUser("aa");
		delegateTask.addCandidateUser("hh");
	}

}
