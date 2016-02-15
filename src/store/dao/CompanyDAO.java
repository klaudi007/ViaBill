package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import universal.connection_helper.DBConnection;

public class CompanyDAO {

private Connection conn;
	
	public void setConnection(Connection conn){
		this.conn=conn;
	}

	public List<Company> findAll() {
		List<Company> list = new ArrayList<Company>();
		Connection c = null;
		String sql = "SELECT * FROM STORE.COMPANY";
		try {
			c = DBConnection.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnection.close(c);
		}
		return list;
	}

	public Company findById(int id) {
		String sql = "SELECT * FROM STORE.COMPANY WHERE id = ?";
		Company c = null;
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				c = processRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnection.close(conn);
		}
		return c;
	}

	public Company insert(Company c) {

		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement(
					"INSERT INTO STORE.COMPANY (name,address,city,country,email,phone,modified_by) "
					+ "VALUES (?, ?, ?, ? ,?, ? ,?)",
					new String[] { "ID" });
			
			ps.setString(1, c.getName());
			ps.setString(2, c.getAddress());
			ps.setString(3, c.getCity());
			ps.setString(4, c.getCountry());
			ps.setString(5, c.getEmail());
			ps.setString(6, c.getPhone());
			ps.setString(7, c.getModified_by());

			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()){
			int id = rs.getInt(1);
			c.setId(id);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return c;
	}

	public Company update(Company c) {

		try {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE STORE.COMPANY SET name=?,address=?,city=?,country=?,email=?,phone=?,modified_by=? WHERE id=?");
			
			ps.setString(1, c.getName());
			ps.setString(2, c.getAddress());
			ps.setString(3, c.getCity());
			ps.setString(4, c.getCountry());
			ps.setString(5, c.getEmail());
			ps.setString(6, c.getPhone());
			ps.setString(7, c.getModified_by());

			ps.setInt(8, c.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return c;
	}
	
	public boolean remove(int id) {
		try {
			PreparedStatement ps = conn
					.prepareStatement("DELETE FROM STORE.COMPANY WHERE id=?");
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			return count == 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected Company processRow(ResultSet rs) throws SQLException {
		
		Company c = new Company();
		c.setId(rs.getInt("id"));
		c.setModification_date(rs.getTimestamp("modification_date"));
		c.setName(rs.getString("name"));
		c.setAddress(rs.getString("address"));
		c.setCity(rs.getString("city"));
		c.setCountry(rs.getString("country"));
		c.setEmail(rs.getString("email"));
		c.setPhone(rs.getString("phone"));
		c.setModified_by(rs.getString("modified_by"));
		
		return c;
		
	}

}
