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
package fr.ft.swingy.Model;

import fr.ft.swingy.Model.Entity.Creature;
import fr.ft.swingy.Model.Entity.Roles;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import org.hibernate.SessionFactory;

/**
 * Model used for creation, deletion and selection of hero
 *
 * @author Pril Wolf
 */
public class CreatorModel {

    private SessionFactory sessionFactory;
    private final Validator validator;
    private DefaultListModel characters;

    /**
     * Retrieve character list from database using a sessionFactory and store
     * them in a ListModel
     *
     * @param sessionFactory
     * @param validator
     */
    public CreatorModel(SessionFactory sessionFactory, Validator validator) {
        this.sessionFactory = sessionFactory;
        this.validator = validator;
        characters = new DefaultListModel();

        List<Creature> data = sessionFactory.fromSession(session -> {
            return session
                    .createSelectionQuery("from Creature", Creature.class)
                    .getResultList();
        });
        characters.addAll(data);
    }

    /**
     * Check if hero fields contain unauthorized value
     *
     * @param hero
     * @return true if hero is valid
     */
    public boolean validateHero(Creature hero) {
        Set<ConstraintViolation<Creature>> constraintViolations = validator.validate(hero);
        return constraintViolations.isEmpty();
    }

    public void create(String name, Roles role) throws InvalidHeroException {
        Creature newBorn = Creature.invoke(name, role);
        if (validateHero(newBorn) == true) {
            try {
                sessionFactory.inTransaction(session -> {
                    session.persist(newBorn);
                });
                characters.addElement(newBorn);
            } catch (Exception e) {
                throw new InvalidHeroException("duplicate name");
            }
        } else {
            throw new InvalidHeroException("name not valid");
        }

    }

    /**
     * Update the characters list from database
     */
    public void refresh() {
        characters.clear();
        List<Creature> data = sessionFactory.fromSession(session -> {
            return session
                    .createSelectionQuery("from Creature", Creature.class)
                    .getResultList();
        });
        characters.addAll(data);
    }

    /**
     * Delete a hero from database and refresh characters list
     *
     * @param hero
     */
    public void delete(Creature hero) {
        sessionFactory.inTransaction(session -> {
            session.remove(hero);
        });
        refresh();
    }

    /**
     *
     * @return
     */
    public DefaultListModel getCharacters() {
        return characters;
    }

}
