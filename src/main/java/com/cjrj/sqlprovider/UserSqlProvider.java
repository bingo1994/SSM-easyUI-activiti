package com.cjrj.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.cjrj.model.User;

public class UserSqlProvider {

	/**
	 * 动态修改
	 * @param user
	 * @return
	 */
	public String updateUser(final User user){
		return new SQL(){
			{
				UPDATE("mod_user");
				if (user.getUser_account() != null) {
					SET("user_account=#{user_account}");
				}
				if (user.getUser_name() != null) {
					SET("user_name=#{user_name}");
				}
				if (user.getUser_desc() != null) {
					SET("user_desc=#{user_desc}");
				}
				if (user.getPass() != null) {
					SET("pass=#{pass}");
				}
				if (user.getUdept_id() != null) {
					SET("udept_id=#{udept_id}");
				}
				if (user.getUser_address() != null) {
					SET("user_address=#{user_address}");
				}
				if (user.getUser_sex() != null) {
					SET("user_sex=#{user_sex}");
				}
				if (user.getRegtime() != null) {
					SET("regtime=#{regtime}");
				}
			WHERE("user_id=#{user_id}");
			}
		}.toString();
	}
	/**
	 * 动态添加用户表
	 * @param user
	 * @return
	 */
	public String addUser(final User user) {
		return new SQL() {
			{
				INSERT_INTO("mod_user");
				if (user.getUser_id() != null) {
					VALUES("user_id", "#{user_id}");
				}
				if (user.getUser_account() != null) {
					VALUES("user_account", "#{user_account}");
				}
				if (user.getUser_name() != null) {
					VALUES("user_name", "#{user_name}");
				}
				if (user.getUser_desc() != null) {
					VALUES("user_desc", "#{user_desc}");
				}
				if (user.getPass() != null) {
					VALUES("pass", "#{pass}");
				}
				if (true) {
					VALUES("create_date", "sysdate");
				}
				if (user.getUdept_id() != null) {
					VALUES("udept_id", "#{udept_id}");
				}
				if (user.getUser_address() != null) {
					VALUES("user_address", "#{user_address}");
				}
				if (user.getUser_sex() != null) {
					VALUES("user_sex", "#{user_sex}");
				}
				if (user.getRegtime() != null) {
					VALUES("regtime", "#{regtime}");
				}
			}
		}.toString();
	}
}
