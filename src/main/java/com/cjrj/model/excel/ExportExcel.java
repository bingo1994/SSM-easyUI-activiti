package com.cjrj.model.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcel {
	 //HSSFWorkbook����(excel���ĵ�����)  
	HSSFWorkbook wb =null; 
	//sheet����excel�ı���
	HSSFSheet sheet=null;
	//����������Ϊ������(excel����)
	HSSFRow row=null;
	//��Ԫ��(excel�ĵ�Ԫ��)
	HSSFCell cell=null;  
	/**
	 * ����HSSFWorkbook����(excel���ĵ�����)  
	 * @return
	 */
    public HSSFWorkbook createsHSSFWorkbook(){
    	wb = new HSSFWorkbook(); 
    	return wb;
    }
    
    /**
     * �����µ�sheet����excel�ı���  
     * @param sheetName  
     * @return
     */
    public HSSFSheet ctreatesSheet(HSSFWorkbook wb,String sheetName){
    	  sheet=wb.createSheet(sheetName); 
    	 return sheet;
    }
    
    /**
     * ��sheet�ﴴ������������Ϊ������(excel����)��������0��65535֮����κ�һ��  
     * @param rownum
     * @return
     */
    public HSSFRow createsHSSFRow(HSSFSheet sheet,int rownum){
    	  row=sheet.createRow(rownum);  
    	 return row;
    }
    
    /**
     * ������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��  
     * @return
     */
    public HSSFCell createsHSSFCell(HSSFRow row,int cellnum){
    	 cell=row.createCell(cellnum);  
    	 return cell;
    }
    
    /**
     * ���õ�Ԫ������ 
     * @param value
     */
    public void setCellValues(HSSFCell cell,String value){
    	cell.setCellValue(value);
    }
    
    /**
     * �ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������  
     */
    public void addMergedRegions(HSSFSheet sheet,int endcol){
    	sheet.addMergedRegion(new CellRangeAddress(0,0,0,endcol));
    }
    
 
}
