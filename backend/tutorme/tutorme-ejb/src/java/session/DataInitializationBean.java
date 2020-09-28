/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Area;
import entity.JobListing;
import entity.Offer;
import entity.Person;
import entity.Subject;
import entity.Timeslot;
import entity.Tutee;
import entity.Tutor;
import enumeration.StaffPositionEnum;
import enumeration.CitizenshipEnum;
import enumeration.DowEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import enumeration.ShiftEnum;
import exception.InvalidSubjectChoiceException;
import exception.PersonNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tay Z H Owen
 */
@Singleton
@Startup
@LocalBean
public class DataInitializationBean {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @EJB
    SubjectSessionLocal subjectSession;
    @EJB
    AreaSessionLocal areaSession;
    @EJB
    StaffSessionLocal staffSession;
    @EJB
    TimeslotSessionLocal timeslotSession;
    @EJB
    TutorSessionLocal tutorSession;
    @EJB
    TuteeSessionLocal tuteeSession;
    @EJB
    JobListingSessionLocal jobListingSession;
    @EJB
    OfferSessionLocal offerSession;
    @EJB
    RatingSessionLocal ratingSession;
    @EJB
    PersonSessionLocal persionSession;
    @EJB
    MessageSessionLocal messageSession;

    public DataInitializationBean() {
    }

    @PostConstruct
    public void postContruct() {
        initStaff();
        initTutors();
        initTutees();
        initArea();
        initTimeslots();
        initSubjects();
        initJobListings();
        initOffers();
        initRatings();
        initMessages();
    }

