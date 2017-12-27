package com.cjrj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjrj.model.SysMenu;
import com.cjrj.model.Tree;
import com.cjrj.service.RoleService;

@Controller
public class SysmenuController {

	@Autowired
	RoleService roleService;
	
	/**
	 * ҳ����ת
	 * @return
	 */
	@RequestMapping("/gotomenu.do")
	public String gotoMenu(){
	return "menu";	
	}
	
	/**
	 * ��ѯ���и����˵�
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getpmenu.do")
	public List<Tree>getTreess(HttpServletRequest req){
		List<SysMenu>root=roleService.getPMenu();
		//���ڵ���
        List<Tree>treelist=new ArrayList<Tree>();
		for(SysMenu menu:root){
			Tree node=new Tree();
			node.setState("closed");
			node.setId(menu.getMenu_id());
			node.setParentid(menu.getParentid());
			node.setText(menu.getMenu_name());
			node.setIconCls(menu.getIconCls());
			treelist.add(node);
		}
        return buildTreess(treelist);  
	}
	
	/**
	 * ���ݸ����˵����Ҷ�Ӧ���Ӳ˵�
	 * @param root
	 * @return
	 */
	public List<Tree> buildTreess(List<Tree> root){  //child
	  for(int i=0;i<root.size();i++){ 
	     List<SysMenu> children = roleService.getCMenu(root.get(i).getId());
	      //ѭ���ӽڵ�
	      List<Tree>clist=new ArrayList<Tree>();
	      for(SysMenu menu:children){
	    	Tree node=new Tree();
	    	
	    	node.setId(menu.getMenu_id());
	    	node.setParentid(menu.getParentid());
	    	node.setText(menu.getMenu_name());
	    	node.setIconCls(menu.getIconCls());
	    	node.setUrl(menu.getUrl());
	    	clist.add(node);
	    	//�����Ӷ�Ӧ 
	    	root.get(i).setChildren(clist);
	    	}
	    }
	        return root;
	  }
}
