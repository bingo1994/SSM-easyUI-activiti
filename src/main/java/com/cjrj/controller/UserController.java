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
	 * ��ת�û��б�ҳ��
	 */
	@RequestMapping("/userlist.do")
	public String goUserList(){
		return "forward:WEB-INF/jsp/user/userlist.jsp";
	}
	/**
	 * ��ת����ɫ�б�ҳ��
	 * @return
	 */
	@RequestMapping("/rolelist.do")
	public String goRoleList(){
		return "forward:WEB-INF/jsp/rolelist.jsp";
	}
	
	/**
	 * ��ת�������б�ҳ��
	 * @return
	 */
	@RequestMapping("/deptlist.do")
	public String goDeptList(){
		return "forward:WEB-INF/jsp/deptlist.jsp";
	}
	
	//��ת�����ҳ��
	@RequestMapping("/gochar.do")
	public String gotoChart(){
		return "chart";
	}
	
	//��ת����
	@RequestMapping("/goactiviti.do")
	public String gotoActiviti(){
		return "activiti";
	}
	/**
	 * ǰ��
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
	 * ����û���Ϣ�Լ�������ӵ��û���Ϣ����û���ɫ��
	 * @return
	 */
	@Transactional//����ع�
	@ResponseBody
	@RequestMapping("/addUser.do")
	public int addUser(User user, Integer[]rolename){
		int q=0;
		if(user.getUser_id()==null){//����û�
			 q=userService.addUser(user);
			int uid=userService.getUserId();
			Map<String,Object>map=new HashMap<String, Object>();
			map.put("user_id", uid);
			if(rolename!=null){
				for(int i=0;i<rolename.length;i++){//����û���ɫ��
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
	 * �����û�idɾ���û����û���ɫ�����Ϣ
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
	 * ���������û���Ϣ
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
	 * �����û����������¼��ѯ
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login.do")
	public User loginUser(HttpServletRequest req,String user_account,String pass){
		User user=userService.getUserLoginByname(user_account);
		User newuser=new User();
		String result="";
		if(user==null){//�û�������
			log.info("�û�������");
			result="���û�������";
		}else{
			if(user.getPass().equals(pass)){//�û����ڣ�������ȷ
				//��user��Ϣ
				req.getSession().setAttribute(Const.SESSION_USER, user);
				log.info("��¼�ɹ�");
				result="��¼�ɹ�";
			}else{//�û��������벻��ȷ
				log.info("�û��������벻��ȷ");
				result="��¼�������";
			}
		}
		newuser.setInfo(result);
		return newuser;
	}
	
	/**
	 * �����û��Ľ�ɫid��ѯ�˵�
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
			if(menu.getParentid()!=0){//�ӽڵ�
				node.setParentid(menu.getParentid());
			}
			if(menu.getChildcount()>0){//���ӽڵ�
				node.setState("closed");
			}
			Map<String,Object>attr=new HashMap<String, Object>();
			attr.put("url", menu.getUrl());
			
			treelist.add(node);
		}
		return treelist;
	}
	
	
	 /** 
     * ��ȡ���������б�   �����˵�
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
      //List<SysMenu> root = userService.findById(rlist.get(0).getRole_id());  //��ȡ���ڵ㣨��ȡ��ֵ�浽list�У�  
      //���ڵ���
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
     * �Ӽ��˵�
     * @param root
     * @return
     */
    public List<Tree> buildTree(List<Tree> root,int userid){  //child
    	Map<String,Object>map=new HashMap<String, Object>();
    	map.put("user_id", userid);
    	//���и����ӵļ���
    	//ѭ�����ڵ�
        for(int i=0;i<root.size();i++){ 
        	map.put("parentid", root.get(i).getId());
        	//�ӽڵ�
        	List<SysMenu> children = userService.findByPid(map); 
        	//ѭ���ӽڵ�
        	List<Tree>clist=new ArrayList<Tree>();
        	for(SysMenu menu:children){
    			Tree node=new Tree();
    				node.setParentid(menu.getParentid());
    				node.setText(menu.getMenu_name());
    				node.setIconCls(menu.getIconCls());
    				node.setUrl(menu.getUrl());
    				clist.add(node);
    				//�����Ӷ�Ӧ 
    	        	root.get(i).setChildren(clist);
    			}
        	if(root.get(i).getChildren()!=null){
        		root.get(i).setState("closed");
        	}
    		}
        			return root;
        }  
    
    /**
     * �޸��û�����
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
     * ���Ҿ�����
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
     * ���û���¼��
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
     * ������ѯ�û���Ϣ
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
     * �����û�
     * @return
     */
    @RequestMapping("/exportexcel.do")
  //  @ResponseBody
    public void ExportExcel(HttpServletResponse response,Integer[] user_ids){
    	
    	ExportExcel excel=new ExportExcel();
		//HSSFWorkbook����(excel���ĵ�����)  
		HSSFWorkbook wb =excel.createsHSSFWorkbook();
		//sheet����excel�ı���
		HSSFSheet sheet=excel.ctreatesSheet(wb,"�û�����Ϣ");
		//������
		//int rownum=userlist.size();
		
		HSSFRow rowHead=excel.createsHSSFRow(sheet, 0);//��һ��
		//������Ԫ��
		HSSFCell cellOne=excel.createsHSSFCell(rowHead, 0);
		cellOne.setCellValue("�û���Ϣ");//��ͷ
		
		//�ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������  
		excel.addMergedRegions(sheet,4);
		
		HSSFRow rows=excel.createsHSSFRow(sheet, 1);//�ڶ���
		//������Ԫ�����õ�Ԫ������  
		rows.createCell(0).setCellValue("�û�����");
		rows.createCell(1).setCellValue("�û��Ա�");
		rows.createCell(2).setCellValue("�û���ַ");
		rows.createCell(3).setCellValue("��������");
		rows.createCell(4).setCellValue("��ɫ����");
    	
    	
    	
    	List<User>userlists=new ArrayList<User>();
    	if(user_ids==null||user_ids.length==0){//��������
    		//�����û�
    		List<User> userlist=userService.getUserDeptList();
    		
    		for(User user:userlist){
    			String str="";
    			List<String>strlist=new ArrayList<String>();
    			//���û���Ӧ�����н�ɫ
    			List<Role>rolelist=userService.getRoleNameByUser_id(user.getUser_id());
    			//ѭ����ɫ
    			for(Role role:rolelist){
    				//strlist.add(role.getRole_name());
    				str=role.getRole_name()+","+str;
    			}
    			//user.setRole_name(strlist);//�û���ɫ���û�һһ��Ӧ
    			user.setStr(str);
    		}
    		//ѭ���û�
			for(int i=0;i<userlist.size();i++){
				//ÿһ�У�����Ϊ������(excel����)
				HSSFRow row=excel.createsHSSFRow(sheet, i+2);//������
				//������Ԫ�����õ�Ԫ������  
				row.createCell(0).setCellValue(userlist.get(i).getUser_name());
				row.createCell(1).setCellValue(userlist.get(i).getUser_sex());
				row.createCell(2).setCellValue(userlist.get(i).getUser_address());
				row.createCell(3).setCellValue(userlist.get(i).getDept_name());
				row.createCell(4).setCellValue(userlist.get(i).getStr());
			}
    		try {
    			//���Excel�ļ�  
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
    		
    	}else{//��������
    		for(Integer i:user_ids){
    			String str="";
    			List<String>strlist=new ArrayList<String>();
    			User user=userService.getUserDeptByUser_id(i);
    			//���û���Ӧ�����н�ɫ
    			List<Role>rolelist=userService.getRoleNameByUser_id(user.getUser_id());
    			//ѭ����ɫ
    			for(Role role:rolelist){
    				//strlist.add(role.getRole_name());
    				str=role.getRole_name()+","+str;
    			}
    			//user.setRole_name(strlist);//�û���ɫ���û�һһ��Ӧ
    			user.setStr(str);
    			userlists.add(user);
    		}
    		//ѭ���û�
			for(int i=0;i<userlists.size();i++){
				//ÿһ�У�����Ϊ������(excel����)
				HSSFRow row=excel.createsHSSFRow(sheet, i+2);//������
				//������Ԫ�����õ�Ԫ������  
				row.createCell(0).setCellValue(userlists.get(i).getUser_name());
				row.createCell(1).setCellValue(userlists.get(i).getUser_sex());
				row.createCell(2).setCellValue(userlists.get(i).getUser_address());
				row.createCell(3).setCellValue(userlists.get(i).getDept_name());
				row.createCell(4).setCellValue(userlists.get(i).getStr());
			}
			try {
				//���Excel�ļ�  
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
	 * �������в˵������û�ӵ�еĲ˵�ѡ��
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserTree.do")
	public List<Tree>getUserTreeChk(HttpServletRequest req,Integer user_id){
		//��ɫ��ӵ�еĲ˵�
		List<Integer>menulist=userService.getMenuByRole_id(user_id);
		//�������и��˵�
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
	 * ���ݸ����˵����Ҷ�Ӧ���Ӳ˵�
	 * @param root
	 * @return
	 */
	public List<Tree> buildUserTree(List<Tree> root,List<Integer>menulist){  //child
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
	    	for(Integer s:menulist){
				if(s.equals(menu.getMenu_id())){
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
    
	/**
	 * ��������
	 * @param file
	 * @param req
	 * @return
	 */
	@RequestMapping("/impexcel.do")
    public String importExcel(@RequestParam("impexcel")MultipartFile file,HttpServletRequest req){
    	List<User>userlist=new ArrayList<User>();
    	if(file!=null){
    		//����ָ�����ļ�����������Excel�Ӷ�����Workbook����  
    		try {
				Workbook wb = new HSSFWorkbook(file.getInputStream());
				//��ȡExcel�ĵ��еĵ�һ����  
	    		Sheet sheet = wb.getSheetAt(0);  
	    		//��Sheet�е�ÿһ�н��е���  
	    		for (Row r : sheet) {  
	    		//�����ǰ�е��кţ���0��ʼ��δ�ﵽ2�������У������ѭ��  
	    		if(r.getRowNum()<2){  
	    		      continue;  
	    		     } 
	    		User user=new User();
	    		//ȡ����ǰ�е�1����Ԫ�����ݣ�����װ��infoʵ��stuName������  
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
    	//���
    	for(User u:userlist){
    		userService.addUser(u);
    	}
		return "forward:userlist.do";
    }
    
	/**
	 * �������
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
    		//��
    		for(User u:nanuser){//���ݿ��·���Ϣ
        		String str[]=u.getMon().split("-");
        		int j=Integer.parseInt(str[1]);
        		for(int i=1;i<=12;i++){
        		if(j==i){
        			naninfo[i-1]=u.getNum();
        		}
        	}
    		}
    		
    		for(User u:nvuser){//���ݿ��·���Ϣ
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
