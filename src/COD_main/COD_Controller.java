package COD_main;

import GUI.MainFrame;
import database_conn.DbConnect;
import java.sql.SQLException;


/**
 *
 * @author Petru Botnar
 */
//Controller class that initialises the frame
public class COD_Controller  {

   private MainFrame frame;
    
    public COD_Controller() throws SQLException {
        frame  = new MainFrame();
    }
}
