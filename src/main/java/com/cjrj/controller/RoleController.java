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
	 * 查询角色表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getrolelist.do")
	public Map<String,Object> getRoleList(@RequestParam(value="page",defaultValue="1",required=false)Integer page,Integer rows){
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("stime", (page-1)*rows);
		map.put("etime", page*rows);
		//所有角色表信息
		List<Role>rolelist=roleService.getRoleList(map);
		
		for(Role r:rolelist){
			//角色对应的父级菜单
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
	 * 查找菜单
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTree.do")
	public List<Tree>getTree(HttpServletRequest req){
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
        return buildTree(treelist);  
	}
	
	/**
	 * 根据父级菜单查找对应的子菜单
	 * @param root
	 * @return
	 */
	public List<Tree> buildTree(List<Tree> root){  //child
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
	
	/**
	 * 验证添加的角色是否存在
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
	 * 添加角色
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
		//根据添加的角色id以及菜单id循环添加角色-菜单表
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
	 * 修改角色
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
		//删除角色菜单中间表
		roleService.delRoleMenuByRole_id(role.getRole_id());
		//循环添加角色-菜单表
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
	 * 删除角色
	 * @param role_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delrole.do")
	public int delRole(Integer role_id){
		int x=roleService.delRoleById(role_id);
		if(x>0){
			//删除角色菜单
			 roleService.delRoleMenuByRole_id(role_id);
			 //删除用户角色表
			 roleService.delUserRoleByRole_id(role_id);
		}
		return x;
	}
	
	/**
	 * 根据角色id查找菜单id信息
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
	 * 根据角色id查询角色信息
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
	 * 查找所有菜单并让角色拥有的菜单选择
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleTree.do")
	public List<Tree>getTreeChk(HttpServletRequest req,Integer role_id){
		//角色所拥有的菜单
		List<SysMenu>menulist=roleService.getMenuByRole_id(role_id);
		
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
			
			//有就选中
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
	 * 根据父级菜单查找对应的子菜单
	 * @param root
	 * @return
	 */
	public List<Tree> buildRoleTree(List<Tree> root,int role_id){  //child
		//角色所拥有的菜单
		List<SysMenu>menulist=roleService.getMenuByRole_id(role_id);
				
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
	    	//此角色有此菜单就选中
	    	for(SysMenu s:menulist){
				if(s.getMenu_id().equals(menu.getMenu_id())){
					node.setChecked(true);
				}
			}
	    	clist.add(node);
	    	//父与子对应 
	    	root.get(i).setChildren(clist);
	    	}
	    }
	        return root;
	  }
}
