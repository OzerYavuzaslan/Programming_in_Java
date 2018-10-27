
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunningProccess_Class
{
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    public ArrayList<Employee_Class> bring_employee_method()
    {
        ArrayList<Employee_Class> output = new ArrayList<>();
        
        try
        {
            statement = con.createStatement();
            String query = "SELECT * FROM employee_table";
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String department = rs.getString("department");
                String salary = rs.getString("salary");
                output.add(new Employee_Class(id, name, surname, department, salary));
            }
            return output;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RunningProccess_Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean login_method(String username, String password)
    {
        String query = "SELECT * FROM admin_table WHERE username = ? and password = ?";
        
        try
        {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            /*if(rs.next() == false)
                return false;
            
            return true;*/
            
            return rs.next();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RunningProccess_Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void add_employee_method(String name, String surname, String department, String salary)
    {
        String query = "INSERT INTO employee_table (name, surname, department, salary) VALUES(?, ?, ?, ?)";
        try
        {
            preparedStatement = con.prepareStatement(query);
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, salary);
            
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RunningProccess_Class.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_employee_method(int id, String name, String surname, String department, String salary)
    {
        String query = "UPDATE employee_table SET name = ?, surname = ?, "
                + "department = ?, salary = ? where id = ?";
        
        try
        {
            preparedStatement = con.prepareStatement(query);
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, salary);
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RunningProccess_Class.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_employee_method(int id)
    {
        String query = "DELETE FROM employee_table where id = ?";
        
        try
        {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(RunningProccess_Class.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public RunningProccess_Class()
    {
        String url = "jdbc:mysql://" + Database_Class.host + ":"
                + Database_Class.port + "/" + Database_Class.db_name 
                + "?useUnicode=true&characterEncoding=utf8";
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Driver was not found!");
        }
        
        try
        {
            con = DriverManager.getConnection(url, Database_Class.username, Database_Class.password);
            System.out.println("Connection is successful.");  
        }
        catch (SQLException ex)
        {
            System.out.println("Connection is not successful!");
            //ex.printStackTrace();
        }
    }
}
