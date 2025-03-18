/*
 * The MIT License
 *
 * Copyright 2025 Pril Wolf.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package Model;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Pril Wolf
 */
public class CreatorModel implements AutoCloseable {

    public DefaultComboBoxModel roles;
    public DefaultListModel characters;
    SessionFactory sessionFactory;

    public CreatorModel() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        roles = new DefaultComboBoxModel(Roles.heroes());
        characters = new DefaultListModel();

        List<Creature> data = sessionFactory.fromSession(session -> {
            return session
                    .createSelectionQuery("from Creature", Creature.class)
                    .getResultList();
        });
        characters.addAll(data);
        Creature.setUpValidator();
    }

    public void create(String name, Roles role) {
        Creature newBorn = Creature.invoke(name, role);
        if (newBorn.validate() == true) {
            try {
                sessionFactory.inTransaction(session -> {
                    session.persist(newBorn);
                });
                characters.addElement(newBorn);
            } catch (Exception e) {
                System.err.println("session throws: " + e.getMessage());
            }
        } else {
            System.err.println("model not valid");
        }

    }

    public void delete(int idx) {
        Creature hero = (Creature) characters.remove(idx);
        sessionFactory.inTransaction(session -> {
            session.remove(hero);
        });
    }

    @Override
    public void close() {
        sessionFactory.close();
    }

}
