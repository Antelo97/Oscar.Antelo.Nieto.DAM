package org.example.meth;

import org.example.conn.HibernateUtil;
import org.example.pojos.Episode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MethEpisode {
    public static void createEpisode(Episode episode) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(episode);
        session.flush();
        transaction.commit();

        session.close();
    }

    public static Episode getEpisodeByUrl(String url) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query<Episode> query = session.createNativeQuery("SELECT * FROM Episodes WHERE url_episode = :url", Episode.class)
                .setParameter("url", url);
        Episode episode = query.uniqueResult();
        transaction.commit();

        session.close();
        return episode;
    }
}
