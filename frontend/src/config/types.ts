type Action = {type: string, payload: any }

type getJobListingListWithParamsProps = {
    subject?: string;
    level?: string;
    price?: string;
};

type jobListingType = {
    id?: number;
    name?: string;
    img?: string;
    education?: string;
    description?: string;
    subjects?: {
        name?: string;
    }[];
    price?: string;
    reviewScore?: number;
    reviewCount?: number;
};

type jobListingCardProps = jobListingType & {
    loading?: boolean;
    handleClick?: (id?: number) => void;
}

type getJobListingListWithParamResposeProps = jobListingType[]

type subjectResponseType = {
    subjectName: string;
    subjectLevel: string;
}[];