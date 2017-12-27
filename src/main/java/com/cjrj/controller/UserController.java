package com.cjrj.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cjrj.model.Dept;
import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;
import com.cjrj.model.Tree;
import com.cjrj.model.User;
import com.cjrj.model.excel.ExportExcel;
import com.cjrj.service.RoleService;
import com.cjrj.service.UserService;
import com.cjrj.util.Const;

@Controller
public class UserController {
	private static final transient Logger log = Logger.getLogger(UserController.class);
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	/**
	 * 跳转用户列表页面
	 */
	@RequestMapping("/userlist.do")
	public String goUserList(){
		return "forward:WEB-INF/jsp/user/userlist.jsp";
	}
	/**
	 * 跳转到角色列表页面
	 * @return
	 */
	@RequestMapping("/rolelist.do")
	public String goRoleList(){
		return "forward:WEB-INF/jsp/rolelist.jsp";
	}
	
	/**
	 * 跳转到部门列表页面
	 * @return
	 */
	@RequestMapping("/deptlist.do")
	public String goDeptList(){
		return "forward:WEB-INF/jsp/deptlist.jsp";
	}
	
	//跳转到表格页面
	@RequestMapping("/gochar.do")
	public String gotoChart(){
		return "chart";
	}
	
	//跳转流程
	@RequestMapping("/goactiviti.do")
	public String gotoActiviti(){
		return "activiti";
	}
	/**
	 * 前骤
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getmap.do")
	public Map<String,Object>getInfo(Integer user_id){
		User users=null;
		List<Role>rolelist=null;
		if(user_id!=null){
			users=userService.selUserById(user_id);
			rolelist=userService.getRoleByUser_id(user_id);
			if(users.getRegtime()!=null){
			String[]a=users.getRegtime().toLocaleString().split(" ");
			users.setStrtime(a[0]);
			
			}
		}
		List<Dept>dlist=userService.getDeptList();
		List<Role>rlist=userService.getRoleList();
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("rolelist", rolelist);
		map.put("user", users);
		map.put("dlist", dlist);
		map.put("rlist", rlist);
		return map;
	}
	
	/**
	 * 添加用户信息以及根据添加的用户信息添加用户角色表
	 * @return
	 */
	@Transactional//事务回滚
	@ResponseBody
	@RequestMapping("/addUser.do")
	public int addUser(User user, Integer[]rolename){
		int q=0;
		if(user.getUser_id()==null){//添加用户
			 q=userService.addUser(user);
			int uid=userService.getUserId();
			Map<String,Object>map=new HashMap<String, Object>();
			map.put("user_id", uid);
			if(rolename!=null){
				for(int i=0;i<rolename.length;i++){//添加用户角色表
					map.put("role_id", rolename[i]);
					userService.addUser_role(map);
				}
			}
		}else{
			 q=userService.updateUser(user);
			userService.deleteUser_roleByUid(user.getUser_id());
			Map<String,Object>map=new HashMap<String, Object>();
			if(rolename!=null){
				for(int i=0;i<rolename.length;i++){
					map.put("user_id", user.getUser_id());
					map.put("role_id", rolename[i]);
					userService.addUser_role(map);
				}
			}
		}
		return q;
		//return "forward:WEB-INF/jsp/user/userlist.jsp";
	}
	
