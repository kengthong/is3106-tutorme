type Action = {type: string, payload: any }

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
    }
}
// type jobListingType = {
//     id?: number;
//     name?: string;
//     img?: string;
//     education?: string;
//     description?: string;
//     subjects?: {
//         name?: string;
//     }[];
//     price?: string;
//     reviewScore?: number;
//     reviewCount?: number;
// };

type jobListingCardProps = jobListingType & {
    loading?: boolean;
    handleClick?: (id?: number) => void;
}

type getJobListingListWithParamResposeProps = jobListingType[]

type subjectResponseType = {
    subjectName: string;
    subjectLevel: string;
}[];