type Action = { type: string, payload: any }

type chatListType = {
    userId: number;
    message: string;
    senderName: string;
}[];

type chatListResponseType = chatMessagesType[]

type chatMessagesType = {
    body: string;
    createdDate: string;
    messageId: number;
    receiver: {
        activeStatus?: boolean;
        createdDate?: string;
        dob?: string;
        email?: string;
        firstName: string;
        gender?: string;
        lastName?: string;
        mobileNum?: string;
        personEnum?: string;
        personId: number;
        avgRating?: number;
        citizenship?: string;
        highestQualification?: string;
        profileDesc?: string;
        race?: string;
    },
    sender: {
        activeStatus?: boolean;
        createdDate?: string;
        dob?: string;
        email?: string;
        firstName: string;
        gender?: string;
        lastName?: string;
        mobileNum?: string;
        personEnum?: string;
        personId: number;
        staffPositionEnum?: string;
    }
}[];

type createJobListingParams = {
    listingTitle: string;
    listingDesc: string;
    rate: number;
    subject: string;
    levels: [];
    timeslot: string;
    area: string;
}

type createStaffParams = {
    firstName: string;
    lastName: string;
    email: string;
    password?: null;
    phoneNumber: string;
    gender: string;
    date: string;
    accountType: string;
}

type getJobListingListWithParamsProps = {
    subject?: string;
    level?: string;
    price?: string;
    name?: string;
};

type getJobListingListWithParamResposeProps = jobListingType[]

type jobListingType = {
    activeStatus?: boolean;
    areas?: string;
    createdDate?: string;
    hourlyRates?: number;
    jobListingDesc?: string;
    jobListingId?: number;
    reviewCount?: number;
    reviewScore?: number;
    subjects?: [
        {
            subjectId?: number,
            subjectLevel?: string;
            subjectName?: string;
        }
    ],
    timeslots?: string;
    tutor?: tutorDataType,
    offers?: offerType[],
    numOffers?: number,
    numSubjects?: number
}

type jobListingCardProps = {
    loading?: boolean;
    handleClick?: (id?: number) => void;
} & jobListingType;

type offerType = {
    additionalNote: string;
    chosenSubject: {
        subjectId: number;
        subjectLevel: string;
        subjectName: string;
    },
    jobListing: jobListingType,
    createdDate: string;
    numHoursPerSession: number;
    numSessions: number;
    offerId: number;
    offerStatus: string;
    offeredRate: number;
    rating: {
        comments: string;
        createdDate: string;
        ratingId: number;
        ratingValue: number;
    },
    startDate: string;
    tutee: {
        activeStatus: boolean;
        createdDate: string;
        dob: string;
        email: string;
        firstName: string;
        gender: string;
        lastName: string;
        mobileNum: string;
        personEnum: string;
        personId: number;
    }
}

type createOfferParams = {
    price: number;
    subject: string;
    numSession: number;
    duration: number;
    jobListingId: string;
    levels: string[];
    addNote: string;

}

type staffDataType = {
    personId: number;
    createdDate: string;
    activeStatus: boolean;
    firstName: string;
    lastName: string;
    email: string;
    mobileNum: string;
    password?: null;
    personEnum: string;
    gender: string;
    salt?: null;
    profileImage?: string;
    dob: string;
    sentMessage?: null;
    receivedMessages?: null;
    staffPositionEnum: string;
    profileDesc?: string;
    offers?: null;
}

type tuteeDataType = {
    personId: number;
    createdDate: string;
    activeStatus: boolean;
    firstName: string;
    lastName: string;
    email: string;
    mobileNum: string;
    password?: null;
    salt?: null;
    gender: string;
    personEnum: string;
    profileImage?: string;
    dob: string;
    sentMessage?: null;
    receivedMessages?: null;
    profileDesc?: string;
    offers?: null;
}

type tutorDataType = {
    activeStatus: boolean;
    avgRating: number;
    ratingCount?: number;
    citizenship?: string | null;
    createdDate: string;
    dob: string;
    email: string;
    firstName: string;
    gender: string;
    highestQualification: string | null;
    jobListings?: jobListingType[];
    lastName: string;
    mobileNum: string;
    password?: null;
    personEnum: string;
    personId: number;
    profileImage?: string;
    profileDesc: string;
    race?: null;
    receivedMessages?: null;
    salt?: null;
    sentMessages?: null;
}

type staffDashboardType = {
    numActiveTutors: number;
    tutorGrowth: number;
    numActiveTutees: number;
    tuteeGrowth: number;
    numActiveJobListings: number;
    jobListingGrowth: number;
    offerGrowth: number;
    offerWithdrawalRate: number;
    offerAcceptanceRate: number;
    offerRejectionRate: number;
}

type subjectResponseType = {
    subjectId?: number;
    subjectName: string;
    subjectLevel: string;
}[];



