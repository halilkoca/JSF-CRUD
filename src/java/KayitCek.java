

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


@ManagedBean
@RequestScoped
public class KayitCek implements Filter{

    List<Kisiler> sorguSonucu;

    public List<Kisiler> getSorguSonucu() {
        return sorguSonucu;
    }

    public void setSorguSonucu(List<Kisiler> sorguSonucu) {
        this.sorguSonucu = sorguSonucu;
    }
    
    
    @PostConstruct
    public void veriTabanindanCek()
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        sorguSonucu = new ArrayList<>();
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/kayitlar", "root", "");
            ps = (PreparedStatement) conn.prepareStatement("SELECT * FROM bilgiler");
            rs = ps.executeQuery();
            while (rs.next()) {                
                Kisiler kisi = new Kisiler();
                kisi.setNo(rs.getInt("no"));
                kisi.setIsim(rs.getString("isim"));
                kisi.setSoyisim(rs.getString("soyisim"));
                kisi.setUniversite(rs.getString("universite"));
                sorguSonucu.add(kisi);
            }
            
            
        } catch (Exception e) {
            System.err.println("Hata:" + e);
        }finally
        {
            try {
                conn.close();
                ps.close();
            } catch (Exception e) {
                System.err.println("Hata:" + e);
            }
        }
        

    }    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
    
    
    
}