	/**
	 * 根据用户id删除用户表及用户角色表的信息
	 * @return
	 */
	@RequestMapping("/deleteuser.do")
	public String deleteUserByUid(Integer user_id,Integer[] user_ids){
		if(user_ids==null||user_ids.length==0){
			userService.deleteByUser_id(user_id);
			userService.deleteUser_roleByUid(user_id);
		}else{
			for(Integer uid:user_ids){
				userService.deleteByUser_id(uid);
				userService.deleteUser_roleByUid(uid);
			}
		}
		return "forward:WEB-INF/jsp/user/userlist.jsp";
	}
	/**
	 * 查找所有用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getuserlist.do")
	public Map<String,Object>getUserList(@RequestParam(value="page",defaultValue="1",required=false)Integer page,Integer rows){
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("stime", (page-1)*rows);
		map.put("etime", page*rows);
		List<User>userlist=userService.getUserList(map);
		int usercount=userService.getUserCount();
		Map<String,Object>maps=new HashMap<String, Object>();
		maps.put("rows", userlist);
		maps.put("total ", usercount);
		return maps;
	}
	
	/**
	 * 根据用户名和密码登录查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login.do")
	public User loginUser(HttpServletRequest req,String user_account,String pass){
		User user=userService.getUserLoginByname(user_account);
		User newuser=new User();
		String result="";
		if(user==null){//用户不存在
			log.info("用户不存在");
			result="该用户不存在";
		}else{
			if(user.getPass().equals(pass)){//用户存在，密码正确
				//存user信息
				req.getSession().setAttribute(Const.SESSION_USER, user);
				log.info("登录成功");
				result="登录成功";
			}else{//用户存在密码不正确
				log.info("用户存在密码不正确");
				result="登录密码错误";
			}
		}
		newuser.setInfo(result);
		return newuser;
	}
	
	/**
	 * 根据用户的角色id查询菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMenu.do")
	public List<Tree> getMenuByURoleId(HttpServletRequest req){
		User user=(User)req.getSession().getAttribute(Const.SESSION_USER);
		
		List<Role>rlist=userService.getRoleByUser_id(user.getUser_id());
		List<SysMenu>menulist=userService.getMenuByURoleId(rlist.get(0).getRole_id());
		
		List<Tree>treelist=new ArrayList<Tree>();
		for(SysMenu menu:menulist){
			Tree node=new Tree();
			node.setId(menu.getMenu_id());
			node.setParentid(menu.getParentid());
			node.setText(menu.getMenu_name());
			node.setIconCls(menu.getIconCls());
			if(menu.getParentid()!=0){//子节点
				node.setParentid(menu.getParentid());
			}
			if(menu.getChildcount()>0){//有子节点
				node.setState("closed");
			}
			Map<String,Object>attr=new HashMap<String, Object>();
			attr.put("url", menu.getUrl());
			
			treelist.add(node);
		}
		return treelist;
	}
	
	
	 /** 
     * 获取部门树形列表   父级菜单
     */  
    @RequestMapping("/getTree.do")  
    @ResponseBody   
    public List<Tree> getTree(HttpServletRequest req){  
    	User user=(User)req.getSession().getAttribute(Const.SESSION_USER);
    	
    	List<Role>rlist=userService.getRoleByUser_id(user.getUser_id());
    	
    	List<SysMenu>root=new ArrayList<SysMenu>();
    	for(Role role:rlist){
    		List<SysMenu> roots=userService.findById(role.getRole_id());
    				root.addAll(roots);
    	}
    	for (int i = 0; i < root.size() - 1; i++) {
            for (int j = root.size() - 1; j > i; j--) {
                if (root.get(j).getMenu_name().equals(root.get(i).getMenu_name())) {
                	root.remove(j);
                }
            }
    	}
      //List<SysMenu> root = userService.findById(rlist.get(0).getRole_id());  //获取根节点（获取的值存到list中）  
      //父节点树
        List<Tree>treelist=new ArrayList<Tree>();
		for(SysMenu menu:root){
			Tree node=new Tree();
			//node.setState("closed");
			node.setId(menu.getMenu_id());
			node.setParentid(menu.getParentid());
			node.setText(menu.getMenu_name());
			node.setIconCls(menu.getIconCls());
			treelist.add(node);
		}
        return buildTree(treelist,user.getUser_id());  
    }  
  
    /**
     * 子集菜单
     * @param root
     * @return
     */
    public List<Tree> buildTree(List<Tree> root,int userid){  //child
    	Map<String,Object>map=new HashMap<String, Object>();
    	map.put("user_id", userid);
    	//所有父与子的集合
    	//循环父节点
        for(int i=0;i<root.size();i++){ 
        	map.put("parentid", root.get(i).getId());
        	//子节点
        	List<SysMenu> children = userService.findByPid(map); 
        	//循环子节点
        	List<Tree>clist=new ArrayList<Tree>();
        	for(SysMenu menu:children){
    			Tree node=new Tree();
    				node.setParentid(menu.getParentid());
    				node.setText(menu.getMenu_name());
    				node.setIconCls(menu.getIconCls());
    				node.setUrl(menu.getUrl());
    				clist.add(node);
    				//父与子对应 
    	        	root.get(i).setChildren(clist);
    			}
        	if(root.get(i).getChildren()!=null){
        		root.get(i).setState("closed");
        	}
    		}
        			return root;
        }  
    
    /**
     * 修改用户密码
     * @return
     */
    @RequestMapping("/upUserPass.do")
    public String upUserPass(User user){
    	Map<String,Object>map=new HashMap<String, Object>();
    	map.put("pass", user.getPass());
    	map.put("user_account", user.getUser_account());
    	userService.upUserPass(map);
    	return "../../layout";
    }
    
