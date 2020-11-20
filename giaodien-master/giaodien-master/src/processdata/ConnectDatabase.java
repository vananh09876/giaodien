package processdata;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ConnectDatabase {
	final private String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=DU_LIEU_CHUNG_KHOAN;integratedSecurity=true;";
	final private String user = "sa";
	final private String pass = "123456789";
	private Connection conn;
	private Statement statement;
	
	public ConnectDatabase() {
		this.statement = null;
		try {
			this.conn = DriverManager.getConnection(this.dbURL, this.user, this.pass);
            if (conn == null) {
            	System.out.println("Connect error");
                System.exit(-1);
            }
            this.statement = this.conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public ResultSet executeQuerySelect(String query){
		ResultSet rs = null;
		try{
        // Create and execute a SELECT SQL statement
			rs= statement.executeQuery(query);
		} catch(SQLException e) {
            e.printStackTrace();
        }
        // Print results from select statement	
        return rs;
	}
	
	public ResultSet executeQueryInsert(String query) {
		ResultSet rs = null;
		try {
			PreparedStatement prepsInsertProduct = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            prepsInsertProduct.execute();
            // Retrieve the generated key from the insert.
            rs = prepsInsertProduct.getGeneratedKeys();
            
		} catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	
	
	public void close() {
		try{if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
        }}
		catch(SQLException e) {
            e.printStackTrace();
        }
	}

	
}
