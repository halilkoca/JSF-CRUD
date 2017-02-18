

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class KayitGuncelle {
    
    private static final long serialVersionUID = 1L;
    String guncellemeMesaji;

    public String getGuncellemeMesaji() {
        return guncellemeMesaji;
    }

    public void setGuncellemeMesaji(String guncellemeMesaji) {
        this.guncellemeMesaji = guncellemeMesaji;
    }

    public void gorunurluguDegistir(Kisiler k)
    {
        k.setGuncellenebilirlik(true);
    }
    
    /**
     *
     * @param k Kisiler sınıfından k
     */
    public void kaydiGuncelle(Kisiler k)
    {
        k.setGuncellenebilirlik(false);
        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlar","root","");
            ps = conn.prepareStatement("UPDATE bilgiler SET isim=?, soyisim=?, universite=? WHERE no=?");
             
            ps.setString(1, k.getIsim());
            ps.setString(2, k.getSoyisim());
            ps.setString(3, k.getUniversite());
            ps.setInt(4, k.getNo());
            
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
        
        if (i > 0) 
        {
            guncellemeMesaji = "Güncelleme başarılı";
        }
        else
        {
            guncellemeMesaji = "Güncelleme yapılırken hata oluştu";
        }
        System.out.println(guncellemeMesaji);
    }

}
