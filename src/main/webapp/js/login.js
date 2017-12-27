$(function(){
		/* $.messager.alert('��¼','��ð�','info',function(){
			alert('12334354');
		}); */
		/* $.messager.show({
			title:"ϵͳ��ʾ",
			msg:"��ð�"
		}); */
		// extend the 'equals' rule   
		$.extend($.fn.validatebox.defaults.rules, {   
		    equals: {   
		        validator: function(value,param){   
		            return value == $(param[0]).val();   
		        },   
		        message: '两次密码不一致.'  
		    }   
		});
		$.extend($.fn.validatebox.defaults.rules, {   
		    minLength: {   
		        validator: function(value, param){   
		            return value.length >= param[0];   
		        },   
		        message: '密码长度必须大于等于6位.'  
		    }   
		});  
		$("#dlg").dialog({
			title:'登录',
			width:400,
			height:300,
			modal:true,
			buttons:[{
				text:'登录',
				handler:function(){
					$("#dlg").dialog('close');
				}
			},{
				text:'注册',
				handler:function(){
					
				}
			}]
		});
		
	});