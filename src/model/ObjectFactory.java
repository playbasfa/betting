
package model;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the odds_it package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: odds_it
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sport }
     * 
     */
    public Sport createSport() {
        return new Sport();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link Choice }
     * 
     */
    public Choice createChoice() {
        return new Choice();
    }

    /**
     * Create an instance of {@link Sports }
     * 
     */
    public Sports createSports() {
        return new Sports();
    }

    /**
     * Create an instance of {@link Match }
     * 
     */
    public Match createMatch() {
        return new Match();
    }

    /**
     * Create an instance of {@link Bets }
     * 
     */
    public Bets createBets() {
        return new Bets();
    }

    /**
     * Create an instance of {@link Bet }
     * 
     */
    public Bet createBet() {
        return new Bet();
    }

}
