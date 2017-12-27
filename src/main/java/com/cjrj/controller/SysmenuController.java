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
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("/gotomenu.do")
	public String gotoMenu(){
	return "menu";	
	}
	
	/**
	 * 查询所有父级菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getpmenu.do")
	public List<Tree>getTreess(HttpServletRequest req){
		List<SysMenu>root=roleService.getPMenu();
		//父节点树
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
	 * 根据父级菜单查找对应的子菜单
	 * @param root
	 * @return
	 */
	public List<Tree> buildTreess(List<Tree> root){  //child
	  for(int i=0;i<root.size();i++){ 
	     List<SysMenu> children = roleService.getCMenu(root.get(i).getId());
	      //循环子节点
	      List<Tree>clist=new ArrayList<Tree>();
	      for(SysMenu menu:children){
	    	Tree node=new Tree();
	    	
	    	node.setId(menu.getMenu_id());
	    	node.setParentid(menu.getParentid());
	    	node.setText(menu.getMenu_name());
	    	node.setIconCls(menu.getIconCls());
	    	node.setUrl(menu.getUrl());
	    	clist.add(node);
	    	//父与子对应 
	    	root.get(i).setChildren(clist);
	    	}
	    }
	        return root;
	  }
}
