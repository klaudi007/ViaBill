package bsn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import dao.CompanyDAO;
import model.Company;

import universal.connection_helper.DBConnection;

public class Central {

//	            Get All
	public static List<Company> getAllCompany(){
		return new CompanyDAO().findAll();
	}

	
	public static List<Company> createCompany(Object requestEntity,String userName) throws Exception{

		Company cat=new Company();
		CompanyDAO catDao=new CompanyDAO();
		
		ObjectMapper objectMapper=new ObjectMapper();
		HashMap<String,Object> hashMap=new HashMap<>();
		
		hashMap=(HashMap<String, Object>) requestEntity;
		
		try {
			cat=objectMapper.convertValue(hashMap.get("company"), Company.class);
		} catch (Exception e) {
			System.out.println("Object convertion Exception"+e);
		}
		cat.setModified_by(userName);
		
		Connection conn=null;
	    try {
    		conn=DBConnection.getConnection();
    		conn.setAutoCommit(false);
    	
			catDao.setConnection(conn);
			catDao.insert(cat);

			conn.commit();
    } catch (Exception e) {
		try {if(conn!=null){conn.rollback();}
		} catch (SQLException e1) {}
	} finally {DBConnection.close(conn);}
			

		return catDao.findAll();
		
	}
	
	public static List<Company> updateCompany(Object requestEntity, int id, String userName) throws Exception{

		Company cat=new Company();
		CompanyDAO catDao=new CompanyDAO();
		
		ObjectMapper objectMapper=new ObjectMapper();
		HashMap<String,Object> hashMap=new HashMap<>();
		
		hashMap=(HashMap<String, Object>) requestEntity;
		
		try {
			cat=objectMapper.convertValue(hashMap.get("company"), Company.class);
		} catch (Exception e) {
			System.out.println("Object convertion Exception"+e);
		}
		cat.setModified_by(userName);
		cat.setId(id);
		Connection conn=null;
	    try {
    		conn=DBConnection.getConnection();
    		conn.setAutoCommit(false);
    	
			catDao.setConnection(conn);
			catDao.update(cat);

			conn.commit();
    } catch (Exception e) {
		try {if(conn!=null){conn.rollback();}
		} catch (SQLException e1) {}
	} finally {DBConnection.close(conn);}
			

		return catDao.findAll();
		
	}
	
	
	public static List<Company> removeCompany(int id){
	
	CompanyDAO cd=new CompanyDAO();
	Connection conn = null;
	try {
		conn = DBConnection.getConnection();
		conn.setAutoCommit(false);

		cd.setConnection(conn);
		
		cd.remove(id);
		
		conn.commit();
	} catch (Exception e) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e1) {
		}
	} finally {
		DBConnection.close(conn);
	}
	
	return cd.findAll();
}
	
	public static Company getParticularCompany(int id){
		return new CompanyDAO().findById(id);
	}
	
//              END GET ALL
	
//	            GET PARTICULAR by FK
	
