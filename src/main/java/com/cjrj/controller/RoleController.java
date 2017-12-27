package com.cjrj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;
import com.cjrj.model.Tree;
import com.cjrj.model.User;
import com.cjrj.service.RoleService;
import com.cjrj.util.Const;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	/**
	 * ��ѯ��ɫ��
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getrolelist.do")
	public Map<String,Object> getRoleList(@RequestParam(value="page",defaultValue="1",required=false)Integer page,Integer rows){
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("stime", (page-1)*rows);
		map.put("etime", page*rows);
		//���н�ɫ����Ϣ
		List<Role>rolelist=roleService.getRoleList(map);
		
		for(Role r:rolelist){
			//��ɫ��Ӧ�ĸ����˵�
			List<SysMenu>menulist=roleService.getMenuByRoleId(r.getRole_id());
			List<String> menuName=new ArrayList<String>();
			for(SysMenu s:menulist){
				menuName.add(s.getMenu_name());
			}
			r.setMenuName(menuName);
		}
		int count=roleService.getRoleCount();
		Map<String,Object>maps=new HashMap<String, Object>();
		maps.put("rows", rolelist);
		maps.put("total", count);
		return maps;
	}
	
	/**
	 * ���Ҳ˵�
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTree.do")
	public List<Tree>getTree(HttpServletRequest req){
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
        return buildTree(treelist);  
	}
	
	/**
	 * ���ݸ����˵����Ҷ�Ӧ���Ӳ˵�
	 * @param root
	 * @return
	 */
	public List<Tree> buildTree(List<Tree> root){  //child
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
	
	/**
	 * ��֤��ӵĽ�ɫ�Ƿ����
	 * @param role_name
	 * @return
	 */
	@RequestMapping("/chkrole.do")
	@ResponseBody
	public boolean chkRole(String role_name){
		boolean flag=true;
		Role role=roleService.getRoleByName(role_name);
		if(role!=null){
			flag=false;
		}
		return flag;
	}
	
	/**
	 * ��ӽ�ɫ
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addrole.do")
	public int addRole(Role role){
		roleService.addRole(role);
		Integer[] menu_id=role.getMenu_ids();
		List<Role>rlist=roleService.getRoleByAdd();
		int sum=0;
		//������ӵĽ�ɫid�Լ��˵�idѭ����ӽ�ɫ-�˵���
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("role_id", rlist.get(0).getRole_id());
		for(Integer m:menu_id){
			map.put("menu_id", m);
			int i=roleService.addRole_Menu(map);
			sum=sum+i;
		}
		int x=0;
		if(sum==menu_id.length){
			x=1;
		}
		return x;
	}
	
	/**
	 * �޸Ľ�ɫ
	 * @param role
	 * @return
	 */
	@RequestMapping("/updaterole.do")
	@ResponseBody
	public int updateRole(Role role){
		int sum=0;
		int x=0;
		Integer[] menu_id=role.getMenu_ids();
		int z=roleService.updateRole(role);
		if(z>0){
		//ɾ����ɫ�˵��м��
		roleService.delRoleMenuByRole_id(role.getRole_id());
		//ѭ����ӽ�ɫ-�˵���
		Map<String,Object>map=new HashMap<String, Object>();
			map.put("role_id",role.getRole_id());
				if(menu_id!=null||menu_id.length>0){
					for(Integer m:menu_id){
						map.put("menu_id", m);
						int i=roleService.addRole_Menu(map);
						sum=sum+i;
					}
				}
				if(sum==menu_id.length){
					x=1;
				}
		}
		return x;
	}
	
	
	
	/**
	 * ɾ����ɫ
	 * @param role_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delrole.do")
	public int delRole(Integer role_id){
		int x=roleService.delRoleById(role_id);
		if(x>0){
			//ɾ����ɫ�˵�
			 roleService.delRoleMenuByRole_id(role_id);
			 //ɾ���û���ɫ��
			 roleService.delUserRoleByRole_id(role_id);
		}
		return x;
	}
	
	/**
	 * ���ݽ�ɫid���Ҳ˵�id��Ϣ
	 * @param role_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMenuId.do")
	public List<SysMenu>getMenuByRole_id(Integer role_id){
		List<SysMenu>menulist=roleService.getMenuByRole_id(role_id);
		return menulist;
	}
	
	/**
	 * ���ݽ�ɫid��ѯ��ɫ��Ϣ
	 * @param role_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getroleinfo.do")
	public Role getRoleById(Integer role_id){
		Role role=roleService.getRoleById(role_id);
		return role;
	}
	
	
	/**
	 * �������в˵����ý�ɫӵ�еĲ˵�ѡ��
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleTree.do")
	public List<Tree>getTreeChk(HttpServletRequest req,Integer role_id){
		//��ɫ��ӵ�еĲ˵�
		List<SysMenu>menulist=roleService.getMenuByRole_id(role_id);
		
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
			
			//�о�ѡ��
			for(SysMenu s:menulist){
				if(s.getMenu_id().equals(menu.getMenu_id())){
					node.setChecked(true);
					//node.setState("open");
				}
			}
			treelist.add(node);
		}
        return buildRoleTree(treelist,role_id);  
	}
	
	/**
	 * ���ݸ����˵����Ҷ�Ӧ���Ӳ˵�
	 * @param root
	 * @return
	 */
	public List<Tree> buildRoleTree(List<Tree> root,int role_id){  //child
		//��ɫ��ӵ�еĲ˵�
		List<SysMenu>menulist=roleService.getMenuByRole_id(role_id);
				
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
	    	//�˽�ɫ�д˲˵���ѡ��
	    	for(SysMenu s:menulist){
				if(s.getMenu_id().equals(menu.getMenu_id())){
					node.setChecked(true);
				}
			}
	    	clist.add(node);
	    	//�����Ӷ�Ӧ 
	    	root.get(i).setChildren(clist);
	    	}
	    }
	        return root;
	  }
}
