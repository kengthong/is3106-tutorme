/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import exception.PersonNotFoundException;
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
        initTutors();
        initTutees();
        initStaff();
        initSubjects();
        initJobListings();
        initOffers();
        initRatings();
        initMessages();
        System.out.println("I HAVE DEPLOYED EVERYTHING");
    }

    private void initTutors() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();

        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        for (int i = 1; i <= 5; i++) {
            String email = "test_tutor".concat(String.valueOf(i)).concat("@email.com");
            int mobileNum = randomNumberGenerator(80000001, 99999998);
            GenderEnum gender = mobileNum % 2 == 0 ? GenderEnum.MALE : GenderEnum.FEMALE;
            Date dob = randomDateBetween(randomStartDate, randomEndDate);
            Tutor tutor = tutorSession.createTutorInit("test", "tutor", email, "password", String.valueOf(mobileNum), gender, dob, QualificationEnum.BACHELOR, CitizenshipEnum.SINGAPORE, RaceEnum.CHINESE, "I LOVE TEACHING", "");
            em.flush();
 
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
            Tutee tutee = tuteeSession.createTuteeInit("test", "tutee", email, "password", String.valueOf(mobileNum), gender, dob, "I love to learn", "");
            em.flush();
        }
    }

    private void initStaff() {
        Calendar c = Calendar.getInstance();
        c.set(1990, 0, 0);
        Date randomStartDate = c.getTime();

        c.set(2015, 12, 31);
        Date randomEndDate = c.getTime();

        staffSession.createStaff("test", "staff", "is3106dummy@gmail.com", "password", "99990001", GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), StaffPositionEnum.MANAGER);
        em.flush();
        staffSession.createStaff("test", "staff", "tutormecare3106@gmail.com", "password", "99990002", GenderEnum.MALE, randomDateBetween(randomStartDate, randomEndDate), StaffPositionEnum.OPERATOR);
        em.flush();
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

    private void initJobListings() {

        try {
            Tutor tutor1 = tutorSession.retrieveTutorByEmail("test_tutor1@email.com");
            Subject subject1 = subjectSession.retrieveSubjectById(1L);
            List<Long> subject1AsLongList = new ArrayList<>();
            subject1AsLongList.add(subject1.getSubjectId());
            JobListing jobListing = jobListingSession.createJobListing(tutor1.getPersonId(), subject1AsLongList, 1.23, "preferred timeslots", "preferred areas", "job description");
            em.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initOffers() {
        try {
            Tutee tutee1 = tuteeSession.retrieveTuteeByEmail("test_tutee1@email.com");
            JobListing joblisting1 = jobListingSession.retrieveJobListingById(1L);
            Subject subject = joblisting1.getSubjects().get(0);

            Offer offer = offerSession.createOffer(2.0, new Date(), tutee1.getPersonId(), subject.getSubjectId(), joblisting1.getJobListingId(), 1, 2, "I love learning");
            em.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initRatings() {
        try {
            Offer offer = offerSession.retrieveOfferById(1L);
            Rating rating = ratingSession.createRating(5, "the tutor was great/bad", offer.getOfferId());
        } catch (OfferNotFoundException ex) {
            Logger.getLogger(DataInitializationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initMessages() {
        try {
            Tutor tutor1 = tutorSession.retrieveTutorByEmail("test_tutor1@email.com");
            Tutee tutee1 = tuteeSession.retrieveTuteeByEmail("test_tutee1@email.com");
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
