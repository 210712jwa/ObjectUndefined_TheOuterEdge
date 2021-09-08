import { Users } from './user';
import { FormStatus } from './form-status';

export interface Form {
    id: number,
    title: string,
    description: string,
    submitted: string,
    eventTime: string,
    image: string,
    author:Users,
    like:number,
    dislike:number,
    verifier: Users,
    formStatus: FormStatus
}