//	public static List<Product> getProductbyFK(int fk_category){
//		return new ProductDAO().findByFK(fk_category);
//	}
//
//	public static WrProduct getProductDetails(int fk_product) {
//		WrProduct w = new WrProduct();
//
//		DetailDAO dd = new DetailDAO();
//		ImagesDAO imd = new ImagesDAO();
//
//		Connection conn = null;
//		try {
//			conn = DBConnection.getConnection();
//			conn.setAutoCommit(false);
//
//			dd.setConnection(conn);
//			imd.setConnection(conn);
//
//			w.setDetail(dd.findByFk(fk_product));
//			w.setImages(imd.findByFk(fk_product));
//
//			conn.commit();
//		} catch (Exception e) {
//			try {
//				if (conn != null) {
//					conn.rollback();
//				}
//			} catch (SQLException e1) {
//			}
//		} finally {
//			DBConnection.close(conn);
//		}
//
//		return w;
//	}
//	
////	            END GET PARTICULAR by FK
//	
//	
//// 	               CREATE
//	
//	public static List<Category> createCategory(Object requestEntity,String userName) throws Exception{
//
//		Category cat=new Category();
//		CategoryDAO catDao=new CategoryDAO();
//		
//		ObjectMapper objectMapper=new ObjectMapper();
//		HashMap<String,Object> hashMap=new HashMap<>();
//		
//		hashMap=(HashMap<String, Object>) requestEntity;
//		
//		try {
//			cat=objectMapper.convertValue(hashMap.get("cat"), Category.class);
//		} catch (Exception e) {
//			System.out.println("Object convertion Exception"+e);
//		}
//		cat.setModified_by(userName);
//		
//		Connection conn=null;
//	    try {
//    		conn=DBConnection.getConnection();
//    		conn.setAutoCommit(false);
//    	
//			catDao.setConnection(conn);
//			catDao.insert(cat);
//
//			conn.commit();
//    } catch (Exception e) {
//		try {if(conn!=null){conn.rollback();}
//		} catch (SQLException e1) {}
//	} finally {DBConnection.close(conn);}
//			
//		
//		
//		
//		return catDao.findAll();
//		
//		
//	}
//	public static List<Product> createProduct(Object requestEntity,String userName) throws Exception{
//		long product_code=System.currentTimeMillis();
//		
//		Product pro=new Product();
//		ProductDAO proDao=new ProductDAO();
//		
//		Detail det=new Detail();
//		DetailDAO dd=new DetailDAO();
//		
//		Images im=new Images();
//		ImagesDAO id=new ImagesDAO();
//		
//		String mainImageBnr="",mainImageType="",mainImage="";
//		HashMap<String,String> images=new HashMap<>();
//		
//		ObjectMapper objectMapper=new ObjectMapper();
//		HashMap<String,Object> hashMap=new HashMap<>();
//		
//		hashMap=(HashMap<String, Object>) requestEntity;
//		
//		try {
//			pro=objectMapper.convertValue(hashMap.get("pro"), Product.class);
//			det=objectMapper.convertValue(hashMap.get("detail"), Detail.class);
//			mainImageBnr=objectMapper.convertValue(hashMap.get("mainImage"), String.class);
//			mainImageType=objectMapper.convertValue(hashMap.get("type"), String.class);
//			images=objectMapper.convertValue(hashMap.get("images"), HashMap.class);
//			
//		} catch (Exception e) {
//			System.out.println("Object convertion Exception"+e);
//		}
//		
//		mainImage=saveMainImage(mainImageBnr,mainImageType);
//		List<String> imagesNames=saveDetailImages(images);
//		
//		pro.setImage(mainImage);
//		pro.setModified_by(userName);
//		det.setImage(mainImage);
//		det.setCode(String.valueOf(product_code));
//		det.setModified_by(userName);
//		
//		Connection conn=null;
//	    try {
//    		conn=DBConnection.getConnection();
//    		conn.setAutoCommit(false);
//    	
//			proDao.setConnection(conn);
//			proDao.insert(pro);
//			
//			det.setFk_product(pro.getId());
//			dd.setConnection(conn);
//			dd.insert(det);
//			
//			id.setConnection(conn);
//			for(String imageName:imagesNames){
//				im.setFk_product(pro.getId());
//				im.setImage(imageName);
//				id.insert(im);
//			}
//			
//			conn.commit();
//    } catch (Exception e) {
//		try {if(conn!=null){conn.rollback();}
//		} catch (SQLException e1) {}
//	} finally {DBConnection.close(conn);}
//			
//		
//		
//		
//		return proDao.findByFK(pro.getFk_category());
//		
//		
//	}
////	            END CREATE
//	
////              REMOVE
//	
//	public static List<Category> removeCategory(int id) {
//		CategoryDAO cd = new CategoryDAO();
//		ProductDAO pd = new ProductDAO();
//		DetailDAO dd = new DetailDAO();
//		ImagesDAO imd = new ImagesDAO();
//
//		// Orphan Removing - remove Category means - remove related
//		// Product-Detail and Images (I have to remove related files too)
//		Connection conn = null;
//		try {
//			conn = DBConnection.getConnection();
//			conn.setAutoCommit(false);
//
//			cd.setConnection(conn);
//			pd.setConnection(conn);
//			dd.setConnection(conn);
//			imd.setConnection(conn);
//
//			cd.remove(id);
//			
//			List<Product> pList=pd.findByCategory(id);
//			for(Product p: pList){
//				dd.removeByFk(p.getId());
//				imd.removeByFk(p.getId());
//			}
//			pd.removeByFk(id);
//			
//			conn.commit();
//		} catch (Exception e) {
//			try {
//				if (conn != null) {
//					conn.rollback();
//				}
//			} catch (SQLException e1) {
//			}
//		} finally {
//			DBConnection.close(conn);
//		}
//
//		return cd.findAll();
//	}
//	public static List<Product> removeProduct(int id,String fk_category){
//		ProductDAO pd=new ProductDAO();
//		DetailDAO dd = new DetailDAO();
//		ImagesDAO imd = new ImagesDAO();
//		
//		Connection conn = null;
//		try {
//			conn = DBConnection.getConnection();
//			conn.setAutoCommit(false);
//
//			pd.setConnection(conn);
//			dd.setConnection(conn);
//			imd.setConnection(conn);
//			
//			dd.removeByFk(id);
//			imd.removeByFk(id);
//			pd.remove(id);
//			
//			conn.commit();
//		} catch (Exception e) {
//			try {
//				if (conn != null) {
//					conn.rollback();
//				}
//			} catch (SQLException e1) {
//			}
//		} finally {
//			DBConnection.close(conn);
//		}
//		
//		return pd.findByFK(Integer.parseInt(fk_category));
//	}
//	
//	
//              END REMOVE
		
}
