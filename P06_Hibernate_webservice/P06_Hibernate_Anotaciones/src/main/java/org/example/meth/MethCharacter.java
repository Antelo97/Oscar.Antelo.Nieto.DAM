package org.example.meth;

import org.example.conn.HibernateUtil;
import org.example.pojos.Character;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MethCharacter {
    public static void createCharacter(Character character) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        session.save(character);
        transaction.commit();

        session.close();
    }

    public static Character getCharacterByIdApi(int idApi) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query<Character> query = session.createNativeQuery("SELECT * FROM Characters WHERE idApi_character = :idApi", Character.class)
                .setParameter("idApi", idApi);
        Character character = query.uniqueResult();
        transaction.commit();

        session.close();
        return character;
    }
}
