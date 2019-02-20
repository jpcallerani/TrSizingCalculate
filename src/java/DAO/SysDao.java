// 
// Decompiled by Procyon v0.5.30
// 

package DAO;

import java.sql.CallableStatement;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.JDBCException;
import java.util.List;
import org.hibernate.Session;

public class SysDao
{
    private Session sessao;
    private List listagem;
    private String _erro;
    
    public SysDao() {
        this._erro = "";
        this.sessao = NewHibernateUtil.getSessionFactory().openSession();
    }
    
    public String save(final Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.save(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        }
        catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        }
        catch (Exception e) {
            this._erro = e.getMessage();
            e.printStackTrace();
        }
        finally {
            this.sessao.close();
        }
        return this._erro;
    }
    
    public String update(final Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.update(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        }
        catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        }
        finally {
            this.sessao.close();
        }
        return this._erro;
    }
    
    public String delete(final Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.delete(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        }
        catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        }
        finally {
            this.sessao.close();
        }
        return this._erro;
    }
    
    public List listagem(final Class clazz, final List<Criterion> p_list_argumentos, final Order order, final Integer rownum) {
        try {
            final Criteria cri = this.sessao.createCriteria(clazz);
            p_list_argumentos.stream().forEach(criterion -> cri.add(criterion));
            if (order != null) {
                cri.addOrder(order);
            }
            if (rownum > 0) {
                cri.setMaxResults((int)rownum);
            }
            cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            this.listagem = cri.list();
        }
        catch (Exception ex) {
            System.out.println(ex.getCause() + "------" + ex.getMessage());
            this.listagem = null;
        }
        finally {
            this.sessao.close();
        }
        return this.listagem;
    }
    
    public List busca(final Object obj, final String query, final int value, final String fieldName) {
        try {
            final Query qy = this.sessao.getNamedQuery(query);
            qy.setInteger(fieldName, value);
            this.listagem = qy.list();
        }
        catch (Exception ex) {
            this.listagem = null;
        }
        finally {
            this.sessao.close();
        }
        return this.listagem;
    }
    
    public String executeObject(final String query) {
        try {
            this.sessao.doWork(cnctn -> {
                System.out.println(query);
                final CallableStatement call = cnctn.prepareCall("{ call " + query + " }");
                call.execute();
                cnctn.commit();
            });
        }
        catch (Exception ex) {
            this._erro = ex.getMessage();
            this.listagem = null;
        }
        finally {
            this.sessao.close();
        }
        return this._erro;
    }
    
    public void commit() {
        this.sessao.getTransaction().commit();
    }
    
    public void rollback() {
        this.sessao.getTransaction().rollback();
    }
    
    public void closeSession() {
        this.sessao.close();
    }
    
    public void openSession() {
        this.sessao.beginTransaction();
    }
}