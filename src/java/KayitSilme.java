
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class KayitSilme {

    String silmeMesaji;

    public String getSilmeMesaji() {
        return silmeMesaji;
    }

    public void setSilmeMesaji(String silmeMesaji) {
        this.silmeMesaji = silmeMesaji;
    }
    
    public void kaydiSil(Kisiler k)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlar?zeroDateTimeBehavior=convertToNull [root on Default schema]","root","");
            ps = conn.prepareStatement("DELETE FROM bilgiler WHERE no=?");
            
            ps.setInt(1, k.getNo());
            
            i = ps.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Hata:" + e);
        }
        finally
        {
            try {
                
                conn.close();
                ps.close();
                
            } catch (Exception e) {
                System.err.println("Hata:" + e);
            }
        }
        
        if (i > 0) {
            silmeMesaji = "Silme işlemi başarılı";
        }
        else
        {
            silmeMesaji = "Silme işlemi hatalı";
        }
        System.out.println(silmeMesaji);
    }
    
}
