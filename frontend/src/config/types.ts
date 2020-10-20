type getJobListingListWithParamsProps = {
    subject?: string;
    level?: string;
    price?: string;
}

type jobListingType = {
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
    loading?: boolean;
}

type getJobListingListWithParamResposeProps = jobListingType[]
