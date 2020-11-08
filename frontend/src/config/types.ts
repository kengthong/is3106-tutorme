type Action = {type: string, payload: any }

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
    listingDesc: string;
    rate: number;
    subject: string;
    timeslot: string;
}

type getJobListingListWithParamsProps = {
    subject?: string;
    level?: string;
    price?: string;
    name?: string;
};

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
    tutor?: {
        activeStatus?: boolean;
        createdDate?: string;
        dob?: string;
        email?: string;
        firstName?: string;
        gender?: string;
        lastName?: string;
        mobileNum?: string;
        personEnum?: string;
        personId?: number;
        avgRating?: number;
    },
    offers?: offerType[]
}


type jobListingCardProps = jobListingType & {
    loading?: boolean;
    handleClick?: (id?: number) => void;
}

type getJobListingListWithParamResposeProps = jobListingType[]

type offerType = {
    additionalNote: string;
    chosenSubject: {
        subjectId: number;
        subjectLevel: string;
        subjectName: string;
    },
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
    profileDesc: string ;
    race?: null;
    receivedMessages?: null;
    salt?: null;
    sentMessages?: null;
}

type subjectResponseType = {
    subjectName: string;
    subjectLevel: string;
}[];

