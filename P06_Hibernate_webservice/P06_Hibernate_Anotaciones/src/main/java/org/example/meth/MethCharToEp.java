package org.example.meth;

import org.example.conn.HibernateUtil;
import org.example.pojos.CharToEp;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MethCharToEp {
    public static void createCharToEp(CharToEp charToEp) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(charToEp);
        session.flush();
        transaction.commit();

        session.close();
    }
}
