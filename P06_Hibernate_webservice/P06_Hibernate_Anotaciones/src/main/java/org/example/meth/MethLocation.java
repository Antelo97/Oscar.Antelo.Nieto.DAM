package org.example.meth;

import org.example.conn.HibernateUtil;
import org.example.pojos.Location;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MethLocation {
    public static void createLocation(Location location) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(location);
        transaction.commit();

        session.close();
    }

    public static Location getLocationByUrl(String url) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query<Location> query = session.createNativeQuery("SELECT * FROM Locations WHERE url_location = :url", Location.class)
                .setParameter("url", url);
        Location location = query.uniqueResult();
        transaction.commit();

        session.close();
        return location;
    }
}
