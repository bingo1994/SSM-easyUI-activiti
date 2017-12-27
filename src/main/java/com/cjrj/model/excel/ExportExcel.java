package com.cjrj.model.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcel {
	 //HSSFWorkbook对象(excel的文档对象)  
	HSSFWorkbook wb =null; 
	//sheet对象（excel的表单）
	HSSFSheet sheet=null;
	//行数，参数为行索引(excel的行)
	HSSFRow row=null;
	//单元格(excel的单元格)
	HSSFCell cell=null;  
	/**
	 * 创建HSSFWorkbook对象(excel的文档对象)  
	 * @return
	 */
    public HSSFWorkbook createsHSSFWorkbook(){
    	wb = new HSSFWorkbook(); 
    	return wb;
    }
    
    /**
     * 建立新的sheet对象（excel的表单）  
     * @param sheetName  
     * @return
     */
    public HSSFSheet ctreatesSheet(HSSFWorkbook wb,String sheetName){
    	  sheet=wb.createSheet(sheetName); 
    	 return sheet;
    }
    
    /**
     * 在sheet里创建行数，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
     * @param rownum
     * @return
     */
    public HSSFRow createsHSSFRow(HSSFSheet sheet,int rownum){
    	  row=sheet.createRow(rownum);  
    	 return row;
    }
    
    /**
     * 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个  
     * @return
     */
    public HSSFCell createsHSSFCell(HSSFRow row,int cellnum){
    	 cell=row.createCell(cellnum);  
    	 return cell;
    }
    
    /**
     * 设置单元格内容 
     * @param value
     */
    public void setCellValues(HSSFCell cell,String value){
    	cell.setCellValue(value);
    }
    
    /**
     * 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列  
     */
    public void addMergedRegions(HSSFSheet sheet,int endcol){
    	sheet.addMergedRegion(new CellRangeAddress(0,0,0,endcol));
    }
    
 
}