    /**
     * 查找旧密码
     * @param user_account
     * @return
     */
    @ResponseBody
    @RequestMapping("/getoldpass.do")
    public User getOldPass(String user_account){
    	User user=userService.getUserLoginByname(user_account);
    	return user;
    }
    
    /**
     * 验用户登录名
     * @param user_account
     * @return
     */
    @ResponseBody
    @RequestMapping("/chkaccount.do")
    public User getUser_account(String user_account){
    	User user=null;
    	 user=userService.getUserLoginByname(user_account);
    	 if(user==null){
    		 user=new User();
    		 user.setFlag(true);
    	 }
    	return user;
    }
    
    /**
     * 条件查询用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/getuserbycon.do")
    public Map<String,Object> getUserByCon(String name,String address,@RequestParam(value="page",defaultValue="1",required=true)int page,int rows){
    	try {
			name=new String(name.getBytes("ISO-8859-1"),"UTF-8");
			address=new String(address.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Map<String,Object>map=new HashMap<String, Object>();
    	map.put("name", name);
    	map.put("address",address);
    	List<User>ulist=userService.getUserByCon(map);
    	int count=userService.getCountByCon(map);
    	Map<String,Object>maps=new HashMap<String, Object>();
    	maps.put("rows", ulist);
    	maps.put("total", count);
    	return maps;
    }
    
    
    
    /**
     * 导出用户
     * @return
     */
    @RequestMapping("/exportexcel.do")
  //  @ResponseBody
    public void ExportExcel(HttpServletResponse response,Integer[] user_ids){
    	
    	ExportExcel excel=new ExportExcel();
		//HSSFWorkbook对象(excel的文档对象)  
		HSSFWorkbook wb =excel.createsHSSFWorkbook();
		//sheet对象（excel的表单）
		HSSFSheet sheet=excel.ctreatesSheet(wb,"用户表信息");
		//总行数
		//int rownum=userlist.size();
		
		HSSFRow rowHead=excel.createsHSSFRow(sheet, 0);//第一行
		//创建单元格
		HSSFCell cellOne=excel.createsHSSFCell(rowHead, 0);
		cellOne.setCellValue("用户信息");//表头
		
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列  
		excel.addMergedRegions(sheet,4);
		
		HSSFRow rows=excel.createsHSSFRow(sheet, 1);//第二行
		//创建单元格并设置单元格内容  
		rows.createCell(0).setCellValue("用户姓名");
		rows.createCell(1).setCellValue("用户性别");
		rows.createCell(2).setCellValue("用户地址");
		rows.createCell(3).setCellValue("部门名称");
		rows.createCell(4).setCellValue("角色名称");
    	
    	
    	
    	List<User>userlists=new ArrayList<User>();
    	if(user_ids==null||user_ids.length==0){//导出所有
    		//所有用户
    		List<User> userlist=userService.getUserDeptList();
    		
    		for(User user:userlist){
    			String str="";
    			List<String>strlist=new ArrayList<String>();
    			//该用户对应的所有角色
    			List<Role>rolelist=userService.getRoleNameByUser_id(user.getUser_id());
    			//循环角色
    			for(Role role:rolelist){
    				//strlist.add(role.getRole_name());
    				str=role.getRole_name()+","+str;
    			}
    			//user.setRole_name(strlist);//用户角色与用户一一对应
    			user.setStr(str);
    		}
    		//循环用户
			for(int i=0;i<userlist.size();i++){
				//每一行，参数为行索引(excel的行)
				HSSFRow row=excel.createsHSSFRow(sheet, i+2);//第三行
				//创建单元格并设置单元格内容  
				row.createCell(0).setCellValue(userlist.get(i).getUser_name());
				row.createCell(1).setCellValue(userlist.get(i).getUser_sex());
				row.createCell(2).setCellValue(userlist.get(i).getUser_address());
				row.createCell(3).setCellValue(userlist.get(i).getDept_name());
				row.createCell(4).setCellValue(userlist.get(i).getStr());
			}
    		try {
    			//输出Excel文件  
    		    OutputStream output=response.getOutputStream();  
    		    response.reset();  
    		    response.setHeader("Content-disposition", "attachment; filename=userinfo.xls");  
    		    response.setContentType("application/msexcel");          
    		    wb.write(output);  
    		    output.close();  
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}else{//导出部分
    		for(Integer i:user_ids){
    			String str="";
    			List<String>strlist=new ArrayList<String>();
    			User user=userService.getUserDeptByUser_id(i);
    			//该用户对应的所有角色
    			List<Role>rolelist=userService.getRoleNameByUser_id(user.getUser_id());
    			//循环角色
    			for(Role role:rolelist){
    				//strlist.add(role.getRole_name());
    				str=role.getRole_name()+","+str;
    			}
    			//user.setRole_name(strlist);//用户角色与用户一一对应
    			user.setStr(str);
    			userlists.add(user);
    		}
    		//循环用户
			for(int i=0;i<userlists.size();i++){
				//每一行，参数为行索引(excel的行)
				HSSFRow row=excel.createsHSSFRow(sheet, i+2);//第三行
				//创建单元格并设置单元格内容  
				row.createCell(0).setCellValue(userlists.get(i).getUser_name());
				row.createCell(1).setCellValue(userlists.get(i).getUser_sex());
				row.createCell(2).setCellValue(userlists.get(i).getUser_address());
				row.createCell(3).setCellValue(userlists.get(i).getDept_name());
				row.createCell(4).setCellValue(userlists.get(i).getStr());
			}
			try {
				//输出Excel文件  
			    OutputStream output=response.getOutputStream();  
			    response.reset();  
			    response.setHeader("Content-disposition", "attachment; filename=userinfo.xls");  
			    response.setContentType("application/msexcel");          
			    wb.write(output);  
			    output.close();  
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
    	}
    	
		
    
    
    
    
    
    
    /**
	 * 查找所有菜单并让用户拥有的菜单选中
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserTree.do")
	public List<Tree>getUserTreeChk(HttpServletRequest req,Integer user_id){
		//角色所拥有的菜单
		List<Integer>menulist=userService.getMenuByRole_id(user_id);
		//查找所有父菜单
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
			for(Integer s:menulist){
				if(s.equals(menu.getMenu_id())){
					node.setChecked(true);
					//node.setState("open");
				}
			}
			treelist.add(node);
		}
        return buildUserTree(treelist,menulist);  
	}
	
	/**
	 * 根据父级菜单查找对应的子菜单
	 * @param root
	 * @return
	 */
	public List<Tree> buildUserTree(List<Tree> root,List<Integer>menulist){  //child
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
	    	for(Integer s:menulist){
				if(s.equals(menu.getMenu_id())){
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
    
	/**
	 * 导入数据
	 * @param file
	 * @param req
	 * @return
	 */
	@RequestMapping("/impexcel.do")
    public String importExcel(@RequestParam("impexcel")MultipartFile file,HttpServletRequest req){
    	List<User>userlist=new ArrayList<User>();
    	if(file!=null){
    		//根据指定的文件输入流导入Excel从而产生Workbook对象  
    		try {
				Workbook wb = new HSSFWorkbook(file.getInputStream());
				//获取Excel文档中的第一个表单  
	    		Sheet sheet = wb.getSheetAt(0);  
	    		//对Sheet中的每一行进行迭代  
	    		for (Row r : sheet) {  
	    		//如果当前行的行号（从0开始）未达到2（第三行）则从新循环  
	    		if(r.getRowNum()<2){  
	    		      continue;  
	    		     } 
	    		User user=new User();
	    		//取出当前行第1个单元格数据，并封装在info实体stuName属性上  
	    		user.setUser_name(r.getCell(0).getStringCellValue());
	    		user.setUser_sex(r.getCell(1).getStringCellValue());
	    		user.setUser_address(r.getCell(2).getStringCellValue());
	    		userlist.add(user);  
	    		  }  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
    		
    	 }
    	//添加
    	for(User u:userlist){
    		userService.addUser(u);
    	}
		return "forward:userlist.do";
    }
    
	/**
	 * 表格数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/charinfo.do")
    public Map<String,Object>getChar(){
		Map<String,Object>map=new HashMap<String, Object>();
    	List<User>nanuser=userService.getUserCharNan();
    	List<User>nvuser=userService.getUserCharNv();
    	int naninfo[]=new int[12];
    	int nvinfo[]=new int[12];
    		//男
    		for(User u:nanuser){//数据库月份信息
        		String str[]=u.getMon().split("-");
        		int j=Integer.parseInt(str[1]);
        		for(int i=1;i<=12;i++){
        		if(j==i){
        			naninfo[i-1]=u.getNum();
        		}
        	}
    		}
    		
    		for(User u:nvuser){//数据库月份信息
        		String str[]=u.getMon().split("-");
        		int j=Integer.parseInt(str[1]);
        		for(int i=1;i<=12;i++){
        		if(j==i){
        			nvinfo[i-1]=u.getNum();
        		}
        	}
    		}
    	map.put("naninfo", naninfo);
    	map.put("nvinfo", nvinfo);
    	return map;
    }
    
}
