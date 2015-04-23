
import COD_main.Customer;
import COD_main.User;
import COD_main.COD_Controller;
import java.sql.SQLException;


/**
 *
 * @author Petru Botnar
 */

//Main class that runs the application
public class COD_ElevenExpress {
    
    COD_Controller control;
    COD_ElevenExpress() throws SQLException{
        control = new COD_Controller();
    }
    
    
  public static void main(String[] args) throws SQLException {
      new COD_ElevenExpress();
  }  
}