    private void initStaff() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();
        
        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        for (int i = 1; i <= 5; i++) {
            String email = "test_staff".concat(String.valueOf(i)).concat("@email.com");
            int mobileNum = randomNumberGenerator(80000001, 99999998);
            GenderEnum gender = mobileNum % 2 == 0 ? GenderEnum.MALE : GenderEnum.FEMALE;
            StaffPositionEnum adminPosition = mobileNum % 2 == 0 ? StaffPositionEnum.MANAGER : StaffPositionEnum.OPERATOR;
            Date dob = randomDateBetween(randomStartDate, randomEndDate);
            staffSession.createStaff("test", "admin", email, "password", String.valueOf(mobileNum), gender, randomStartDate, adminPosition);
        }
    }

    private void initTutors() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();
        
        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        for (int i = 1; i <= 10; i++) {
            String email = "test_tutor".concat(String.valueOf(i)).concat("@email.com");
            int mobileNum = randomNumberGenerator(80000001, 99999998);
            GenderEnum gender = mobileNum % 2 == 0 ? GenderEnum.MALE : GenderEnum.FEMALE;
            Date dob = randomDateBetween(randomStartDate, randomEndDate);
            tutorSession.createTutor("test", "tutor", email, "password", String.valueOf(mobileNum), gender, dob, QualificationEnum.BACHELOR, CitizenshipEnum.SINGAPORE, RaceEnum.CHINESE, "i am test tutor ".concat(String.valueOf(i)));
        }
    }

    private void initTutees() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();
        
        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        for (int i = 1; i <= 10; i++) {
            String email = "test_tutee".concat(String.valueOf(i)).concat("@email.com");
            int mobileNum = randomNumberGenerator(80000001, 99999998);
            GenderEnum gender = mobileNum % 2 == 0 ? GenderEnum.MALE : GenderEnum.FEMALE;
            Date dob = randomDateBetween(randomStartDate, randomEndDate);
            tuteeSession.createTutee("test", "tutee", email, "password", String.valueOf(mobileNum), gender, dob, "i am test tutee ".concat(String.valueOf(i)));
        }
    }

    private void initArea() {
        areaSession.createArea("NORTH");
        areaSession.createArea("NORTH-EAST");
        areaSession.createArea("WEST");
        areaSession.createArea("CENTRAL");
        areaSession.createArea("EAST");
    }

    private void initTimeslots() {
        for (DowEnum day : DowEnum.values()) {
            for (ShiftEnum shift : ShiftEnum.values()) {
                timeslotSession.createTimeslot(day, shift);
            }
        }

    }

    private void initSubjects() {
        subjectSession.createSubject("Primary 1", "English Language");
        subjectSession.createSubject("Primary 1", "Mother Tongue Language (MTL)");
        subjectSession.createSubject("Primary 1", "Mathematics");
        subjectSession.createSubject("Primary 1", "Science");
        subjectSession.createSubject("Primary 1", "Art");
        subjectSession.createSubject("Primary 1", "Music");
        subjectSession.createSubject("Primary 1", "Physical Education");
        subjectSession.createSubject("Primary 1", "Social Studies");
        subjectSession.createSubject("Primary 1", "Character and Citizenship Education");
        subjectSession.createSubject("Primary 2", "English Language");
        subjectSession.createSubject("Primary 2", "Mother Tongue Language (MTL)");
        subjectSession.createSubject("Primary 2", "Mathematics");
        subjectSession.createSubject("Primary 2", "Science");
        subjectSession.createSubject("Primary 2", "Art");
        subjectSession.createSubject("Primary 2", "Music");
        subjectSession.createSubject("Primary 2", "Physical Education");
        subjectSession.createSubject("Primary 2", "Social Studies");
        subjectSession.createSubject("Primary 2", "Character and Citizenship Education");
        subjectSession.createSubject("Primary 3", "English Language");
        subjectSession.createSubject("Primary 3", "Mother Tongue Language (MTL)");
        subjectSession.createSubject("Primary 3", "Mathematics");
        subjectSession.createSubject("Primary 3", "Science");
        subjectSession.createSubject("Primary 3", "Art");
        subjectSession.createSubject("Primary 3", "Music");
        subjectSession.createSubject("Primary 3", "Physical Education");
        subjectSession.createSubject("Primary 3", "Social Studies");
        subjectSession.createSubject("Primary 3", "Character and Citizenship Education");
        subjectSession.createSubject("Primary 4", "English Language");
        subjectSession.createSubject("Primary 4", "Mother Tongue Language (MTL)");
        subjectSession.createSubject("Primary 4", "Mathematics");
        subjectSession.createSubject("Primary 4", "Science");
        subjectSession.createSubject("Primary 4", "Art");
        subjectSession.createSubject("Primary 4", "Music");
        subjectSession.createSubject("Primary 4", "Physical Education");
        subjectSession.createSubject("Primary 4", "Social Studies");
        subjectSession.createSubject("Primary 4", "Character and Citizenship Education");
        subjectSession.createSubject("Primary 5", "English Language");
        subjectSession.createSubject("Primary 5", "Mother Tongue Language (MTL)");
        subjectSession.createSubject("Primary 5", "Mathematics");
        subjectSession.createSubject("Primary 5", "Science");
        subjectSession.createSubject("Primary 5", "Art");
        subjectSession.createSubject("Primary 5", "Music");
        subjectSession.createSubject("Primary 5", "Physical Education");
        subjectSession.createSubject("Primary 5", "Social Studies");
        subjectSession.createSubject("Primary 5", "Character and Citizenship Education");
        subjectSession.createSubject("Primary 6", "English Language");
        subjectSession.createSubject("Primary 6", "Mother Tongue Language (MTL)");
        subjectSession.createSubject("Primary 6", "Mathematics");
        subjectSession.createSubject("Primary 6", "Science");
        subjectSession.createSubject("Primary 6", "Art");
        subjectSession.createSubject("Primary 6", "Music");
        subjectSession.createSubject("Primary 6", "Physical Education");
        subjectSession.createSubject("Primary 6", "Social Studies");
        subjectSession.createSubject("Primary 6", "Character and Citizenship Education");

        subjectSession.createSubject("Secondary 1 (O)", "English Language");
        subjectSession.createSubject("Secondary 1 (O)", "Mother Tongue Languages");
        subjectSession.createSubject("Secondary 1 (O)", "Mathematics");
        subjectSession.createSubject("Secondary 1 (O)", "Physics");
        subjectSession.createSubject("Secondary 1 (O)", "Biology");
        subjectSession.createSubject("Secondary 1 (O)", "Chemistry");
        subjectSession.createSubject("Secondary 1 (O)", "Character and Citizenship Education");
        subjectSession.createSubject("Secondary 1 (O)", "Geography");
        subjectSession.createSubject("Secondary 1 (O)", "History");
        subjectSession.createSubject("Secondary 1 (O)", "Literature");
        subjectSession.createSubject("Secondary 1 (O)", "Design and Technology");
        subjectSession.createSubject("Secondary 1 (O)", "Food and Consumer Education");
        subjectSession.createSubject("Secondary 1 (O)", "Art");
        subjectSession.createSubject("Secondary 1 (O)", "Music");
        subjectSession.createSubject("Secondary 2 (O)", "English Language");
        subjectSession.createSubject("Secondary 2 (O)", "Mother Tongue Languages");
        subjectSession.createSubject("Secondary 2 (O)", "Mathematics");
        subjectSession.createSubject("Secondary 2 (O)", "Physics");
        subjectSession.createSubject("Secondary 2 (O)", "Biology");
        subjectSession.createSubject("Secondary 2 (O)", "Chemistry");
        subjectSession.createSubject("Secondary 2 (O)", "Character and Citizenship Education");
        subjectSession.createSubject("Secondary 2 (O)", "Geography");
        subjectSession.createSubject("Secondary 2 (O)", "History");
        subjectSession.createSubject("Secondary 2 (O)", "Literature");
        subjectSession.createSubject("Secondary 2 (O)", "Design and Technology");
        subjectSession.createSubject("Secondary 2 (O)", "Food and Consumer Education");
        subjectSession.createSubject("Secondary 2 (O)", "Art");
        subjectSession.createSubject("Secondary 2 (O)", "Music");
        subjectSession.createSubject("Secondary 3 (O)", "English Language");
        subjectSession.createSubject("Secondary 3 (O)", "Mother Tongue Languages");
        subjectSession.createSubject("Secondary 3 (O)", "Elementary Mathematics");
        subjectSession.createSubject("Secondary 3 (O)", "Additional Mathematics");
        subjectSession.createSubject("Secondary 3 (O)", "Pure Physics");
        subjectSession.createSubject("Secondary 3 (O)", "Combined Physics");
        subjectSession.createSubject("Secondary 3 (O)", "Pure Biology");
        subjectSession.createSubject("Secondary 3 (O)", "Combined Biology");
        subjectSession.createSubject("Secondary 3 (O)", "Pure Chemistry");
        subjectSession.createSubject("Secondary 3 (O)", "Combined Chemsitry");
        subjectSession.createSubject("Secondary 3 (O)", "Pure Geography");
        subjectSession.createSubject("Secondary 3 (O)", "Combined Geography");
        subjectSession.createSubject("Secondary 3 (O)", "Pure History");
        subjectSession.createSubject("Secondary 3 (O)", "Combined History");
        subjectSession.createSubject("Secondary 3 (O)", "Pure Literature");
        subjectSession.createSubject("Secondary 3 (O)", "Combined Literature");
        subjectSession.createSubject("Secondary 3 (O)", "Character and Citizenship Education");
        subjectSession.createSubject("Secondary 3 (O)", "Design and Technology");
        subjectSession.createSubject("Secondary 3 (O)", "Food and Consumer Education");
        subjectSession.createSubject("Secondary 3 (O)", "Art");
        subjectSession.createSubject("Secondary 3 (O)", "Music");
        subjectSession.createSubject("Secondary 4 (O)", "English Language");
        subjectSession.createSubject("Secondary 4 (O)", "Mother Tongue Languages");
        subjectSession.createSubject("Secondary 4 (O)", "Elementary Mathematics");
        subjectSession.createSubject("Secondary 4 (O)", "Additional Mathematics");
        subjectSession.createSubject("Secondary 4 (O)", "Pure Physics");
        subjectSession.createSubject("Secondary 4 (O)", "Combined Physics");
        subjectSession.createSubject("Secondary 4 (O)", "Pure Biology");
        subjectSession.createSubject("Secondary 4 (O)", "Combined Biology");
        subjectSession.createSubject("Secondary 4 (O)", "Pure Chemistry");
        subjectSession.createSubject("Secondary 4 (O)", "Combined Chemsitry");
        subjectSession.createSubject("Secondary 4 (O)", "Pure Geography");
        subjectSession.createSubject("Secondary 4 (O)", "Combined Geography");
        subjectSession.createSubject("Secondary 4 (O)", "Pure History");
        subjectSession.createSubject("Secondary 4 (O)", "Combined History");
        subjectSession.createSubject("Secondary 4 (O)", "Pure Literature");
        subjectSession.createSubject("Secondary 4 (O)", "Combined Literature");
        subjectSession.createSubject("Secondary 4 (O)", "Character and Citizenship Education");
        subjectSession.createSubject("Secondary 4 (O)", "Design and Technology");
        subjectSession.createSubject("Secondary 4 (O)", "Food and Consumer Education");
        subjectSession.createSubject("Secondary 4 (O)", "Art");
        subjectSession.createSubject("Secondary 4 (O)", "Music");

        subjectSession.createSubject("H1", "China Studies in English");
        subjectSession.createSubject("H1", "General Paper");
        subjectSession.createSubject("H1", "Project Work");
        subjectSession.createSubject("H1", "Geography");
        subjectSession.createSubject("H1", "History");
        subjectSession.createSubject("H1", "Economics");
        subjectSession.createSubject("H1", "Bengali");
        subjectSession.createSubject("H1", "Gujarati");
        subjectSession.createSubject("H1", "Hindi");
        subjectSession.createSubject("H1", "French");
        subjectSession.createSubject("H1", "Literature in English");
        subjectSession.createSubject("H1", "German");
        subjectSession.createSubject("H1", "Japanese");
        subjectSession.createSubject("H1", "Panjabi");
        subjectSession.createSubject("H1", "Urdu");
        subjectSession.createSubject("H1", "Mathematics");
        subjectSession.createSubject("H1", "Physics");
        subjectSession.createSubject("H1", "Chemistry");
        subjectSession.createSubject("H1", "Biology");
        subjectSession.createSubject("H1", "Art");
        subjectSession.createSubject("H1", "Chinese B");
        subjectSession.createSubject("H1", "Chinese Language ");
        subjectSession.createSubject("H1", "General Studies in Chinese");
        subjectSession.createSubject("H1", "Chinese Language");
        subjectSession.createSubject("H1", "Malay B");
        subjectSession.createSubject("H1", "Malay Language");
        subjectSession.createSubject("H1", "Tamil B");
        subjectSession.createSubject("H1", "Tamil Language ");
        subjectSession.createSubject("H2", "English Language and Linguistics");
        subjectSession.createSubject("H2", "Literature in English");
        subjectSession.createSubject("H2", "Theatre Studies and Drama");
        subjectSession.createSubject("H2", "Computing ");
        subjectSession.createSubject("H2", "Translation (Chinese)");
        subjectSession.createSubject("H2", "Spanish");
        subjectSession.createSubject("H2", "Management of BusinesubjectSession ");
        subjectSession.createSubject("H2", "Principles of Accounting");
        subjectSession.createSubject("H2", "China Studies in English");
        subjectSession.createSubject("H2", "Further Mathematics");
        subjectSession.createSubject("H2", "Chemistry");
        subjectSession.createSubject("H2", "French");
        subjectSession.createSubject("H2", "German");
        subjectSession.createSubject("H2", "Japanese");
        subjectSession.createSubject("H2", "Biology");
        subjectSession.createSubject("H2", "Physics");
        subjectSession.createSubject("H2", "Art");
        subjectSession.createSubject("H2", "Geography");
        subjectSession.createSubject("H2", "History");
        subjectSession.createSubject("H2", "Music");
        subjectSession.createSubject("H2", "Economics");
        subjectSession.createSubject("H2", "Mathematics");
        subjectSession.createSubject("H2", "Knowledge and Inquiry");
        subjectSession.createSubject("H2", "Chinese Language & Literature");
        subjectSession.createSubject("H2", "China Studies in Chinese");
        subjectSession.createSubject("H2", "Malay Language & Literature");
        subjectSession.createSubject("H2", "Tamil Language & Literature");
        subjectSession.createSubject("H3", "Literature in English");
        subjectSession.createSubject("H3", "Economics");
        subjectSession.createSubject("H3", "Chemistry");
        subjectSession.createSubject("H3", "Physics");
        subjectSession.createSubject("H3", "Biology");
        subjectSession.createSubject("H3", "Art");
        subjectSession.createSubject("H3", "Music");
        subjectSession.createSubject("H3", "Mathematics");
        subjectSession.createSubject("H3", "Geography");
        subjectSession.createSubject("H3", "History");
        subjectSession.createSubject("H3", "Chinese Language and Literature");
        subjectSession.createSubject("H3", "Malay Language and Literature");
        subjectSession.createSubject("H3", "Tamil Language and Literature");
    }

    private void initJobListings() {
        List<Tutor> tutors = tutorSession.retrieveAllTutors();
        List<Subject> subjects = subjectSession.retrieveAllSubjects();
        List<Timeslot> timeslots = timeslotSession.retrieveAllTimeslots();
        List<Area> areas = areaSession.retrieveAllAreas();
        for (int i = 0; i < 25; i++) {
            int randomTutorIndex = randomNumberGenerator(0, tutors.size());
            Tutor tutor;
            tutor = tutors.get(randomTutorIndex);

            int numSubjects = randomNumberGenerator(1, 6);
            List<Subject> jlSubjects = new ArrayList();
            for (int j = 0; j < numSubjects; j++) {
                int randomSubjectIndex = randomNumberGenerator(0, subjects.size());
                jlSubjects.add(subjects.get(randomSubjectIndex));
            }

            int numTimeslots = randomNumberGenerator(1, 6);
            List<Timeslot> jlTimeslots = new ArrayList();
            for (int j = 0; j < numTimeslots; j++) {
                int randomTimeslotIndex = randomNumberGenerator(0, timeslots.size());
                jlTimeslots.add(timeslots.get(randomTimeslotIndex));
            }

            int numAreas = randomNumberGenerator(1, 4);
            List<Area> jlAreas = new ArrayList();
            for (int j = 0; j < numTimeslots; j++) {
                int randomAreaIndex = randomNumberGenerator(0, areas.size());
                jlAreas.add(areas.get(randomAreaIndex));
            }

            double rates = (double) randomNumberGenerator(20, 100);
            jobListingSession.createJobListing(tutor, jlSubjects, rates, jlTimeslots, jlAreas, "i love to teach");
        }
    }

    private void initOffers() {
        List<JobListing> jobListings = jobListingSession.retrieveAllJobListings();
        List<Tutee> tutees = tuteeSession.retrieveAllTutees();
        for (int i = 0; i < 100; i++) {
            int randomJobListingIndex = randomNumberGenerator(0, jobListings.size());
            JobListing jobListing = jobListings.get(randomJobListingIndex);
            int randomTuteeIndex = randomNumberGenerator(0, tutees.size());
            Tutee tutee = tutees.get(randomTuteeIndex);

            double minRate = jobListing.getHourlyRates() - 10;
            double maxRate = jobListing.getHourlyRates() + 10;
            double rates = (double) randomNumberGenerator((int) minRate, (int) maxRate);

            List<Subject> subjects = jobListing.getSubjects();
            int randomSubjectIndex = randomNumberGenerator(0, subjects.size());
            Subject chosenSubject = subjects.get(randomSubjectIndex);

            Date startDate = new Date();
            try {
                offerSession.createOffer(rates, startDate, tutee, chosenSubject, jobListing, "I love learning");
            } catch (InvalidSubjectChoiceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void initRatings() {
        List<Offer> offers = offerSession.retrieveAllOffers();
        for (int i=0; i<50; i++) {
            int randomOfferIndex = randomNumberGenerator(1, offers.size());
            Offer offer = offers.get(randomOfferIndex);
            double value = (double) randomNumberGenerator(1,5);
            ratingSession.createRating(value, "the tutor was great/bad", offer);
        }
    }
    
    private void initMessages() {
        List<Person> persons = persionSession.retrieveAllPersons();
        for (int i=0; i<100; i++) {
            int randomSenderIndex = randomNumberGenerator(0, persons.size());
            int randomReceiverIndex = randomNumberGenerator(0, persons.size());
            Person sender = persons.get(randomSenderIndex);
            Person receiver = persons.get(randomReceiverIndex);
            try {
                messageSession.createMessage(sender.getPersonId(), receiver.getPersonId(), "test message from Person_"+sender.getPersonId()+" to Person_"+receiver.getPersonId());
            } catch (PersonNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static Date randomDateBetween(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);
        return new Date(randomMillisSinceEpoch);
    }

    public static int randomNumberGenerator(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}