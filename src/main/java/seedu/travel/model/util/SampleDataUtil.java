package seedu.travel.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;

/**
 * Contains utility methods for populating {@code TravelBuddy} with sample data.
 */
public class SampleDataUtil {
    public static Place[] getSamplePlaces() {
        return new Place[] {
            new Place(new Name("Jigokudani Monkey Park"),
                    new CountryCode("JPN"),
                    new DateVisited("21/12/2012"),
                    new Rating("3"),
                    new Description("The name Jigokudani (meaning “Hell’s Valley”), is due to steam and boiling "
                            + "water that bubbles out the frozen ground."),
                    new Address("381-0401 Nagano, Shimotakai District, Yamanochi, Hirao"),
                    getTagSet("animals", "hiking", "nature")),
            new Place(new Name("Japanese Cemetery Park"),
                    new CountryCode("SGP"),
                    new DateVisited("11/09/2017"),
                    new Rating("3"),
                    new Description("The Japanese Cemetery Park serves as the burial ground for Japanese soldiers "
                            + "and civilians who lived in the early 20th century."),
                    new Address("22 Chuan Hoe Ave, Singapore 549854"),
                    getTagSet("cemetery", "placeOfInterest", "historic")),
            new Place(new Name("Universal Studios Singapore"),
                    new CountryCode("SGP"),
                    new DateVisited("23/12/2017"),
                    new Rating("4"),
                    new Description("Movie amusement park with sets & rides on themes from Hollywood to sci-fi, "
                            + "plus live entertainment."),
                    new Address("8 Sentosa Gateway, 0982693"),
                    getTagSet("amusementPark", "recreation")),
            new Place(new Name("Changi Airport Singapore"),
                    new CountryCode("SGP"),
                    new DateVisited("01/01/2019"),
                    new Rating("5"),
                    new Description("Passenger & cargo hub with 4 modern terminals plus buses & trains to the "
                            + "city center."),
                    new Address("Airport Boulevard"),
                    getTagSet("airport", "shoppingMall")),
            new Place(new Name("Singapore Zoo"),
                    new CountryCode("SGP"),
                    new DateVisited("09/08/2018"),
                    new Rating("3"),
                    new Description("Rainforest zoo with tram rides, trails & viewing platforms to see wildlife "
                            + "habitats & exhibits."),
                    new Address("80 Mandai Lake Rd, Singapore 729826"),
                    getTagSet("zoo", "animals")),
            new Place(new Name("Botanic Gardens"),
                    new CountryCode("SGP"),
                    new DateVisited("1/1/2017"),
                    new Rating("4"),
                    new Description("UNESCO World Heritage Site."),
                    new Address("1 Cluny Rd, Singapore 259569"),
                    getTagSet("nature")),
            new Place(new Name("Hong Kong Disneyland"),
                    new CountryCode("HKG"),
                    new DateVisited("21/7/2017"),
                    new Rating("4"),
                    new Description("It is the largest theme park in Hong Kong."),
                    new Address("Hong Kong Disneyland, Lantau Island, Outlying Islands"),
                    getTagSet("themePark")),
            new Place(new Name("Platinum Fashion Mall"),
                    new CountryCode("THA"),
                    new DateVisited("10/8/2018"),
                    new Rating("3"),
                    new Description("Specializes in fashion clothes and accessories retail and wholesale."),
                    new Address("222 Phetchaburi Rd, Khwaeng Thanon Phetchaburi, Khet Ratchathewi, Krung Thep Maha "
                            + "Nakhon 10400, Thailand"),
                    getTagSet("shopping")),
            new Place(new Name("National Palace Museum"),
                    new CountryCode("TWN"),
                    new DateVisited("12/2/2019"),
                    new Rating("2"),
                    new Description("Has a permanent collection of nearly 700,000 pieces of ancient Chinese imperial "
                            + "artifacts and artworks."),
                    new Address("No. 221, Sec 2, Zhi Shan Rd, Shilin District, Taipei City, Taiwan 111"),
                    getTagSet("museum", "historic", "culture")),
            new Place(new Name("Great Wall of China"),
                    new CountryCode("CHN"),
                    new DateVisited("16/11/2013"),
                    new Rating("3"),
                    new Description("It was built to protect the Chinese states and empires against the raids and "
                            + "invasions of the various nomadic groups."),
                    new Address("Huairou, China"),
                    getTagSet("landmark")),
            new Place(new Name("Eiffel Tower"),
                    new CountryCode("FRA"),
                    new DateVisited("14/2/2013"),
                    new Rating("5"),
                    new Description("It is named after the engineer Gustave Eiffel, whose company designed and built"
                            + " the tower."),
                    new Address("Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France"),
                    getTagSet("zoo", "animals")),
            new Place(new Name("Mount Fuji"),
                    new CountryCode("JPN"),
                    new DateVisited("15/1/2011"),
                    new Rating("4"),
                    new Description("It is the 2nd-highest peak of an island (volcanic) in Asia."),
                    new Address("Kitayama, Fujinomiya, Shizuoka 418-0112, Japan"),
                    getTagSet("nature", "hiking")),
            new Place(new Name("Phi Phi Islands"),
                    new CountryCode("THA"),
                    new DateVisited("13/10/2014"),
                    new Rating("2"),
                    new Description("An island group in Thailand, between the large island of Phuket and the "
                            + "west Strait of Malacca coast of the mainland."),
                    new Address("32/1 Moo 7, Ao Nang, Muang, Krabi"),
                    getTagSet("nature", "island")),
            new Place(new Name("Khlong Lad Mayom Floating Market"),
                    new CountryCode("THA"),
                    new DateVisited("20/10/2014"),
                    new Rating("2"),
                    new Description("Experience rural Thai culture and explore the canal waterways outside the "
                            + "capital."),
                    new Address("30/1 Bang Ramat Rd, Bang Ramat, Taling Chan"),
                    getTagSet("culture")),
            new Place(new Name("Ko Samui"),
                    new CountryCode("THA"),
                    new DateVisited("5/10/2014"),
                    new Rating("1"),
                    new Description("Thailand’s second largest island."),
                    new Address("9/123 Moo 5, Tambon Bophut Koh Samui, Surat Thani"),
                    getTagSet("nature", "island")),
            new Place(new Name("Marina Bay Sands"),
                    new CountryCode("SGP"),
                    new DateVisited("15/1/2011"),
                    new Rating("4"),
                    new Description("It is the 2nd-highest peak of an island (volcanic) in Asia."),
                    new Address("10 Bayfront Avenue"),
                    getTagSet("luxury", "hotel", "landmark")),
            new Place(new Name("Hiroshima Peace Memorial"),
                    new CountryCode("JPN"),
                    new DateVisited("1/5/2016"),
                    new Rating("2"),
                    new Description("The memorial features Genbaku Dome, the only building left standing in the "
                            + "vicinity after the bomb dropped."),
                    new Address("1-2 Nakajima-cho, Naka-ku, Hiroshima-shi 730-0811"),
                    getTagSet("historic", "landmark")),
            new Place(new Name("Louvre"),
                    new CountryCode("FRA"),
                    new DateVisited("9/5/2018"),
                    new Rating("4"),
                    new Description("The world's largest art museum."),
                    new Address("Musée du Louvre, 75001 Paris, France"),
                    getTagSet("museum", "landmark", "art")),
            new Place(new Name("Arc de Triomphe"),
                    new CountryCode("FRA"),
                    new DateVisited("31/1/2019"),
                    new Rating("5"),
                    new Description("Formerly named Place de l'Étoile — the étoile or \"star\" of the juncture"
                            + " formed by its twelve radiating avenues."),
                    new Address("Place de l'Étoile, 75008 Paris"),
                    getTagSet("landmark"))
        };
    }

    public static ReadOnlyTravelBuddy getSampleTravelBuddy() {
        TravelBuddy sampleAb = new TravelBuddy();
        for (Place samplePlace : getSamplePlaces()) {
            sampleAb.addPlace(samplePlace);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
