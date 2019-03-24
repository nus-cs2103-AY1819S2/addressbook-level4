package seedu.travel.testutil;

import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_CLEMENTI;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_DG;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_COUNTRY_CODE_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CLEMENTI;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DG;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_CLEMENTI;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_DG;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_CLEMENTI;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_DG;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_MRT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.Place;

/**
 * A utility class containing a list of {@code Place} objects to be used in tests.
 */
public class TypicalPlaces {

    public static final Place ALICE = new PlaceBuilder()
            .withName("VivoCity")
            .withCountryCode("SGP")
            .withAddress("1 Harbourfront Walk, Singapore 098585")
            .withDescription("Expansive, modern shopping center hosting a wide range of retailers, "
                    + "restaurants & theaters.")
            .withRating("1")
            .withTags("shoppingMall")
            .build();
    public static final Place BENSON = new PlaceBuilder()
            .withName("Changi Airport Singapore")
            .withCountryCode("SGP")
            .withAddress("Airport Boulevard")
            .withDescription("Passenger & cargo hub with 4 modern terminals plus buses & trains to the city center.")
            .withRating("5")
            .withTags("airport", "shoppingMall")
            .build();
    public static final Place CARL = new PlaceBuilder()
            .withName("Universal Studios Singapore")
            .withCountryCode("SGP")
            .withRating("4")
            .withDescription("Movie amusement centre with sets & rides on themes from "
                    + "Hollywood to sci-fi, plus live entertainment.")
            .withAddress("8 Sentosa Gateway, 0982693")
            .withTags("amusementPark", "recreation")
            .build();
    public static final Place DANIEL = new PlaceBuilder()
            .withName("Yanaka Cemetery Park")
            .withCountryCode("JPN")
            .withRating("3")
            .withDescription("Hilly, cherry tree–planted cemetery with graves"
                    + " of renowned artists & actors & a 5-story pagoda.")
            .withAddress("7-chōme-5-24 Yanaka, Taito City, Tōkyō-to 110-0001, Japan")
            .withTags("cemetery", "placeOfInterest")
            .build();
    public static final Place ELLE = new PlaceBuilder()
            .withName("National University of Singapore")
            .withCountryCode("SGP")
            .withRating("4")
            .withDescription("The National University of Singapore is an autonomous research university in Singapore.")
            .withAddress("21 Lower Kent Ridge Rd, Singapore 119077")
            .withTags("school")
            .build();
    public static final Place FIONA = new PlaceBuilder()
            .withName("Singapore Zoo")
            .withCountryCode("SGP")
            .withRating("3")
            .withDescription("Rainforest zoo with tram rides, trails & viewing platforms "
                    + "to see wildlife habitats & exhibits.")
            .withAddress("80 Mandai Lake Rd, Singapore 729826")
            .withTags("zoo", "animals")
            .build();
    public static final Place GEORGE = new PlaceBuilder()
            .withName("Buddha Tooth Relic Temple")
            .withCountryCode("SGP")
            .withRating("4")
            .withDescription("Tang dynasty–style temple housing religious relics, "
                    + "with ornate rooms & a tranquil rooftop garden.")
            .withAddress("288 South Bridge Rd, Singapore 058840")
            .withTags("temple", "heritageSite")
            .build();

    // Manually added
    public static final Place HOON = new PlaceBuilder()
            .withName("Changi General Hospital")
            .withCountryCode("SGP")
            .withRating("2")
            .withDescription("Singapore's first purpose-built general hospital to"
                    + " serve communities in the east and north-east regions.")
            .withAddress("2 Simei Street 3, Singapore 529889")
            .build();

    public static final Place IDA = new PlaceBuilder()
            .withName("Singapore Botanic Gardens")
            .withCountryCode("SGP")
            .withRating("5")
            .withDescription("Botanical gardens with sculptures, a swan lake"
                    + " & significant collection of tropical trees.")
            .withAddress("1 Cluny Rd, Singapore 259569")
            .build();

    // Manually added - Place's details found in {@code CommandTestUtil}
    public static final Place AMK = new PlaceBuilder()
            .withName(VALID_NAME_AMK)
            .withCountryCode(VALID_COUNTRY_CODE_AMK)
            .withRating(VALID_RATING_AMK)
            .withDescription(VALID_DESCRIPTION_AMK)
            .withAddress(VALID_ADDRESS_AMK)
            .withTags(VALID_TAG_MRT)
            .build();
    public static final Place BEDOK = new PlaceBuilder()
            .withName(VALID_NAME_BEDOK)
            .withCountryCode(VALID_COUNTRY_CODE_AMK)
            .withRating(VALID_RATING_BEDOK)
            .withDescription(VALID_DESCRIPTION_BEDOK)
            .withAddress(VALID_ADDRESS_BEDOK)
            .withTags(VALID_TAG_EWL, VALID_TAG_MRT)
            .build();
    public static final Place CLEMENTI = new PlaceBuilder()
            .withName(VALID_NAME_CLEMENTI)
            .withCountryCode(VALID_COUNTRY_CODE_AMK)
            .withRating(VALID_RATING_CLEMENTI)
            .withDescription(VALID_DESCRIPTION_CLEMENTI)
            .withAddress(VALID_ADDRESS_CLEMENTI)
            .withTags(VALID_TAG_EWL)
            .build();
    public static final Place DG = new PlaceBuilder()
            .withName(VALID_NAME_DG)
            .withCountryCode(VALID_COUNTRY_CODE_AMK)
            .withRating(VALID_RATING_DG)
            .withDescription(VALID_DESCRIPTION_DG)
            .withAddress(VALID_ADDRESS_DG)
            .build();

    public static final String KEYWORD_MATCHING_SINGAPORE = "Singapore"; // A keyword that matches SINGAPORE

    public static final String KEYWORD_MATCHING_SHOPPING_MALL = "shoppingMall"; // A keyword that matches shoppingMall

    public static final String KEYWORD_MATCHING_FOUR = "4"; // A keyword that matches 4

    public static final String KEYWORD_MATCHING_SGP = "SGP"; // A keyword that matches the country code SGP

    private TypicalPlaces() {} // prevents instantiation

    /**
     * Returns an {@code TravelBuddy} with all the typical places.
     */
    public static TravelBuddy getTypicalTravelBuddy() {
        TravelBuddy ab = new TravelBuddy();
        for (Place place : getTypicalPlaces()) {
            ab.addPlace(place);
        }
        return ab;
    }

    public static List<Place> getTypicalPlaces() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
