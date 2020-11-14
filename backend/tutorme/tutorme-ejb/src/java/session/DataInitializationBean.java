package session;

import entity.JobListing;
import entity.Message;
import entity.Offer;
import entity.Rating;
import entity.Staff;
import entity.Subject;
import entity.Tutee;
import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.StaffPositionEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.OfferNotFoundException;
import exception.OfferStatusException;
import exception.PersonNotFoundException;
import exception.RegistrationFailException;
import exception.StaffNotFoundException;
import exception.TuteeNotFoundException;
import exception.TutorNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    StaffSessionLocal staffSession;
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
        initSubjects();
        initTutors2();
        initTutees2();
        initJobListings2();
        initOffers2();
        initRatings2();
        initStaff();
        initMessages();
        System.out.println("I HAVE DEPLOYED EVERYTHING");
    }

    private void initTutors2() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();

        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        Tutor tutor1 = tutorSession.createTutorInit("Hsiang", "Hui", "hsianghui@nus.edu.sg", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), QualificationEnum.BACHELOR, CitizenshipEnum.SINGAPORE, RaceEnum.CHINESE, "I have 6 years of tutoring kids and very experienced. Please pm me for more info or to negotiate my rates");
        em.flush();
        Tutor tutor2 = tutorSession.createTutorInit("Enelton", "Satria", "EneltonSatria@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.FEMALE, randomDateBetween(randomStartDate, randomEndDate), QualificationEnum.DIPLOMA, CitizenshipEnum.SINGAPORE, RaceEnum.INDIAN, "Please chat with me!");
        em.flush();
        Tutor tutor3 = tutorSession.createTutorInit("Keng", "Thong", "KengThong@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), QualificationEnum.DOCTORATE, CitizenshipEnum.SINGAPORE, RaceEnum.CHINESE, "I am super experienced with over 100 students");
        em.flush();
        Tutor tutor4 = tutorSession.createTutorInit("Brandon", "Ng", "BrandonNg@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.FEMALE, randomDateBetween(randomStartDate, randomEndDate), QualificationEnum.NLEVELS, CitizenshipEnum.SINGAPORE, RaceEnum.INDIAN, "Get cheaper quotes when you chat with me");
        em.flush();
        Tutor tutor5 = tutorSession.createTutorInit("Soh", "Yibin", "SohYibin@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), QualificationEnum.PSLE, CitizenshipEnum.SINGAPORE, RaceEnum.CHINESE, "You have to pay for my grab expenses");
        em.flush();
    }

    private void initTutees2() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();
        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        Tutee tutee1 = tuteeSession.createTuteeInit("Chen", "Peizhi", "chen@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.FEMALE, randomDateBetween(randomStartDate, randomEndDate), "Need to learn maths v badly!!");
        em.flush();
        Tutee tutee2 = tuteeSession.createTuteeInit("Wei", "Shaoqing", "wei@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), "Failed my maths");
        em.flush();
        Tutee tutee3 = tuteeSession.createTuteeInit("Liang", "Bolu", "liang@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.FEMALE, randomDateBetween(randomStartDate, randomEndDate), "Need a good tutor");
        em.flush();
        Tutee tutee4 = tuteeSession.createTuteeInit("Tan", "Lulu", "tan@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), "Location and availability is important");
        em.flush();
        Tutee tutee5 = tuteeSession.createTuteeInit("Lan", "Meili", "lan@gmail.com", "password", String.valueOf(randomNumberGenerator(80000001, 99999998)), GenderEnum.FEMALE, randomDateBetween(randomStartDate, randomEndDate), "Help me with my studies");
        em.flush();

    }

    private void initJobListings2() {
        JobListing jobListing;

        try {
            Subject mathsPrimary1 = subjectSession.retrieveSubjectByNameAndLevel("Mathematics", "Primary 1");
            Subject mathsPrimary2 = subjectSession.retrieveSubjectByNameAndLevel("Mathematics", "Primary 2");
            Subject mathsPrimary3 = subjectSession.retrieveSubjectByNameAndLevel("Mathematics", "Primary 3");
            Subject mathsPrimary4 = subjectSession.retrieveSubjectByNameAndLevel("Mathematics", "Primary 4");
            Subject mathsPrimary5 = subjectSession.retrieveSubjectByNameAndLevel("Mathematics", "Primary 5");
            Subject mathsPrimary6 = subjectSession.retrieveSubjectByNameAndLevel("Mathematics", "Primary 6");
            Subject sciencePrimary1 = subjectSession.retrieveSubjectByNameAndLevel("Science", "Primary 1");
            Subject sciencePrimary2 = subjectSession.retrieveSubjectByNameAndLevel("Science", "Primary 2");
            Subject sciencePrimary3 = subjectSession.retrieveSubjectByNameAndLevel("Science", "Primary 3");
            Subject sciencePrimary4 = subjectSession.retrieveSubjectByNameAndLevel("Science", "Primary 4");
            Subject sciencePrimary5 = subjectSession.retrieveSubjectByNameAndLevel("Science", "Primary 5");
            Subject sciencePrimary6 = subjectSession.retrieveSubjectByNameAndLevel("Science", "Primary 6");

            List<Long> combo1 = new ArrayList<>();
            combo1.add(mathsPrimary4.getSubjectId());
            combo1.add(mathsPrimary1.getSubjectId());
            combo1.add(mathsPrimary2.getSubjectId());
            combo1.add(mathsPrimary3.getSubjectId());

            List<Long> combo2 = new ArrayList<>();
            combo2.add(mathsPrimary2.getSubjectId());
            combo2.add(mathsPrimary3.getSubjectId());
            combo2.add(mathsPrimary4.getSubjectId());
            combo2.add(mathsPrimary5.getSubjectId());

            List<Long> combo3 = new ArrayList<>();
            combo3.add(mathsPrimary6.getSubjectId());
            combo3.add(mathsPrimary3.getSubjectId());
            combo3.add(mathsPrimary4.getSubjectId());
            combo3.add(mathsPrimary5.getSubjectId());

            List<Long> combo4 = new ArrayList<>();
            combo4.add(sciencePrimary5.getSubjectId());
            combo4.add(sciencePrimary6.getSubjectId());
            combo4.add(sciencePrimary3.getSubjectId());
            combo4.add(sciencePrimary4.getSubjectId());

            List<Long> combo5 = new ArrayList<>();
            combo5.add(sciencePrimary1.getSubjectId());
            combo5.add(sciencePrimary2.getSubjectId());
            combo5.add(sciencePrimary3.getSubjectId());
            combo5.add(sciencePrimary4.getSubjectId());

            List<Long> combo6 = new ArrayList<>();
            combo6.add(sciencePrimary1.getSubjectId());
            combo6.add(sciencePrimary3.getSubjectId());
            combo6.add(sciencePrimary5.getSubjectId());
            combo6.add(sciencePrimary6.getSubjectId());

            //new JobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc)
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("hsianghui@nus.edu.sg").getPersonId(), combo1, 45.0, "Anyday except weekends", "North", "Math P1 to P4", "Negotiable prices");
            em.flush();
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("hsianghui@nus.edu.sg").getPersonId(), combo4, 35.0, "Anyday except weekends", "North", "Science P3 to P6", "Negotiable prices");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("EneltonSatria@gmail.com").getPersonId(), combo2, 15.0, "Monday Only", "South", "Math P2 to P5", "Fixed prices");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("EneltonSatria@gmail.com").getPersonId(), combo5, 65.0, "Monday Only", "South", "Science P1 to P4", "Fixed prices");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("KengThong@gmail.com").getPersonId(), combo3, 50.0, "Weekdays Only", "East", "Science P3 to P6", "PM me fast");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("KengThong@gmail.com").getPersonId(), combo6, 70.0, "Weekdays Only", "East", "Science P1,3,5,6", "PM me fast");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("BrandonNg@gmail.com").getPersonId(), combo1, 25.0, "Weekend Only", "West", "Math P1 to P4", "Flex prices");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("BrandonNg@gmail.com").getPersonId(), combo4, 35.0, "Weekend Only", "West", "Science P3 to P6", "Flex prices");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("SohYibin@gmail.com").getPersonId(), combo2, 20.0, "Weekday Night Only", "Anywhere", "Math P2 to P5", "Negotiable prices for friendly customers");
            jobListing = jobListingSession.createJobListingInit(tutorSession.retrieveTutorByEmail("SohYibin@gmail.com").getPersonId(), combo5, 35.0, "Weekday Night Only", "Anywhere", "Science P1 to P4", "Negotiable prices for friendly customers");
            em.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initOffers2() {
        Offer offer;
        try {
            //createOffer(Double offeredRate, Date startDate, Long tuteeId, Long subjectId, Long jobListingId, int numSessions, double numHoursPerSession, String additionalNote)
            offer = offerSession.createOffer(35.0, new Date(), tuteeSession.retrieveTuteeByEmail("chen@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(1L).getSubjects().get(0).getSubjectId(), 1L, 1, 1, "I love learning");
            offer = offerSession.createOffer(50.0, new Date(), tuteeSession.retrieveTuteeByEmail("chen@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(2L).getSubjects().get(1).getSubjectId(), 2L, 2, 2, "Choose me");
            offer = offerSession.createOffer(20.0, new Date(), tuteeSession.retrieveTuteeByEmail("chen@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(3L).getSubjects().get(2).getSubjectId(), 3L, 3, 3, "I love learning");
            offer = offerSession.createOffer(35.0, new Date(), tuteeSession.retrieveTuteeByEmail("chen@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(4L).getSubjects().get(3).getSubjectId(), 4L, 4, 1, "Please accept me");
            offer = offerSession.createOffer(45.0, new Date(), tuteeSession.retrieveTuteeByEmail("wei@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(5L).getSubjects().get(0).getSubjectId(), 5L, 1, 2, "I love learning");
            offer = offerSession.createOffer(50.0, new Date(), tuteeSession.retrieveTuteeByEmail("wei@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(6L).getSubjects().get(1).getSubjectId(), 6L, 2, 3, "Please accept me");
            offer = offerSession.createOffer(15.0, new Date(), tuteeSession.retrieveTuteeByEmail("wei@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(7L).getSubjects().get(2).getSubjectId(), 7L, 3, 1, "I love learning");
            offer = offerSession.createOffer(20.0, new Date(), tuteeSession.retrieveTuteeByEmail("wei@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(8L).getSubjects().get(3).getSubjectId(), 8L, 4, 2, "Please accept me");
            offer = offerSession.createOffer(40.0, new Date(), tuteeSession.retrieveTuteeByEmail("liang@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(9L).getSubjects().get(0).getSubjectId(), 9L, 1, 3, "I love learning");
            offer = offerSession.createOffer(60.0, new Date(), tuteeSession.retrieveTuteeByEmail("liang@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(10L).getSubjects().get(1).getSubjectId(), 10L, 2, 2, "I need help urgently");
            offer = offerSession.createOffer(50.0, new Date(), tuteeSession.retrieveTuteeByEmail("liang@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(1L).getSubjects().get(2).getSubjectId(), 1L, 1, 1, "I love learning");
            offer = offerSession.createOffer(25.0, new Date(), tuteeSession.retrieveTuteeByEmail("liang@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(2L).getSubjects().get(3).getSubjectId(), 2L, 2, 2, "I  need help urgently");
            offer = offerSession.createOffer(35.0, new Date(), tuteeSession.retrieveTuteeByEmail("tan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(3L).getSubjects().get(0).getSubjectId(), 3L, 3, 3, "I love learning");
            offer = offerSession.createOffer(50.0, new Date(), tuteeSession.retrieveTuteeByEmail("tan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(4L).getSubjects().get(1).getSubjectId(), 4L, 4, 1, "Take me as tutee please");
            offer = offerSession.createOffer(55.0, new Date(), tuteeSession.retrieveTuteeByEmail("tan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(5L).getSubjects().get(2).getSubjectId(), 5L, 5, 2, "I love learning");
            offer = offerSession.createOffer(60.0, new Date(), tuteeSession.retrieveTuteeByEmail("lan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(6L).getSubjects().get(3).getSubjectId(), 6L, 1, 1, "Take me as tutee please");
            offer = offerSession.createOffer(35.0, new Date(), tuteeSession.retrieveTuteeByEmail("lan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(7L).getSubjects().get(0).getSubjectId(), 7L, 2, 2, "I love learning");
            offer = offerSession.createOffer(30.0, new Date(), tuteeSession.retrieveTuteeByEmail("lan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(8L).getSubjects().get(1).getSubjectId(), 8L, 3, 3, "Take me as tutee please");
            offer = offerSession.createOffer(40.0, new Date(), tuteeSession.retrieveTuteeByEmail("lan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(9L).getSubjects().get(2).getSubjectId(), 9L, 4, 2, "I love learning");
            offer = offerSession.createOffer(20.0, new Date(), tuteeSession.retrieveTuteeByEmail("lan@gmail.com").getPersonId(), jobListingSession.retrieveJobListingById(10L).getSubjects().get(3).getSubjectId(), 10L, 1, 5, "Take me as tutee please");
            em.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initRatings2() {
        Rating rating;
        try {
            //createRating(Double ratingValue, String comments, Long offerId) throws OfferNotFoundException {
//            rating = ratingSession.createRatingInit(0, "the tutor was super bad, did not turn up!! DONT EVER TAKE THIS TUTOR", 1L);
//            rating = ratingSession.createRatingInit(0, "cannot teach", 2L);
            rating = ratingSession.createRatingInit(1, "super bad", 3L);
            rating = ratingSession.createRatingInit(1, "too expensive not worth it", 4L);
            rating = ratingSession.createRatingInit(2, "ok la", 5L);
            rating = ratingSession.createRatingInit(2, "below average, i sacked him", 6L);
            rating = ratingSession.createRatingInit(3, "hmmm.. idk leh good or not", 7L);
            rating = ratingSession.createRatingInit(3, "not very honest but can teach", 8L);
            rating = ratingSession.createRatingInit(4, "the tutor was great", 9L);
            rating = ratingSession.createRatingInit(4, "highly recommend", 10L);
            rating = ratingSession.createRatingInit(5, "quite good tbh", 11L);
            rating = ratingSession.createRatingInit(5, "my grades improved!!", 12L);
            rating = ratingSession.createRatingInit(4, "one of the better teacher i have", 13L);
            rating = ratingSession.createRatingInit(4, "good tutor", 14L);
            rating = ratingSession.createRatingInit(3, "average la", 15L);
            rating = ratingSession.createRatingInit(3, "not bad la", 16L);
            rating = ratingSession.createRatingInit(4, "the tutor was great", 17L);
            rating = ratingSession.createRatingInit(4, "the tutor was great i recommend", 18L);
            rating = ratingSession.createRatingInit(5, "excellent", 19L);
            rating = ratingSession.createRatingInit(5, "super friendly", 20L);
            em.flush();

        } catch (OfferNotFoundException | OfferStatusException ex) {
            Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initStaff() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();

        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        try {
            staffSession.createStaff("dummy", "staff", "is3106dummy@gmail.com", "password", "99990001", GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), StaffPositionEnum.MANAGER);
            em.flush();
            staffSession.createStaff("customer care", "staff", "tutormecare3106@gmail.com", "password", "99990002", GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), StaffPositionEnum.OPERATOR);
            em.flush();
        } catch (RegistrationFailException ex) {
            Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
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
        subjectSession.createSubject("Secondary 1 (O)", "Mother Tongue Language");
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
        subjectSession.createSubject("Secondary 2 (O)", "Mother Tongue Language");
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
        subjectSession.createSubject("Secondary 3 (O)", "Mother Tongue Language");
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
        subjectSession.createSubject("Secondary 4 (O)", "Mother Tongue Language");
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

    private void initMessages() {
        try {
            Tutor tutor1 = tutorSession.retrieveTutorByEmail("hsianghui@nus.edu.sg");
            Tutee tutee1 = tuteeSession.retrieveTuteeByEmail("chen@gmail.com");
            Staff staff1 = staffSession.retrieveStaffByEmail("tutormecare3106@gmail.com");
            //Tutor-Tutee
            Message m1 = messageSession.createMessage(tutor1.getPersonId(), tutee1.getPersonId(), "test message from Person_" + tutor1.getPersonId() + " to Person_" + tutee1.getPersonId());
            Message m2 = messageSession.createMessage(tutee1.getPersonId(), tutor1.getPersonId(), "test message from Person_" + tutee1.getPersonId() + " to Person_" + tutor1.getPersonId());
            //Tutor-Staff
            Message m3 = messageSession.createMessage(tutor1.getPersonId(), staff1.getPersonId(), "test message from Person_" + tutor1.getPersonId() + " to Person_" + staff1.getPersonId());
            Message m4 = messageSession.createMessage(staff1.getPersonId(), tutor1.getPersonId(), "test message from Person_" + staff1.getPersonId() + " to Person_" + tutor1.getPersonId());
            //Tutee-Staff
            Message m5 = messageSession.createMessage(tutee1.getPersonId(), staff1.getPersonId(), "test message from Person_" + tutee1.getPersonId() + " to Person_" + staff1.getPersonId());
            Message m6 = messageSession.createMessage(staff1.getPersonId(), tutee1.getPersonId(), "test message from Person_" + staff1.getPersonId() + " to Person_" + tutee1.getPersonId());

        } catch (PersonNotFoundException | TutorNotFoundException | TuteeNotFoundException | StaffNotFoundException ex) {
            System.out.println("Error from data initialization initMessages()");
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
